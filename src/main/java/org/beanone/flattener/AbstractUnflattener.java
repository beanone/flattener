package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.CTYPE_SUFFIX;
import static org.beanone.flattener.FlattenerContants.REF_SUFFIX;
import static org.beanone.flattener.FlattenerContants.SIZE_SUFFIX;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.KeyStack;
import org.beanone.flattener.api.Unflattener;
import org.beanone.flattener.exception.FlattenerException;

/**
 * Abstraction implementation of Flattener.
 *
 * @author Hongyan Li
 *
 */
public abstract class AbstractUnflattener implements Unflattener {
	private final Map<String, Object> beanRefs = new HashMap<>();
	private final FlattenerRegistry flattenerRegistry;
	private final FlattenerUtil util;

	protected AbstractUnflattener(FlattenerRegistry flattenerRegistry) {
		if (flattenerRegistry == null) {
			throw new IllegalArgumentException("FlattenerRegistry is null!");
		}
		this.flattenerRegistry = flattenerRegistry;
		util = new FlattenerUtil();
	}

	@Override
	public final FlattenerRegistry getFlattenerRegistry() {
		return flattenerRegistry;
	}

	@Override
	public Object unflat(Map<String, String> flatted, KeyStack keyStack,
	        Class<?> clazz) {
		try {
			final String classKey = keyStack.pop();
			final String prefix = classKey.substring(0,
			        classKey.length() - CTYPE_SUFFIX.length());
			final Object object = doCreateObject(flatted, keyStack, clazz);
			beanRefs.put(prefix, object);
			while (!keyStack.isEmpty()) {
				final String key = keyStack.peek();
				if (!key.startsWith(prefix)) {
					break;
				}
				if (key.endsWith(CTYPE_SUFFIX)) {
					final Object value = unflat(flatted, keyStack,
					        flatted.get(key));
					doPopulate(object, key, CTYPE_SUFFIX.length(), value);
				} else if (key.endsWith(SIZE_SUFFIX)) {
					keyStack.pop();
				} else if (key.endsWith(REF_SUFFIX)) {
					keyStack.pop();
					final String refObjectKey = flatted.get(key);
					final Object value = beanRefs.get(refObjectKey);
					doPopulate(object, key, REF_SUFFIX.length(), value);
				} else {
					keyStack.pop();
					final String typedValueStr = flatted.get(key);
					final Object value = getFlattenerRegistry()
					        .parsePrimitive(typedValueStr);
					doPopulate(object, key, 0, value);
				}
			}
			return object;
		} catch (final ReflectiveOperationException e) {
			throw new FlattenerException(e);
		}
	}

	protected abstract Object doCreateObject(Map<String, String> flatted,
	        KeyStack keyStack, Class<?> clazz)
	        throws ReflectiveOperationException;

	protected abstract void doPopulate(Object object, String key,
	        int suffixSize, Object value) throws ReflectiveOperationException;

	protected final FlattenerUtil getUtil() {
		return util;
	}
}

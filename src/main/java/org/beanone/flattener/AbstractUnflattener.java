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
	private static final ThreadLocal<Map<String, Object>> VALUE_REFS = new ThreadLocal<Map<String, Object>>() {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>();
		}
	};

	private final FlattenerRegistry flattenerRegistry;
	private final FlattenerUtil util;

	protected AbstractUnflattener(FlattenerRegistry flattenerRegistry) {
		if (flattenerRegistry == null) {
			throw new IllegalArgumentException("FlattenerRegistry is null!");
		}
		this.flattenerRegistry = flattenerRegistry;
		this.util = new FlattenerUtil();
	}

	@Override
	public final FlattenerRegistry getFlattenerRegistry() {
		return this.flattenerRegistry;
	}

	@Override
	public Object unflat(Map<String, String> flattened) {
		try {
			return Unflattener.super.unflat(flattened);
		} finally {
			VALUE_REFS.get().clear();
		}
	}

	@Override
	public Object unflat(Map<String, String> flatted, KeyStack keyStack,
	        Class<?> clazz) {
		try {
			final String classKey = keyStack.pop();
			final String prefix = classKey.substring(0,
			        classKey.length() - CTYPE_SUFFIX.length());
			final Object object = doCreateObject(flatted, keyStack, clazz);
			VALUE_REFS.get().put(prefix, object);
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
					final Object value = VALUE_REFS.get().get(refObjectKey);
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
		return this.util;
	}
}

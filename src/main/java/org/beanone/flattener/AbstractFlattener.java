package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.ATTRIBUTE_SEPARATE;
import static org.beanone.flattener.FlattenerContants.CTYPE_SUFFIX;
import static org.beanone.flattener.FlattenerContants.REF_SUFFIX;
import static org.beanone.flattener.FlattenerContants.SUFFIX_SEPARATE;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.FlattenerCallback;
import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.ValueConverter;

/**
 * Abstraction implementation of Flattener.
 *
 * @author Hongyan Li
 *
 */
public abstract class AbstractFlattener implements Flattener {
	private final Map<Object, String> valueRefs = new HashMap<>();
	private final FlattenerRegistry flattenerRegistry;

	protected AbstractFlattener(FlattenerRegistry flattenerRegistry) {
		if (flattenerRegistry == null) {
			throw new IllegalArgumentException("FlattenerRegistry is null!");
		}
		this.flattenerRegistry = flattenerRegistry;
	}

	@Override
	public Map<String, String> flat(Object object) {
		try {
			return Flattener.super.flat(object);
		} finally {
			this.valueRefs.clear();
		}
	}

	@Override
	public Map<String, String> flat(Object object, String prefix,
	        FlattenerCallback callback) {
		final Map<String, String> returns = new HashMap<>();
		if (object != null) {
			// save the reference so that we don't flatten the same
			// again in the graph
			this.valueRefs.put(object, removeLastDot(prefix));
			returns.put(createFullKey(prefix, CTYPE_SUFFIX),
			        object.getClass().getName());
			doFlat(object, (key, value, renderValueType) -> {
				if (value == null) {
					return;
				}
				final String fullKey = createFullKey(prefix, key);
				final ValueConverter<?> converter = getFlattenerRegistry()
			            .findConverter(value);
				if (converter != null) {
					// for registered primitive value types (type that can
			        // convert to from string)
			        // because of type erasure in Java, the type information
			        // of
			        // a value must be stored or we can't determine what
			        // type of
			        // value to convert to from the stored string.
					addKeyValue(returns, fullKey,
			                renderValueType ? converter.toTypedString(value)
			                        : converter.toString(value),
			                value, object, callback);
				} else if (this.valueRefs.containsKey(value)) {
					// already processed values - to avoid cyclic issues
					addKeyValue(returns, fullKey + REF_SUFFIX,
			                this.valueRefs.get(value), value, object, callback);
				} else {
					// flatten the value
					final Flattener mapper = getFlattenerRegistry()
			                .findFlattener(value);
					returns.putAll(mapper.flat(value,
			                fullKey + ATTRIBUTE_SEPARATE, callback));
				}
			});
		}
		return returns;
	}

	@Override
	public FlattenerRegistry getFlattenerRegistry() {
		return this.flattenerRegistry;
	}

	private void addKeyValue(Map<String, String> attributeMap, String key,
	        String strVal, Object value, Object object,
	        FlattenerCallback callback) {
		attributeMap.put(key, strVal);
		callback.callback(key, strVal, value, object);
	}

	private String createFullKey(String prefix, String key) {
		if (prefix.endsWith(ATTRIBUTE_SEPARATE)
		        && key.startsWith(SUFFIX_SEPARATE)) {
			return prefix.substring(0, prefix.length() - 1) + key;
		}
		return prefix + key;
	}

	private String removeLastDot(String prefix) {
		if (prefix.endsWith(ATTRIBUTE_SEPARATE)) {
			return prefix.substring(0,
			        prefix.length() - ATTRIBUTE_SEPARATE.length());
		}
		return prefix;
	}

	/**
	 * Performs the object flattening of the object attributes.
	 *
	 * @param object
	 *            and Object to flat.
	 * @param handler
	 *            a {@link KeyValueHandler} callback function.
	 */
	protected abstract void doFlat(Object object, KeyValueHandler handler);
}

package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.KEY_SUFFIX;
import static org.beanone.flattener.FlattenerContants.VALUE_SUFFIX;

import java.util.Map;

import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.KeyStack;

public class MapUnflattener extends AbstractUnflattener {
	private Object attributeKey;

	protected MapUnflattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	@Override
	protected Object doCreateObject(Map<String, String> flatted,
	        KeyStack keyStack, Class<?> clazz)
	        throws InstantiationException, IllegalAccessException {
		return getUtil().createObject(clazz);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void doPopulate(Object object, String key, int suffixSize,
	        Object value) throws IllegalAccessException {
		final String indexStr = getUtil().extractFieldName(key, suffixSize);
		if (indexStr.endsWith(KEY_SUFFIX)) {
			attributeKey = value;
		} else if (indexStr.endsWith(VALUE_SUFFIX)) {
			if (attributeKey == null) {
				throw new IllegalStateException(
				        "Attribute Key not yet populated! Check to make sure that the key attribute is positioned before the value attribute in the map!");
			}
			final Map map = (Map) object;
			map.put(attributeKey, value);
			attributeKey = null;
		} else {
			throw new IllegalArgumentException(
			        "Expected suffix " + KEY_SUFFIX + " or " + VALUE_SUFFIX
			                + " do not exist or not in the next position!");
		}

	}
}

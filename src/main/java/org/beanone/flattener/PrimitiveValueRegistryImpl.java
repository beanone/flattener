package org.beanone.flattener;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.PrimitiveValueRegistry;
import org.beanone.flattener.api.TypeNameAbbretionMap;
import org.beanone.flattener.api.ValueConverter;

class PrimitiveValueRegistryImpl implements PrimitiveValueRegistry {
	private final Map<Class<?>, ValueConverter<?>> converters = new HashMap<>();

	@Override
	public ValueConverter<?> getConverter(Class<?> type) {
		return converters.get(type);
	}

	@Override
	public void register(Class<?> type, ValueConverter<?> converter) {
		converters.put(type, converter);
		if (TypeNameAbbretionMap.getInstance().fromClass(type) == null) {
			throw new IllegalArgumentException("Abbreviation for class "
			        + type.getName() + " not yet defined. See "
			        + TypeNameAbbretionMap.class);
		}
	}

}

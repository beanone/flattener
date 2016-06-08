package org.beanone.flattener.api;

public interface PrimitiveValueRegistry {
	@SuppressWarnings("rawtypes")
	ValueConverter getConverter(Class<?> type);

	void register(Class<?> type, ValueConverter<?> converter);
}

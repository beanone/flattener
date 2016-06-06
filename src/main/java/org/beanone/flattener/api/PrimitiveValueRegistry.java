package org.beanone.flattener.api;

public interface PrimitiveValueRegistry {
	ValueConverter<?> getConverter(Class<?> type);

	void register(Class<?> type, ValueConverter<?> converter);
}

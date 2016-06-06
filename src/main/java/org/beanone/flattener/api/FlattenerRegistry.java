package org.beanone.flattener.api;

/**
 * Abstraction of a factory for {@link Flattener}s.
 *
 * @author Hongyan Li
 *
 */
public interface FlattenerRegistry {
	ValueConverter<?> findConverter(Object value);

	/**
	 * Finds the {@link Flattener} for the passed in object value.
	 *
	 * @param value
	 *            an Object value whose {@link Flattener} is to be found.
	 * @return the {@link Flattener} for the passed in value.
	 */
	Flattener findFlattener(Object value);

	Unflattener findUnflattener(Class<?> clazz);

	PrimitiveValueRegistry getValueTypeRegistry();

	/**
	 * Registers a {@link Flattener} for the passed in {@link FlattenerResolver}
	 * .
	 *
	 * @param resolver
	 * @param flattener
	 */
	void registerFlattener(FlattenerResolver resolver, Flattener flattener);

	void registerUnflattener(UnflattenerResolver resolver,
	        Unflattener unflattener);
}

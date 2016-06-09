package org.beanone.flattener.api;

/**
 * Abstraction of a registry for the primitive values. The primitive value types
 * is a super set of the set of Java primitive value types. For example both the
 * java.util.Date and java.sql.Date are registered as primitive value internally
 * by this API. Every primitive value type must be defined a
 * {@link ValueConverter}. The users can register his own primitive value types
 * as needed. Every registered primitive value must also define an abbreviation
 * using {@link TypeNameAbbretionMap}.
 *
 * @author Hongyan Li
 *
 */
public interface PrimitiveValueRegistry {
	/**
	 * Retrieves the {@link ValueConverter} for the passed in class type.
	 *
	 * @param type
	 *            the Class of the primitive type.
	 * @return a {@link ValueConverter}.
	 */
	@SuppressWarnings("rawtypes")
	ValueConverter getConverter(Class<?> type);

	/**
	 * Registers a primitive value type.
	 *
	 * @param type
	 *            the Class of the primitive type.
	 * @param converter
	 *            the {@link ValueConverter} of the primitive value type.
	 */
	void register(Class<?> type, ValueConverter<?> converter);
}

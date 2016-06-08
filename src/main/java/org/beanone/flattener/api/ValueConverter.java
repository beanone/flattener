package org.beanone.flattener.api;

/**
 * Abstraction of converters for a given class type. The converters encapsulates
 * the logic to convert a type of object to and from String.
 *
 * @author Hongyan Li
 */
@FunctionalInterface
public interface ValueConverter<T> {
	default String getTypeAbbreviation(Object value) {
		final String abbr = TypeNameAbbretionMap.getInstance()
		        .fromClass(value.getClass());
		if (abbr == null) {
			throw new IllegalArgumentException(
			        "Value type abbreviation not yet defined. Register with PrimitiveTypeMap first.");
		}
		return abbr;
	}

	default String toString(Object value) {
		return value == null ? null : value.toString();
	}

	default String toTypedString(Object value) {
		return value == null ? null
		        : getTypeAbbreviation(value) + "," + toString(value);
	}

	T valueOf(String string);
}

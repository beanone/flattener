package org.beanone.flattener.api;

import java.util.Map;

/**
 * Abstraction of a strategy that can flatten an object into a flat map of
 * string key/value pairs. Or you can view it as a different type of Java
 * serialization. One benefit of such serialization is so that you can easily do
 * a comparison to find all the differences in between two objects. This can be
 * used to implement a high performance object store as incremental updates.
 *
 * @author Hongyan Li
 */
@FunctionalInterface
public interface Flattener {
	default Map<String, String> flat(Object object) {
		return getFlattenerRegistry().findFlattener(object).flat(object, "");
	}

	/**
	 * Maps the passed in object to a flat map of string key/value pairs.
	 *
	 * @param object
	 *            a Java bean object.
	 * @param prefix
	 *            the String to prefix every key
	 * @return a {@link Map} of String to String.
	 */
	Map<String, String> flat(Object object, String prefix);

	default FlattenerRegistry getFlattenerRegistry() {
		throw new UnsupportedOperationException(
		        "Any Flattener must be initialized with a FlattenerRegistry!");
	}
}

package org.beanone.flattener.api;

/**
 * A callback interface for {@link Flattener}s when any attribute is flattened.
 *
 * @author Hongyan Li
 */
@FunctionalInterface
public interface FlattenerCallback {
	FlattenerCallback DO_NOTHING = (k, v, vo, o) -> {
		// Do nothing
	};

	/**
	 * All chaining of multiple call-backs.
	 *
	 * @param next
	 *            the next callback to chain.
	 * @return a callback that chains this and the next.
	 */
	default FlattenerCallback and(FlattenerCallback next) {
		return (k, v, vo, o) -> {
			callback(k, v, vo, o);
			next.callback(k, v, vo, o);
		};
	}

	/**
	 * Callback from a Flattener.
	 *
	 * @param k
	 *            the flat key of the currently processing primitive attribute.
	 * @param v
	 *            the flat value of the currently processing primitive attribute
	 * @param vo
	 *            the attribute value object.
	 * @param o
	 *            the object value that directly owns the attribute currently
	 *            processing.
	 */
	void callback(String k, String v, Object vo, Object o);
}

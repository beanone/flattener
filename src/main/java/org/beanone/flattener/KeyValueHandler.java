package org.beanone.flattener;

@FunctionalInterface
public interface KeyValueHandler {
	/**
	 * Handles the passed in key value pair.
	 *
	 * @param key
	 *            a string key.
	 * @param value
	 *            an object value.
	 * @param renderValueType
	 *            a flag to indicate whether to render the type of the value.
	 */
	void handle(String key, Object value, boolean renderValueType);
}

package org.beanone.flattener;

public class FlattenerContants {
	// we pick the # as the separator for suffixes because
	// it is before all letters and "." in ascii table, so
	// that when the flattened map is sorted by keys, it is
	// in a proper order for easy processing when unflat
	// the map
	// suffix for object class type at position 1
	public static final String CTYPE_SUFFIX = "#1ctype";
	// suffix for array element type at position 3
	public static final String ETYPE_SUFFIX = "#3etype";
	public static final String KEY_SUFFIX = "#key";
	public static final String VALUE_SUFFIX = "#value";
	public static final String REF_SUFFIX = "#ref";
	// suffix for array, collection or map size at position 2
	public static final String SIZE_SUFFIX = "#2size";
	public static final String ATTRIBUTE_SEPARATE = ".";
	public static final String TYPE_SEPARATE = ",";
	public static final String SUFFIX_SEPARATE = "#";

	private FlattenerContants() {
		// private for constants holder
	}
}

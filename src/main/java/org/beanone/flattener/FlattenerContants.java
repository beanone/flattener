package org.beanone.flattener;

public class FlattenerContants {
	// we pick the # as the separator for suffixes because
	// it is before all letters and "." in ascii table, so
	// that when the flattened map is sorted by keys, it is
	// in a proper order for easy processing when unflat
	// the map
	// suffix for object class type at position 1
	public static final String CTYPE_SUFFIX = "%1cty";
	// suffix for array element type at position 3
	public static final String ETYPE_SUFFIX = "%3ety";
	public static final String KEY_SUFFIX = "%1key";
	public static final String VALUE_SUFFIX = "%1val";
	public static final String REF_SUFFIX = "%1ref";
	// suffix for array, collection or map size at position 2
	public static final String SIZE_SUFFIX = "%2siz";
	public static final String ATTRIBUTE_SEPARATE = ".";
	public static final String TYPE_SEPARATE = ",";
	public static final String SUFFIX_SEPARATE = "%";

	private FlattenerContants() {
		// private for constants holder
	}
}

package org.beanone.flattener;

import java.util.Comparator;

public class NaturalComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		return compare(o1, o2, 0);
	}

	public int compare(String o1, String o2, int preference) {
		if (o1 == null) {
			return o2 == null ? 0 : -1;
		} else if (o2 == null) {
			return 1;
		}

		for (int i = 0; i < o1.length(); i++) {
			if (o2.length() == i) {
				// o2 is shorter but same as the beginning of o1
				return 1;
			}
			if (Character.isDigit(o1.charAt(i))
			        && Character.isDigit(o2.charAt(i))) {
				// compare in numeric mode for o1 and o2 the same until the
				// numeric part of the string
				return compareNumeric(o1.substring(i), o2.substring(i),
				        preference);
			}
			if (o1.charAt(i) != o2.charAt(i)) {
				// compare in alpha mode (single character as string)
				return compareString(o1.substring(i, i + 1),
				        o2.substring(i, i + 1), preference);
			}
		}

		// o1 is shorter but same as the beginning of o2
		return o1.length() == o2.length() ? preference : -1;
	}

	private int compareNumeric(String o1, String o2, int preference) {
		final int i1 = parseIntNumer(o1);
		final int i2 = parseIntNumer(o2);
		if (i1 == i2) {
			return compareStringRemovePrefixingNumbers(o1, o2, preference);
		} else {
			return i1 - i2;
		}
	}

	private int compareStringRemovePrefixingNumbers(String o1, String o2,
	        int preference) {
		final String s1 = removeNumberFromHead(o1);
		final String s2 = removeNumberFromHead(o2);
		if (s1 == null && s2 == null) {
			// both strings are pure numeric and they are equal but prefixed
			// with different numbers of 0.
			return compareString(o1, o2, preference);
		}
		if (s1 == null) {
			// o1 is just number but o2 has more after the number
			return -1;
		} else if (s2 == null) {
			// o2 is just number but o1 has more after the number
			return 1;
		}

		final int newPreference = o1.substring(0, o1.indexOf(s1))
		        .compareTo(o2.substring(0, o2.indexOf(s2)));
		return compare(s1, s2, newPreference);
	}

	private int compareString(String s1, String s2, int prefer) {
		final int returns = s1.compareTo(s2);
		return returns == 0 ? prefer : returns;
	}

	private int parseIntNumer(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return Integer.parseInt(s.substring(0, i));
			}
		}

		return Integer.parseInt(s);
	}

	private String removeNumberFromHead(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return s.substring(i);
			}
		}
		return null;
	}
}

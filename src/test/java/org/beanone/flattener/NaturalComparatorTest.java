package org.beanone.flattener;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

public class NaturalComparatorTest {
	private final Comparator<String> comparator = new NaturalComparator();

	@Test
	public void testCompareNumericString() {
		assertNegative(comparator.compare("01234", "1234"));
		assertPositive(comparator.compare("1234", "2"));
		assertNegative(comparator.compare("2", "123"));
		assertPositive(comparator.compare("1234", "01234"));
		Assert.assertEquals(0, comparator.compare("01234", "01234"));
		Assert.assertEquals(0, comparator.compare("1234", "1234"));
	}

	@Test
	public void testCompareNumericWithMixed() {
		assertNegative(comparator.compare("01234", "01234abc"));
		assertPositive(comparator.compare("1234", "2abc"));
		assertNegative(comparator.compare("2", "123abc"));
		assertNegative(comparator.compare("1234", "01234abc"));
		assertNegative(comparator.compare("01234", "1234abc"));
		assertPositive(comparator.compare("01234abc", "01234"));
		assertPositive(comparator.compare("1234abc", "01234"));
		assertPositive(comparator.compare("01234abc", "1234"));
		assertPositive(comparator.compare("1234abc", "1234"));
		assertNegative(comparator.compare("2abc", "1234"));
	}

	@Test
	public void testCompareNumericWithNoneNumeric() {
		Assert.assertTrue(comparator.compare("01234", "abc") < 0);
		Assert.assertTrue(comparator.compare("1234", "abc") < 0);
		Assert.assertTrue(comparator.compare("abc", "01234") > 0);
	}

	@Test
	public void testComparePureStrings() {
		assertNegative(comparator.compare("abcdef", "abcdefg"));
		assertPositive(comparator.compare("abcdefg", "abcdef"));
		Assert.assertEquals(0, comparator.compare("abcdef", "abcdef"));
	}

	@Test
	public void testCompareWithNullArguments() {
		assertNegative(comparator.compare(null, ""));
		assertPositive(comparator.compare("", null));
		Assert.assertEquals(0, comparator.compare(null, null));
	}

	@Test
	public void testMixedWithMixed() {
		assertNegative(comparator.compare("abc01234def", "abc1234def"));
		assertPositive(comparator.compare("abc1234def", "abc01234def"));
		assertPositive(comparator.compare("abc1234def", "abc2def"));
		assertNegative(comparator.compare("abc2def", "abc1234def"));
		Assert.assertEquals(0, comparator.compare("abc01234", "abc01234"));
		Assert.assertEquals(0, comparator.compare("abc1234", "abc1234"));
	}

	private void assertNegative(int i) {
		Assert.assertTrue(i < 0);
	}

	private void assertPositive(int i) {
		Assert.assertTrue(i > 0);
	}
}

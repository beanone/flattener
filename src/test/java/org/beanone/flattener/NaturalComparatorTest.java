package org.beanone.flattener;

import java.util.Comparator;

import org.junit.Assert;
import org.junit.Test;

public class NaturalComparatorTest {
	private final Comparator<String> comparator = new NaturalComparator();

	@Test
	public void testCompareNumericString() {
		Assert.assertEquals(-1, comparator.compare("01234", "1234"));
		Assert.assertTrue(comparator.compare("1234", "2") > 0);
		Assert.assertTrue(comparator.compare("2", "123") < 0);
		Assert.assertEquals(1, comparator.compare("1234", "01234"));
		Assert.assertEquals(0, comparator.compare("01234", "01234"));
		Assert.assertEquals(0, comparator.compare("1234", "1234"));
	}

	@Test
	public void testCompareNumericWithMixed() {
		Assert.assertEquals(-1, comparator.compare("01234", "01234abc"));
		Assert.assertTrue(comparator.compare("1234", "2abc") > 0);
		Assert.assertTrue(comparator.compare("2", "123abc") < 0);
		Assert.assertEquals(-1, comparator.compare("1234", "01234abc"));
		Assert.assertEquals(-1, comparator.compare("01234", "1234abc"));
		Assert.assertEquals(-1, comparator.compare("01234abc", "01234"));
		Assert.assertEquals(-1, comparator.compare("1234abc", "01234"));
		Assert.assertEquals(-1, comparator.compare("01234abc", "1234"));
	}

	@Test
	public void testCompareNumericWithNoneNumeric() {
		Assert.assertEquals(-1, comparator.compare("01234", "abc"));
		Assert.assertEquals(-1, comparator.compare("1234", "abc"));
		Assert.assertEquals(1, comparator.compare("abc", "01234"));
	}

	@Test
	public void testMixedWithMixed() {
		Assert.assertEquals(-1,
		        comparator.compare("abc01234def", "abc1234def"));
		Assert.assertEquals(1, comparator.compare("abc1234def", "abc01234def"));
		Assert.assertTrue(comparator.compare("abc1234def", "abc2def") > 0);
		Assert.assertTrue(comparator.compare("abc2def", "abc1234def") < 0);
		Assert.assertEquals(0, comparator.compare("abc01234", "abc01234"));
		Assert.assertEquals(0, comparator.compare("abc1234", "abc1234"));
	}
}

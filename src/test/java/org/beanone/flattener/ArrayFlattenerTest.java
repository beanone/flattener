package org.beanone.flattener;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayFlattenerTest {
	private ArrayFlattener flattener;

	@Before
	public void setup() {
		this.flattener = new ArrayFlattener(new FlattenerRegistryImpl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArrayFlattenerWithNullFlattenerRegistry() {
		new ArrayFlattener(null);
	}

	@Test
	public void testFlatIntArray() {
		final int[] intArr = { 0, 1 };
		final Map<String, String> result = this.flattener.flat(intArr);
		Assert.assertNotNull(result);
		Assert.assertEquals(5, result.size());
		Assert.assertEquals("[I", result.get("#1ctype"));
		Assert.assertEquals("I,0", result.get("0"));
		Assert.assertEquals("I,1", result.get("1"));
		Assert.assertEquals("2", result.get("#2size"));
		Assert.assertEquals("int", result.get("#3etype"));
	}

	@Test
	public void testFlatIntegerArray() {
		final Number[] intArr = { 0, 1.1 };
		final Map<String, String> result = this.flattener.flat(intArr);
		Assert.assertNotNull(result);
		Assert.assertEquals(5, result.size());
		Assert.assertEquals("[Ljava.lang.Number;", result.get("#1ctype"));
		Assert.assertEquals("I,0", result.get("0"));
		Assert.assertEquals("D,1.1", result.get("1"));
		Assert.assertEquals("2", result.get("#2size"));
		Assert.assertEquals("java.lang.Number", result.get("#3etype"));
	}
}

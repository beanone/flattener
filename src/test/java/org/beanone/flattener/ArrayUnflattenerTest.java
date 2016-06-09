package org.beanone.flattener;

import org.beanone.flattener.api.Flattener;
import org.junit.Test;

public class ArrayUnflattenerTest extends UnflattenerTestBase {

	@Test(expected = IllegalArgumentException.class)
	public void testArrayUnflattenerWithNullRegistry() {
		new ArrayUnflattener(null);
	}

	@Test
	public void testUnflatObjectArray() throws Exception {
		final SimpleTestBean[] arrayOfBeans = { new SimpleTestBean(),
		        new SimpleTestBean() };
		test(arrayOfBeans);
	}

	@Test
	public void testUnflatPrimitiveArray() throws Exception {
		final int[] arr = { 10, 20, 30, 40 };
		test(arr);
	}

	@Override
	protected Flattener createFlattener() {
		return new ArrayFlattener(new FlattenerRegistryImpl());
	}

	@Override
	protected AbstractUnflattener createUnflattener() {
		return new ArrayUnflattener(new FlattenerRegistryImpl());
	}
}

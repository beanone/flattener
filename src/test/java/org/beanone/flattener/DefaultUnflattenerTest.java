package org.beanone.flattener;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.Unflattener;
import org.junit.Test;

public class DefaultUnflattenerTest extends UnflattenerTestBase {

	@Test(expected = IllegalArgumentException.class)
	public void testDefaultUnflattenerWithNullRegistry() {
		new DefaultUnflattener(null);
	}

	@Test
	public void testUnflat() throws Exception {
		test(new SimpleTestBean());
	}

	@Test
	public void testUnflatArray() throws Exception {
		final int[] arr = { 10, 20, 30, 40 };
		test(arr);
	}

	@Test
	public void testUnflatBeanWithArrayAttribute() throws Exception {
		final int[] arr = { 10, 20, 30, 40 };
		final SimpleTestBean bean = new SimpleTestBean();
		bean.setArrayOfInts(arr);
		test(bean);
	}

	@Override
	protected Flattener createFlattener() {
		return new DefaultFlattener(new FlattenerRegistryImpl());
	}

	@Override
	protected Unflattener createUnflattener() {
		return new DefaultUnflattener(new FlattenerRegistryImpl());
	}
}

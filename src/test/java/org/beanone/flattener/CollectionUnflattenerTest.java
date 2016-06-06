package org.beanone.flattener;

import java.util.ArrayList;
import java.util.Arrays;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.Unflattener;
import org.junit.Test;

public class CollectionUnflattenerTest extends UnflattenerTestBase {

	@Test(expected = IllegalArgumentException.class)
	public void testCollectionUnflattenerWithNullRegistry() {
		new CollectionUnflattener(null);
	}

	@Test
	public void testUnflatObjectList() throws Exception {
		final SimpleTestBean[] arrayOfBeans = { new SimpleTestBean(),
		        new SimpleTestBean() };
		test(new ArrayList<>(Arrays.asList(arrayOfBeans)));
	}

	@Test
	public void testUnflatPrimitiveList() throws Exception {
		final int[] arr = { 10, 20, 30, 40 };
		test(new ArrayList<>(Arrays.asList(arr)));
	}

	@Override
	protected Flattener createFlattener() {
		return new CollectionFlattener(new FlattenerRegistryImpl());
	}

	@Override
	protected Unflattener createUnflattener() {
		return new CollectionUnflattener(new FlattenerRegistryImpl());
	}
}

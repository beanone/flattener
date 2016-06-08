package org.beanone.flattener;

import org.junit.Test;

public class DefaultEnumFlattenerTest {

	private final DefaultEnumFlattener flattener = new DefaultEnumFlattener(
	        new FlattenerRegistryImpl());

	@Test(expected = IllegalArgumentException.class)
	public void testDefaultEnumFlattenerWithNullFlattenerRegistry() {
		new DefaultEnumFlattener(null);
	}

	@Test
	public void testDoFlat() {
		flattener.doFlat(ColorEnum.RED, (key, value, renderValueType) -> {
			// do nothing
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoFlatNotEnum() {
		flattener.doFlat(new Object(), (key, value, renderValueType) -> {
			// do nothing
		});
	}
}

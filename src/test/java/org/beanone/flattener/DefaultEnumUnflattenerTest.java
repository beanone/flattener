package org.beanone.flattener;

import org.junit.Assert;
import org.junit.Test;

public class DefaultEnumUnflattenerTest {

	private final DefaultEnumUnflattener unflattener = new DefaultEnumUnflattener(
	        new FlattenerRegistryImpl());

	@Test(expected = IllegalArgumentException.class)
	public void testDefaultEnumUnflattenerWithNullRegistry() {
		new DefaultEnumUnflattener(null);
	}

	@Test
	public void testDoCreateObject() throws Exception {
		final EnumHolder holder = (EnumHolder) unflattener.doCreateObject(null,
		        null, ColorEnum.class);
		Assert.assertEquals(ColorEnum.RED.getClass(), holder.getClazz());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoCreateObjectNotEnumClass() throws Exception {
		unflattener.doCreateObject(null, null, Object.class);
	}

	@Test
	public void testDoPopulate() throws Exception {
		final EnumHolder holder = new EnumHolder(ColorEnum.class);
		unflattener.doPopulate(holder, null, 0, "RED");
		Assert.assertSame(ColorEnum.RED, holder.getValue());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoPopulateObjectNotEnumHolder() throws Exception {
		final EnumHolder holder = new EnumHolder(ColorEnum.class);
		unflattener.doPopulate(holder, null, 0, 1L);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDoPopulateValueNotString() throws Exception {
		unflattener.doPopulate(new Object(), null, 0, "RED");
	}
}

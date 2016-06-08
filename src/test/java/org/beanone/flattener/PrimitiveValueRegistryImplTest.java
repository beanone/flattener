package org.beanone.flattener;

import org.junit.Test;

public class PrimitiveValueRegistryImplTest {
	private static class SomePrimitiveType {
		public static SomePrimitiveType valueOf(String s) {
			return new SomePrimitiveType();
		}
	}

	@Test
	public void testRegister() {
		new PrimitiveValueRegistryImpl().register(Integer.class,
		        Integer::valueOf);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterClassWithoutAbbreviation() {
		new PrimitiveValueRegistryImpl().register(SomePrimitiveType.class,
		        SomePrimitiveType::valueOf);
	}
}

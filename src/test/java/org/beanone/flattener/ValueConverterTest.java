package org.beanone.flattener;

import org.beanone.flattener.api.ValueConverter;
import org.junit.Assert;
import org.junit.Test;

public class ValueConverterTest {
	private class MockBeanWithoutAbbreviation {

	}

	private final ValueConverter<?> converter = s -> s;

	@Test(expected = IllegalArgumentException.class)
	public void testGetTypeAbbreviationForClassMissingAbbreviation() {
		converter.getTypeAbbreviation(new MockBeanWithoutAbbreviation());
	}

	@Test
	public void testGetTypeAbbreviationForClassWithAbbreviation() {
		Assert.assertNotNull(converter.getTypeAbbreviation(1L));
		Assert.assertNotNull(converter.getTypeAbbreviation(Integer.class));
	}

	@Test
	public void testToString() {
		Assert.assertEquals("1", converter.toString(1L));
	}

	@Test
	public void testToStringForNull() {
		Assert.assertNull(converter.toString(null));
	}

	@Test
	public void testToTypedString() {
		Assert.assertEquals("L,1", converter.toTypedString(1L));
	}

	@Test
	public void testToTypedStringForNull() {
		Assert.assertNull(converter.toTypedString(null));
	}
}
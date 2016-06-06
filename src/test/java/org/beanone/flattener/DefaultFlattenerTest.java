package org.beanone.flattener;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DefaultFlattenerTest {
	private class MockBean {
		@SuppressWarnings("unused")
		private final String val = "a";
	}

	private final DefaultFlattener flattener = new DefaultFlattener(
	        new FlattenerRegistryImpl());

	@Test(expected = IllegalArgumentException.class)
	public void testDefaultFlattenerWithNullFlattenerRegistry() {
		new DefaultFlattener(null);
	}

	@Test(expected = FlattenerException.class)
	public void testDoFlatWithException() {
		final KeyValueHandler handler = Mockito.mock(KeyValueHandler.class);
		Mockito.doThrow(IllegalAccessException.class).when(handler).handle(
		        Mockito.anyString(), Mockito.any(), Mockito.anyBoolean());
		flattener.doFlat(new MockBean(), handler);
	}

	@Test
	public void testFlat() {
		final Map<String, String> result = flattener.flat(new SimpleTestBean());
		Assert.assertNotNull(result);
		Assert.assertEquals(19, result.size());
		Assert.assertEquals("org.beanone.flattener.SimpleTestBean",
		        result.get("#1ctype"));
		Assert.assertEquals("I,1", result.get("intVal"));
		Assert.assertEquals("I,1", result.get("intValue"));
		Assert.assertEquals("D,1.0", result.get("doubleVal"));
		Assert.assertEquals("D,1.0", result.get("doubleValue"));
		Assert.assertEquals("F,1.0", result.get("floatVal"));
		Assert.assertEquals("F,1.0", result.get("floatValue"));
		Assert.assertEquals("L,1", result.get("longVal"));
		Assert.assertEquals("L,1", result.get("longValue"));
		Assert.assertEquals("H,1", result.get("shortVal"));
		Assert.assertEquals("H,1", result.get("shortValue"));
		Assert.assertEquals("B,true", result.get("booleanVal"));
		Assert.assertEquals("B,true", result.get("booleanValue"));
		Assert.assertEquals("C,a", result.get("charVal"));
		Assert.assertEquals("C,A", result.get("charValue"));
		Assert.assertEquals("Y,1", result.get("byteVal"));
		Assert.assertEquals("Y,1", result.get("byteValue"));
		Assert.assertEquals("S,abc", result.get("strVal"));
		// self reference
		Assert.assertEquals("", result.get("selfRef#ref"));
	}

	public void testFlatArray() {
		final int[] arr = { 10, 20, 30, 40 };
		final Map<String, String> map = flattener.flat(arr);
		Assert.assertNotNull(map);
		Assert.assertEquals(5, map.size());
	}
}

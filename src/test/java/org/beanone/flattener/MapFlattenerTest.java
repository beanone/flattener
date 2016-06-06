package org.beanone.flattener;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapFlattenerTest {
	private MapFlattener flattener;

	@Before
	public void setup() {
		flattener = new MapFlattener(new FlattenerRegistryImpl());
	}

	@Test
	public void testFlat() {
		final Map<Object, Object> aMap = new HashMap<>();
		aMap.put(1, "a");
		aMap.put("1", 'a');

		final Map<String, String> result = flattener.flat(aMap);
		Assert.assertNotNull(result);
		Assert.assertEquals(6, result.size());
		Assert.assertEquals("java.util.HashMap", result.get("#1ctype"));
		Assert.assertEquals("I,1", result.get("1#key"));
		Assert.assertEquals("S,a", result.get("1#value"));
		Assert.assertEquals("S,1", result.get("2#key"));
		Assert.assertEquals("C,a", result.get("2#value"));
		Assert.assertEquals("2", result.get("#2size"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapFlattenerWithNullFlattenerRegistry() {
		new MapFlattener(null);
	}

}

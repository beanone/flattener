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
		this.flattener = new MapFlattener(new FlattenerRegistryImpl());
	}

	@Test
	public void testFlat() {
		final Map<Object, Object> aMap = new HashMap<>();
		aMap.put(1, "a");
		aMap.put("1", 'a');

		final Map<String, String> result = this.flattener.flat(aMap);
		Assert.assertNotNull(result);
		Assert.assertEquals(6, result.size());
		Assert.assertEquals("java.util.HashMap",
		        result.get(FlattenerContants.CTYPE_SUFFIX));
		Assert.assertEquals("I,1",
		        result.get("1" + FlattenerContants.KEY_SUFFIX));
		Assert.assertEquals("S,a",
		        result.get("1" + FlattenerContants.VALUE_SUFFIX));
		Assert.assertEquals("S,1",
		        result.get("2" + FlattenerContants.KEY_SUFFIX));
		Assert.assertEquals("C,a",
		        result.get("2" + FlattenerContants.VALUE_SUFFIX));
		Assert.assertEquals("2", result.get(FlattenerContants.SIZE_SUFFIX));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMapFlattenerWithNullFlattenerRegistry() {
		new MapFlattener(null);
	}

}

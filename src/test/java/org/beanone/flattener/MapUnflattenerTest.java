package org.beanone.flattener;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.Unflattener;
import org.junit.Assert;
import org.junit.Test;

public class MapUnflattenerTest extends UnflattenerTestBase {

	@Test(expected = IllegalArgumentException.class)
	public void testMapUnflattenerWithNullRegistry() {
		new MapUnflattener(null);
	}

	@Test
	public void testUnflatMapOfObject() throws Exception {
		final Map<Object, Object> aMap = new HashMap<>();
		aMap.put(1, new SimpleTestBean());
		aMap.put("1", new SimpleTestBean());
		test(aMap);
	}

	@Test
	public void testUnflatMapOfPrimitive() throws Exception {
		final Map<Object, Object> aMap = new HashMap<>();
		aMap.put(1, "a");
		aMap.put("1", 'a');
		test(aMap);
	}

	@Test(expected = IllegalStateException.class)
	public void testUnflatMissingKeyForValue() throws Exception {
		final Map<Object, Object> aMap = new HashMap<>();
		aMap.put(1, "a");
		aMap.put("1", 'a');
		final Flattener flattener = createFlattener();
		final Unflattener unflattener = createUnflattener();
		final Map<String, String> flatted = flattener.flat(aMap);
		flatted.remove("1#key");
		Assert.assertNotNull(flatted);
		unflattener.unflat(flatted);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnflatWithKeyContainInvalidSuffixValue() throws Exception {
		final Map<Object, Object> aMap = new HashMap<>();
		aMap.put(1, "a");
		aMap.put("1", 'a');
		final Flattener flattener = createFlattener();
		final Unflattener unflattener = createUnflattener();
		final Map<String, String> flatted = flattener.flat(aMap);
		flatted.put("1#que", "S,b");
		Assert.assertNotNull(flatted);
		unflattener.unflat(flatted);
	}

	@Override
	protected Flattener createFlattener() {
		return new MapFlattener(new FlattenerRegistryImpl());
	}

	@Override
	protected AbstractUnflattener createUnflattener() {
		return new MapUnflattener(new FlattenerRegistryImpl());
	}
}

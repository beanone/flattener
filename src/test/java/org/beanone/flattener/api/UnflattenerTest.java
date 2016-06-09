package org.beanone.flattener.api;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.exception.FlattenerException;
import org.junit.Assert;
import org.junit.Test;

public class UnflattenerTest {
	private final Unflattener unflattener = (flatted, keyStack, clazz) -> null;

	@Test(expected = UnsupportedOperationException.class)
	public void testGetFlattenerRegistry() {
		unflattener.getFlattenerRegistry();
	}

	@Test
	public void testUnflatMapEmptyMap() {
		Assert.assertNull(unflattener.unflat(new HashMap<>()));
	}

	@Test
	public void testUnflatMapNullMap() {
		Assert.assertNull(unflattener.unflat(null));
	}

	@Test
	public void testUnflatMapStackClassEmptyMap() {
		Assert.assertNull(unflattener.unflat(new HashMap<>(), new KeyStack(),
		        Integer.class));
	}

	@Test
	public void testUnflatMapStackClassNullMap() {
		Assert.assertNull(
		        unflattener.unflat(null, new KeyStack(), Integer.class));
	}

	@Test
	public void testUnflatMapStackStringEmptyMap() {
		Assert.assertNull(unflattener.unflat(new HashMap<>(), new KeyStack(),
		        Integer.class.getName()));
	}

	@Test
	public void testUnflatMapStackStringNullMap() {
		Assert.assertNull(unflattener.unflat(null, new KeyStack(),
		        Integer.class.getName()));
	}

	@Test(expected = FlattenerException.class)
	public void testUnflatWithInvalidClassName() {
		final Map<String, String> map = new HashMap<>();
		map.put("#1ctype", Integer.class.getName());
		unflattener.unflat(map, new KeyStack(), "some.bad.ClassName");
	}
}

package org.beanone.flattener.api;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.FlattenerContants;
import org.beanone.flattener.exception.FlattenerException;
import org.junit.Assert;
import org.junit.Test;

public class UnflattenerTest {
	private final Unflattener unflattener = (flatted, keyStack, clazz) -> null;

	@Test(expected = UnsupportedOperationException.class)
	public void testGetFlattenerRegistry() {
		this.unflattener.getFlattenerRegistry();
	}

	@Test
	public void testUnflatMapEmptyMap() {
		Assert.assertNull(this.unflattener.unflat(new HashMap<>()));
	}

	@Test
	public void testUnflatMapNullMap() {
		Assert.assertNull(this.unflattener.unflat(null));
	}

	@Test
	public void testUnflatMapStackClassEmptyMap() {
		Assert.assertNull(this.unflattener.unflat(new HashMap<>(),
		        new KeyStack(), Integer.class));
	}

	@Test
	public void testUnflatMapStackClassNullMap() {
		Assert.assertNull(
		        this.unflattener.unflat(null, new KeyStack(), Integer.class));
	}

	@Test
	public void testUnflatMapStackStringEmptyMap() {
		Assert.assertNull(this.unflattener.unflat(new HashMap<>(),
		        new KeyStack(), Integer.class.getName()));
	}

	@Test
	public void testUnflatMapStackStringNullMap() {
		Assert.assertNull(this.unflattener.unflat(null, new KeyStack(),
		        Integer.class.getName()));
	}

	@Test(expected = FlattenerException.class)
	public void testUnflatWithInvalidClassName() {
		final Map<String, String> map = new HashMap<>();
		map.put(FlattenerContants.CTYPE_SUFFIX, Integer.class.getName());
		this.unflattener.unflat(map, new KeyStack(), "some.bad.ClassName");
	}
}

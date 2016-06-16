package org.beanone.flattener.api;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class KeyStackTest {

	@Test
	public void testCreateSetOfString() {
		final Set<String> keys = new TreeSet<>();
		keys.add("k1");
		keys.add("k2");
		final KeyStack ks = KeyStack.create(keys);
		Assert.assertFalse(ks.isEmpty());
		Assert.assertEquals("k2", ks.peek());
		Assert.assertEquals("k2", ks.pop());
		Assert.assertEquals("k1", ks.peek());
		Assert.assertEquals("k1", ks.pop());
	}

	@Test(expected = NullPointerException.class)
	public void testCreateSetOfStringNull() {
		KeyStack.create((Set<String>) null);
	}

	@Test
	public void testCreateStringArray() {
		final String[] keys = { "k1", "k2" };
		final KeyStack ks = KeyStack.create(keys);
		Assert.assertFalse(ks.isEmpty());
		Assert.assertEquals("k2", ks.peek());
		Assert.assertEquals("k2", ks.pop());
		Assert.assertEquals("k1", ks.peek());
		Assert.assertEquals("k1", ks.pop());
	}

	@Test(expected = NullPointerException.class)
	public void testCreateStringArrayNull() {
		KeyStack.create((String[]) null);
	}

}

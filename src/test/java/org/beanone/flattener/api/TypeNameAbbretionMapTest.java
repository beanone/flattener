package org.beanone.flattener.api;

import org.junit.Assert;
import org.junit.Test;

public class TypeNameAbbretionMapTest {
	private class MockValueType {
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWithAbbreviationAlreadyTaken() {
		TypeNameAbbretionMap.getInstance().add("C", Object.class);
	}

	@Test
	public void testAddWithAbbreviationNotYetTaken() {
		TypeNameAbbretionMap.getInstance().add("X", Object.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWithClassAlreadyMapped() {
		TypeNameAbbretionMap.getInstance().add(".", Integer.class);
	}

	@Test
	public void testFromClass() {
		Assert.assertNotNull(
		        TypeNameAbbretionMap.getInstance().fromClass(Integer.class));
		Assert.assertNull(TypeNameAbbretionMap.getInstance()
		        .fromClass(MockValueType.class));
	}

	@Test
	public void testToClass() {
		Assert.assertNotNull(TypeNameAbbretionMap.getInstance().toClass("C"));
	}

}

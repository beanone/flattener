package org.beanone.flattener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.TypeNameAbbretionMap;
import org.beanone.flattener.api.ValueConverter;
import org.junit.Assert;
import org.junit.Test;

public class FlattenerRegistryImplTest {
	private final FlattenerRegistry flattenerRegistry = new FlattenerRegistryImpl();

	@Test
	public void testFindConverterForBoolean() {
		testFindConverter(true);
	}

	@Test
	public void testFindConverterForByte() {
		testFindConverter((byte) 1);
	}

	@Test
	public void testFindConverterForCharacter() {
		testFindConverter('a');
	}

	@Test
	public void testFindConverterForClass() {
		testFindConverter(Object.class);
	}

	@Test
	public void testFindConverterForDouble() {
		testFindConverter(1.0);
	}

	@Test
	public void testFindConverterForFloat() {
		testFindConverter(1.0F);
	}

	@Test
	public void testFindConverterForInteger() {
		testFindConverter(1);
	}

	@Test
	public void testFindConverterForLong() {
		testFindConverter(1L);
	}

	@Test
	public void testFindConverterForShort() {
		testFindConverter((short) 1);
	}

	@Test
	public void testFindConverterForString() {
		testFindConverter("abc");
	}

	@Test
	public void testFindFlattenerForArrayType() {
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new int[10]) instanceof ArrayFlattener);
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new Integer[10]) instanceof ArrayFlattener);
	}

	@Test
	public void testFindFlattenerForCollectionType() {
		Assert.assertTrue(flattenerRegistry.findFlattener(
		        new ArrayList<>()) instanceof CollectionFlattener);
		Assert.assertTrue(flattenerRegistry.findFlattener(
		        new LinkedList<>()) instanceof CollectionFlattener);
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new HashSet<>()) instanceof CollectionFlattener);
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new TreeSet<>()) instanceof CollectionFlattener);
	}

	@Test
	public void testFindFlattenerForMapType() {
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new HashMap<>()) instanceof MapFlattener);
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new TreeMap<>()) instanceof MapFlattener);
	}

	@Test
	public void testFindFlattenerForNotRegisteredType() {
		Assert.assertTrue(flattenerRegistry
		        .findFlattener(new Object()) instanceof DefaultFlattener);
	}

	@Test
	public void testFindUnflattenerForArrayType() {
		Assert.assertTrue(flattenerRegistry
		        .findUnflattener(int[].class) instanceof ArrayUnflattener);
		Assert.assertTrue(flattenerRegistry
		        .findUnflattener(Integer[].class) instanceof ArrayUnflattener);
	}

	@Test
	public void testFindUnflattenerForCollectionType() {
		Assert.assertTrue(flattenerRegistry.findUnflattener(
		        ArrayList.class) instanceof CollectionUnflattener);
		Assert.assertTrue(flattenerRegistry.findUnflattener(
		        LinkedList.class) instanceof CollectionUnflattener);
		Assert.assertTrue(flattenerRegistry.findUnflattener(
		        HashSet.class) instanceof CollectionUnflattener);
		Assert.assertTrue(flattenerRegistry.findUnflattener(
		        TreeSet.class) instanceof CollectionUnflattener);
	}

	@Test
	public void testFindUnflattenerForMapType() {
		Assert.assertTrue(flattenerRegistry
		        .findUnflattener(HashMap.class) instanceof MapUnflattener);
		Assert.assertTrue(flattenerRegistry
		        .findUnflattener(TreeMap.class) instanceof MapUnflattener);
	}

	@Test
	public void testFindUnflattenerForNotRegisteredType() {
		Assert.assertTrue(flattenerRegistry
		        .findUnflattener(Object.class) instanceof DefaultUnflattener);
	}

	@Test
	public void testGetValueTypeRegistry() {
		Assert.assertNotNull(flattenerRegistry.getValueTypeRegistry());
	}

	@Test
	public void testReadPrimitiveValue() {
		Assert.assertEquals(10, flattenerRegistry.parsePrimitive("I,10"));
		Assert.assertEquals(10.0, flattenerRegistry.parsePrimitive("D,10"));
		Assert.assertEquals(10.0F, flattenerRegistry.parsePrimitive("F,10"));
		Assert.assertEquals("AAA", flattenerRegistry.parsePrimitive("S,AAA"));
	}

	@Test
	public void testRegisterFlattener() {
		flattenerRegistry.registerFlattener(v -> false,
		        new DefaultFlattener(new FlattenerRegistryImpl()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterFlattenerWithNullFlattener() {
		flattenerRegistry.registerFlattener(null,
		        new DefaultFlattener(new FlattenerRegistryImpl()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterFlattenerWithNullResolver() {
		flattenerRegistry.registerFlattener(v -> true, null);
	}

	@Test
	public void testRegisterUnflattener() {
		flattenerRegistry.registerUnflattener(v -> false,
		        new DefaultUnflattener(new FlattenerRegistryImpl()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUnflattenerWithNullFlattener() {
		flattenerRegistry.registerUnflattener(null,
		        new DefaultUnflattener(new FlattenerRegistryImpl()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisterUnflattenerWithNullResolver() {
		flattenerRegistry.registerUnflattener(v -> true, null);
	}

	private void testFindConverter(Object value) {
		final String strValue = value.toString();
		final ValueConverter<?> converter = flattenerRegistry
		        .findConverter(value);
		final String abbr = TypeNameAbbretionMap.getInstance()
		        .fromClass(value.getClass());
		Assert.assertEquals(abbr + "," + strValue,
		        converter.toTypedString(value));
		Assert.assertEquals(converter.valueOf(strValue), value);
	}
}

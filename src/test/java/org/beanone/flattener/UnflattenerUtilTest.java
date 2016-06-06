package org.beanone.flattener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.beanone.flattener.api.KeyStack;
import org.junit.Assert;
import org.junit.Test;

public class UnflattenerUtilTest {
	private final UnflattenerUtil util = new UnflattenerUtil(
	        new FlattenerRegistryImpl());

	@Test
	public void testCreateObject() throws Exception {
		testCreateObject(SimpleTestBean.class);
		testCreateObject(ArrayList.class);
		testCreateObject(HashMap.class);
	}

	@Test
	public void testExtractFieldName() {
		Assert.assertEquals("map",
		        util.extractFieldName("map#2size", "#2size".length()));
		Assert.assertEquals("intValue", util
		        .extractFieldName("listOfMapOfBeans.1.2#value.intValue", 0));
	}

	@Test
	public void testPopExpectSuffix() {
		final KeyStack keys = new KeyStack();
		keys.push("map.1#value");
		keys.push("map.1#key");
		keys.push("map#2size");
		Assert.assertEquals("map#2size", util.popExpectSuffix(keys, "#2size"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPopExpectSuffixForInvalidSuffix() {
		final KeyStack keys = new KeyStack();
		keys.push("map.1#value");
		keys.push("map.1#key");
		keys.push("map#2size");
		Assert.assertEquals("map#2size", util.popExpectSuffix(keys, "#key"));
	}

	@Test
	public void testPopulate() throws Exception {
		final SimpleTestBean bean = new SimpleTestBean();
		util.populate(bean, "listOfMapOfBeans.1.2#value.intVal", 0, 100);
		Assert.assertEquals(100, bean.getIntVal());
	}

	@Test
	public void testPopulateArray() throws Exception {
		final SimpleTestBean[] beans = new SimpleTestBean[10];
		util.populateArray(beans, "arrayOfBeans.0#1ctype", 7,
		        new SimpleTestBean());
		util.populateArray(beans, "arrayOfBeans.1#1ctype", 7,
		        new SimpleTestBean());
		util.populateArray(beans, "arrayOfBeans.2#1ctype", 7,
		        new SimpleTestBean());
		Assert.assertNotNull(beans[0]);
		Assert.assertNotNull(beans[1]);
		Assert.assertNotNull(beans[2]);
	}

	@Test
	public void testPopulateCollection() throws Exception {
		final Collection<Integer> integers = new ArrayList<>();
		util.populateCollection(integers, 10);
		Assert.assertEquals(1, integers.size());
		Assert.assertEquals(new Integer(10), integers.iterator().next());
	}

	@Test
	public void testPopulateIgnoreNoSuchMethodException() throws Exception {
		final ClassWithAttributeNoSetter bean = new ClassWithAttributeNoSetter();
		util.populate(bean, "withSetter", 0, "Hello");
		util.populate(bean, "noSetter", 0, "Hello");
		Assert.assertEquals("Hello", bean.getWithSetter());
		Assert.assertNull(bean.getNoSetter());
	}

	@Test
	public void testReadPrimitiveValue() {
		Assert.assertEquals(10, util.readPrimitiveValue("I,10"));
		Assert.assertEquals(10.0, util.readPrimitiveValue("D,10"));
		Assert.assertEquals(10.0F, util.readPrimitiveValue("F,10"));
		Assert.assertEquals("AAA", util.readPrimitiveValue("S,AAA"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnflattenerUtil() {
		new UnflattenerUtil(null);
	}

	private <T> void testCreateObject(Class<T> clazz) throws Exception {
		final Object object = util.createObject(clazz);
		Assert.assertNotNull(object);
		Assert.assertEquals(clazz, object.getClass());
	}

}

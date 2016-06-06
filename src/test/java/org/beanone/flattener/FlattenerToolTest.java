package org.beanone.flattener;

import java.util.HashMap;
import java.util.Map;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.KeyStack;
import org.beanone.flattener.api.Unflattener;
import org.junit.Assert;
import org.junit.Test;

public class FlattenerToolTest {
	private class MockFlattener implements Flattener {
		private final FlattenerRegistry flattenerRegistry;

		public MockFlattener() {
			flattenerRegistry = new FlattenerRegistryImpl();
		}

		@Override
		public Map<String, String> flat(Object object, String prefix) {
			final Map<String, String> returns = new HashMap<>();
			returns.put("a", "b");
			return returns;
		}

		@Override
		public FlattenerRegistry getFlattenerRegistry() {
			return flattenerRegistry;
		}
	}

	private class MockUnflattener implements Unflattener {
		private final FlattenerRegistry flattenerRegistry;

		public MockUnflattener() {
			flattenerRegistry = new FlattenerRegistryImpl();
		}

		@Override
		public FlattenerRegistry getFlattenerRegistry() {
			return flattenerRegistry;
		}

		@Override
		public Object unflat(Map<String, String> flatted, KeyStack keyStack,
		        Class<?> clazz) {
			return "Hello";
		}
	}

	@Test
	public void testFlatAndUnflatOfComplexTestBean() {
		final ComplexTestBean bean = new ComplexTestBean();
		final int[] numbers = { 10, 20, 30 };
		bean.getArrayOfBeans()[0].setArrayOfInts(numbers);
		final FlattenerTool tool = new FlattenerTool();
		final Map<String, String> map = tool.flat(bean);
		Assert.assertNotNull(map);

		final Object result = tool.unflat(map);
		Assert.assertNotNull(result);
		final Map<String, String> map1 = tool.flat(bean);
		Assert.assertEquals(map.size(), map1.size());
	}

	@Test
	public void testFlatOfSimpleTestBean() {
		Assert.assertNotNull(new FlattenerTool()
		        .withSortStrategy((o1, o2) -> o1.compareTo(o2))
		        .flat(new ComplexTestBean()));
	}

	@Test
	public void testFlatOfSimpleTestBeanWitSortStrategy() {
		Assert.assertNotNull(new FlattenerTool().flat(new ComplexTestBean()));
	}

	@Test
	public void testRegisterClassOfQValueConverterOfQ() {
		final FlattenerTool tool = new FlattenerTool().registerConverter(
		        ColorEnum.class, v -> ColorEnum.valueOf(v), "O");
		final Map<String, String> result = tool.flat(new EnumTestBean());
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("org.beanone.flattener.EnumTestBean",
		        result.get("#1ctype"));
		Assert.assertEquals("O,RED", result.get("color"));
	}

	@Test
	public void testRegisterFlattenerResolverFlattener() {
		final Object object = new Object();
		final FlattenerTool tool = new FlattenerTool().registerFlattener(
		        value -> value == object, new MockFlattener());
		final Map<String, String> result = tool.flat(object);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
		Assert.assertEquals("b", result.get("a"));
	}

	@Test
	public void testRegisterUnflattenerResolverUnflattener() {
		final FlattenerTool tool = new FlattenerTool()
		        .registerUnflattener(clazz -> true, new MockUnflattener());
		final Map<String, String> map = new HashMap<>();
		map.put("#1ctype", String.class.getName());
		final Object result = tool.unflat(map);
		Assert.assertNotNull(result);
		Assert.assertEquals("Hello", result);
	}
}

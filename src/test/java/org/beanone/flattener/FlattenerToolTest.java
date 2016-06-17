package org.beanone.flattener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.FlattenerCallback;
import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.KeyStack;
import org.beanone.flattener.api.Unflattener;
import org.junit.Assert;
import org.junit.Test;

public class FlattenerToolTest {

	private class MockUnflattener implements Unflattener {
		private final FlattenerRegistry flattenerRegistry;

		public MockUnflattener() {
			this.flattenerRegistry = new FlattenerRegistryImpl();
		}

		@Override
		public FlattenerRegistry getFlattenerRegistry() {
			return this.flattenerRegistry;
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
	public void testFlatOfSimpleTestBeanWithFlattenerCallBack() {
		final AtomicInteger count = new AtomicInteger();
		Assert.assertNotNull(new FlattenerTool()
		        .withSortStrategy((o1, o2) -> o1.compareTo(o2))
		        .flat(new ComplexTestBean(), (k, v, vo, o) -> {
			        count.incrementAndGet();
		        }));
		Assert.assertTrue(count.get() > 0);
	}

	@Test
	public void testFlatOfSimpleTestBeanWitSortStrategy() {
		Assert.assertNotNull(new FlattenerTool().flat(new ComplexTestBean()));
	}

	@Test
	public void testFlatSubClass() throws Exception {
		final SubClassTestBean bean = new SubClassTestBean();
		bean.setStrBase("string base");
		bean.setStrSub("string sub");
		bean.setListBase(Arrays.asList("a", "b"));
		bean.setListSub(Arrays.asList("A", "B"));
		bean.setMapBase(createMap());
		Assert.assertEquals(
		        "{#1ctype=org.beanone.flattener.SubClassTestBean, listBase#1ctype=java.util.Arrays$ArrayList, listBase#2size=2, listBase.0=S,a, listBase.1=S,b, listSub#1ctype=java.util.Arrays$ArrayList, listSub#2size=2, listSub.0=S,A, listSub.1=S,B, mapBase#1ctype=java.util.HashMap, mapBase#2size=2, mapBase.1#key=S,k1, mapBase.1#value=S,v1, mapBase.2#key=S,k2, mapBase.2#value=S,v2, strBase=S,string base, strSub=S,string sub}",
		        new FlattenerTool().flat(bean).toString().trim());
	}

	@Test
	public void testPrint() {
		final FlattenerTool tool = new FlattenerTool();
		tool.print(new Object());
		tool.print("");
	}

	@Test
	public void testReadPrimitiveValue() {
		final FlattenerTool tool = new FlattenerTool();
		Assert.assertEquals(10, tool.parsePrimitive("I,10"));
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
		final FlattenerTool tool = new FlattenerTool();
		class MockFlattener implements Flattener {
			private final FlattenerRegistry flattenerRegistry;

			public MockFlattener() {
				this.flattenerRegistry = tool.getFlattenerRegistry();
			}

			@Override
			public Map<String, String> flat(Object object, String prefix,
			        FlattenerCallback callback) {
				final Map<String, String> returns = new HashMap<>();
				returns.put("a", "b");
				return returns;
			}

			@Override
			public FlattenerRegistry getFlattenerRegistry() {
				return this.flattenerRegistry;
			}
		}
		final Object object = new Object();
		tool.registerFlattener(value -> value == object, new MockFlattener());
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

	private Map<String, String> createMap() {
		final Map<String, String> returns = new HashMap<>();
		returns.put("k1", "v1");
		returns.put("k2", "v2");
		return returns;
	}
}

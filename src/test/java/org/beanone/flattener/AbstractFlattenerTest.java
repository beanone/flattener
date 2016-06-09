package org.beanone.flattener;

import java.util.Map;

import org.beanone.flattener.api.FlattenerRegistry;
import org.junit.Assert;
import org.junit.Test;

public class AbstractFlattenerTest {
	private class MockBean {
		private Object value;
		private MockBean self;

		public MockBean getSelf() {
			return self;
		}

		public Object getValue() {
			return value;
		}

		public void setSelf(MockBean self) {
			this.self = self;
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}

	private class MockFlattener extends AbstractFlattener {
		protected MockFlattener(FlattenerRegistry flattenerRegistry) {
			super(flattenerRegistry);
		}

		@Override
		protected void doFlat(Object object, KeyValueHandler handler) {
			final MockBean bean = (MockBean) object;
			handler.handle("value", bean.getValue(), true);
			handler.handle("self", bean.getSelf(), true);
		}
	}

	@Test(expected = NullPointerException.class)
	public void testFlatOfNullObject() {
		final MockFlattener flattener = new MockFlattener(
		        new FlattenerRegistryImpl());
		Map<String, String> map = flattener.flat(null);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
		map = flattener.flat(null, "");
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
	}

	@Test
	public void testFlatOfObjectWithNullAttributeValue() {
		final MockFlattener flattener = new MockFlattener(
		        new FlattenerRegistryImpl());
		final Map<String, String> map = flattener.flat(new MockBean());
		Assert.assertNotNull(map);
		Assert.assertEquals(1, map.size());
	}

	@Test
	public void testFlatOfObjectWithSelfReferenceAndNoneNullValue() {
		final MockFlattener flattener = new MockFlattener(
		        new FlattenerRegistryImpl());
		final MockBean bean = new MockBean();
		bean.setValue(new MockBean());
		bean.setSelf(bean);
		final Map<String, String> map = flattener.flat(bean);
		Assert.assertNotNull(map);
		Assert.assertEquals(3, map.size());
	}
}

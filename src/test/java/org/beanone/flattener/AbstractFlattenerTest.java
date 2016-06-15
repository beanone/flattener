package org.beanone.flattener;

import java.util.Map;

import org.beanone.flattener.api.FlattenerCallback;
import org.beanone.flattener.api.FlattenerRegistry;
import org.junit.Assert;
import org.junit.Test;

public class AbstractFlattenerTest {
	private class MockBean {
		private Object value;
		private MockBean self;

		public MockBean getSelf() {
			return this.self;
		}

		public Object getValue() {
			return this.value;
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

	@Test
	public void testFlatObjectPrefixOfNullObject() {
		final MockFlattener flattener = new MockFlattener(
		        new FlattenerRegistryImpl());
		final Map<String, String> map = flattener.flat(null, "",
		        FlattenerCallback.DO_NOTHING);
		Assert.assertNotNull(map);
		Assert.assertTrue(map.isEmpty());
	}

	@Test(expected = NullPointerException.class)
	public void testFlatOfNullObject() {
		final MockFlattener flattener = new MockFlattener(
		        new FlattenerRegistryImpl());
		flattener.flat(null);
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

package org.beanone.flattener.api;

import java.util.Map;

import org.junit.Test;

public class FlattenerTest {
	@Test(expected = UnsupportedOperationException.class)
	public void testGetFlattenerRegistry() {
		new Flattener() {
			@Override
			public Map<String, String> flat(Object object, String prefix) {
				return null;
			}
		}.getFlattenerRegistry();
	}
}

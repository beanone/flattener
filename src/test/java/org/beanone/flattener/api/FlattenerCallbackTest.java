package org.beanone.flattener.api;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Test;

public class FlattenerCallbackTest {
	@Test
	public void testAndCallback() {
		final AtomicInteger count = new AtomicInteger();
		final FlattenerCallback callback = (k, v, vo, o) -> {
			count.incrementAndGet();
		};
		callback.and(callback).callback("a", "I, 1", 1, new Object());

		Assert.assertEquals(2, count.get());
	}
}

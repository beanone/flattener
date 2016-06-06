package org.beanone.flattener;

import java.util.Map;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.Unflattener;
import org.junit.Assert;

public abstract class UnflattenerTestBase {
	protected abstract Flattener createFlattener();

	protected abstract Unflattener createUnflattener();

	protected void test(Object object) throws Exception {
		final Flattener flattener = createFlattener();
		final Unflattener unflattener = createUnflattener();
		final Map<String, String> flatted = flattener.flat(object);
		Assert.assertNotNull(flatted);
		final Object result = unflattener.unflat(flatted);
		Assert.assertNotNull(result);
		final Map<String, String> flatted2 = flattener.flat(result);
		Assert.assertEquals(flatted.size(), flatted2.size());

	}
}

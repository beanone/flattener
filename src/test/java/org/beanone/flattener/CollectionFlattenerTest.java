package org.beanone.flattener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CollectionFlattenerTest {
	private CollectionFlattener flattener;

	@Before
	public void setup() {
		this.flattener = new CollectionFlattener(new FlattenerRegistryImpl());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCollectionFlattenerWithNullFlattenerRegistry() {
		new CollectionFlattener(null);
	}

	@Test
	public void testFlatIntList() {
		final List<Number> intList = new ArrayList<>();
		intList.add(0);
		intList.add(1.1);

		final Map<String, String> result = this.flattener.flat(intList);
		Assert.assertNotNull(result);
		Assert.assertEquals(4, result.size());
		Assert.assertEquals("java.util.ArrayList",
		        result.get(FlattenerContants.CTYPE_SUFFIX));
		Assert.assertEquals("I,0", result.get("0"));
		Assert.assertEquals("D,1.1", result.get("1"));
		Assert.assertEquals("2", result.get(FlattenerContants.SIZE_SUFFIX));
	}

}

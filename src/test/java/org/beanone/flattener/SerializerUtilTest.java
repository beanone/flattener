package org.beanone.flattener;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class SerializerUtilTest {

	@Test
	public void testToStringMapOfStringString() throws Exception {
		final FlattenerTool tool = new FlattenerTool();
		final Map<String, String> map = tool.flat(new ComplexTestBean());
		final String string = SerializerUtil.toString(map);
		final Map<String, String> map1 = SerializerUtil.fromString(string);
		final String string1 = SerializerUtil.toString(map);
		Assert.assertEquals(map.size(), map1.size());
		Assert.assertEquals(string, string1);
	}
}

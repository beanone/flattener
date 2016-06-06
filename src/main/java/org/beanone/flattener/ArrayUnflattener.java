package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.ETYPE_SUFFIX;
import static org.beanone.flattener.FlattenerContants.SIZE_SUFFIX;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.KeyStack;

public class ArrayUnflattener extends AbstractUnflattener {
	protected ArrayUnflattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	@Override
	protected Object doCreateObject(Map<String, String> flatted,
	        KeyStack keyStack, Class<?> clazz)
	        throws InstantiationException, IllegalAccessException,
	        InvocationTargetException, ClassNotFoundException {
		final String sizeKey = getUtil().popExpectSuffix(keyStack, SIZE_SUFFIX);
		// check pop the etyp attribute
		final String eTypeKey = getUtil().popExpectSuffix(keyStack,
		        ETYPE_SUFFIX);
		final String eTypeClassName = flatted.get(eTypeKey);
		final Class<?> componentType = ClassUtils.getClass(eTypeClassName);
		final String sizeStr = flatted.get(sizeKey);
		final int size = Integer.parseInt(sizeStr);
		return Array.newInstance(componentType, size);
	}

	@Override
	protected void doPopulate(Object object, String key, int suffixSize,
	        Object value)
	        throws IllegalAccessException, InvocationTargetException {
		getUtil().populateArray(object, key, suffixSize, value);
	}
}

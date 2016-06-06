package org.beanone.flattener;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.KeyStack;

public class CollectionUnflattener extends AbstractUnflattener {

	protected CollectionUnflattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	@Override
	protected Object doCreateObject(Map<String, String> flatted,
	        KeyStack keyStack, Class<?> clazz)
	        throws InstantiationException, IllegalAccessException,
	        SecurityException, InvocationTargetException {
		return getUtil().createObject(clazz);
	}

	@Override
	protected void doPopulate(Object object, String key, int suffixSize,
	        Object value)
	        throws IllegalAccessException, InvocationTargetException {
		getUtil().populateCollection(object, value);
	}
}

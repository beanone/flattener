package org.beanone.flattener;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.beanone.flattener.api.KeyStack;

public class DefaultUnflattener extends AbstractUnflattener {

	public DefaultUnflattener(FlattenerRegistryImpl flattenerRegistryImpl) {
		super(flattenerRegistryImpl);
	}

	@Override
	protected Object doCreateObject(Map<String, String> flatted,
	        KeyStack keyStack, Class<?> clazz)
	        throws InstantiationException, IllegalAccessException {
		return getUtil().createObject(clazz);
	}

	@Override
	protected void doPopulate(Object object, String key, int suffixSize,
	        Object value)
	        throws IllegalAccessException, InvocationTargetException {
		getUtil().populate(object, key, suffixSize, value);
	}
}

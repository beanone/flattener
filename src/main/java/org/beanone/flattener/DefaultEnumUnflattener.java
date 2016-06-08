package org.beanone.flattener;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.beanone.flattener.api.KeyStack;

public class DefaultEnumUnflattener extends AbstractUnflattener {

	public DefaultEnumUnflattener(FlattenerRegistryImpl flattenerRegistryImpl) {
		super(flattenerRegistryImpl);
	}

	@Override
	protected Object doCreateObject(Map<String, String> flatted,
	        KeyStack keyStack, Class<?> clazz)
	        throws InstantiationException, IllegalAccessException {
		if (clazz.isEnum()) {
			return new EnumHolder(clazz);
		} else {
			throw new IllegalArgumentException(
			        "Expect Enum class but was " + clazz.getName());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPopulate(Object object, String key, int suffixSize,
	        Object value)
	        throws IllegalAccessException, InvocationTargetException {
		if (object instanceof EnumHolder && value instanceof String) {
			final EnumHolder holder = (EnumHolder) object;
			final Enum<?> enumValue = Enum.valueOf(holder.getClazz(),
			        (String) value);
			holder.setValue(enumValue);
		} else {
			throw new IllegalArgumentException(
			        "EnumHolder doPopulate expects String value not was "
			                + value.getClass());
		}
	}
}

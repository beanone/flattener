package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.ATTRIBUTE_SEPARATE;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.beanutils.PropertyUtils;
import org.beanone.flattener.api.KeyStack;
import org.beanone.flattener.exception.FlattenerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class FlattenerUtil {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(FlattenerUtil.class);

	public static Class<?> classValueOf(String classToString) {
		try {
			// Class.toString gives something like "class
			// org.beanone.flatter.SimpleTestBean"
			// remove the first 6 characters, leaves the class name.
			return Class.forName(classToString.substring(6).trim());
		} catch (final ClassNotFoundException e) {
			throw new FlattenerException(e);
		}

	}

	/**
	 * Create new instance of any type that is not a wrapper for primitive type,
	 * not a String and not an array.
	 *
	 * @param clazz
	 *            the Class of the type of object to create.
	 * @return a new instance of the class type passed in
	 * @throws InstantiationException
	 *             if error the same error from Java reflection.
	 * @throws IllegalAccessException
	 *             if error the same error from Java reflection.
	 */
	Object createObject(Class<?> clazz)
	        throws InstantiationException, IllegalAccessException {
		return clazz.newInstance();
	}

	final String extractFieldName(String key, int suffixSize) {
		final int start = key.lastIndexOf(ATTRIBUTE_SEPARATE);
		return key.substring(start == -1 ? 0 : start + 1,
		        key.length() - suffixSize);
	}

	final String popExpectSuffix(KeyStack keyStack, String suffix) {
		final String key = keyStack.pop();
		if (!key.endsWith(suffix)) {
			throw new IllegalArgumentException("Expected suffix " + suffix
			        + " does not exist or not in the next position!");
		}
		return key;
	}

	final void populate(Object object, String key, int suffixSize, Object value)
	        throws IllegalAccessException, InvocationTargetException {
		try {
			if (value instanceof EnumHolder) {
				PropertyUtils.setProperty(object,
				        extractFieldName(key, suffixSize),
				        ((EnumHolder) value).getValue());
			} else {
				PropertyUtils.setProperty(object,
				        extractFieldName(key, suffixSize), value);
			}
		} catch (final NoSuchMethodException e) {
			LOGGER.warn(e.getMessage(), e);
		}
	}

	final void populateArray(Object object, String key, int suffixSize,
	        Object value)
	        throws IllegalAccessException, InvocationTargetException {
		final String indexStr = extractFieldName(key, suffixSize);
		final int index = Integer.parseInt(indexStr);
		Array.set(object, index, value);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final void populateCollection(Object object, Object value)
	        throws IllegalAccessException, InvocationTargetException {
		Collections.addAll((Collection) object, value);
	}
}

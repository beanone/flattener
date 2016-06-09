package org.beanone.flattener;

import java.lang.reflect.Field;

import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.exception.FlattenerException;

/**
 * Flattener for any java bean that does not have a registered Flattener.
 *
 * @author Hongyan Li
 *
 */
class DefaultFlattener extends AbstractFlattener {
	protected DefaultFlattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	@Override
	protected void doFlat(Object object, KeyValueHandler handler) {
		try {
			final Field[] fields = object.getClass().getDeclaredFields();
			for (final Field f : fields) {
				if (!f.isSynthetic()) {
					f.setAccessible(true);
					handler.handle(f.getName(), f.get(object), true);
				}
			}
		} catch (final IllegalAccessException e) {
			throw new FlattenerException(e);
		}
	}
}

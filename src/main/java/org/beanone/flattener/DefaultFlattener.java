package org.beanone.flattener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.exception.FlattenerException;

/**
 * Flattener for any java bean that does not have a registered Flattener.
 *
 * @author Hongyan Li
 *
 */
class DefaultFlattener extends AbstractFlattener {
	private final FieldNameNatureComparator nameComparator = new FieldNameNatureComparator();

	protected DefaultFlattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	private List<Field> getAllFields(Class<?> clazz) {
		final List<Field> returns = new ArrayList<>();
		if (clazz != null) {
			returns.addAll(Arrays.asList(clazz.getDeclaredFields()));
			returns.addAll(getAllFields(clazz.getSuperclass()));
		}

		Collections.sort(returns, this.nameComparator);
		return returns;
	}

	private boolean hasModifier(int modifiers, int modifier) {
		return (modifiers & modifier) == modifier;
	}

	private boolean isStaticFinal(Field f) {
		return hasModifier(f.getModifiers(), Modifier.FINAL)
		        && hasModifier(f.getModifiers(), Modifier.STATIC);
	}

	@Override
	protected void doFlat(Object object, KeyValueHandler handler) {
		try {
			final List<Field> fields = getAllFields(object.getClass());
			for (final Field f : fields) {
				if (!f.isSynthetic() && !isStaticFinal(f)) {
					f.setAccessible(true);
					handler.handle(f.getName(), f.get(object), true);
				}
			}
		} catch (final IllegalAccessException e) {
			throw new FlattenerException(e);
		}
	}
}

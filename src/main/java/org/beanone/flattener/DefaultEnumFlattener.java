package org.beanone.flattener;

import org.beanone.flattener.api.FlattenerRegistry;

/**
 * Flattener for any enum.
 *
 * @author Hongyan Li
 *
 */
class DefaultEnumFlattener extends AbstractFlattener {
	protected DefaultEnumFlattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	@Override
	protected void doFlat(Object object, KeyValueHandler handler) {
		if (object instanceof Enum) {
			handler.handle("value", ((Enum<?>) object).name(), true);
		} else {
			throw new IllegalArgumentException();
		}
	}
}

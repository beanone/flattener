package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.KEY_SUFFIX;
import static org.beanone.flattener.FlattenerContants.SIZE_SUFFIX;
import static org.beanone.flattener.FlattenerContants.VALUE_SUFFIX;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.beanone.flattener.api.FlattenerRegistry;

/**
 * The flattener for all {@link Map} types.
 *
 * @author Hongyan Li
 *
 */
class MapFlattener extends AbstractFlattener {

	protected MapFlattener(FlattenerRegistry flattenerRegistry) {
		super(flattenerRegistry);
	}

	@Override
	protected void doFlat(Object object, KeyValueHandler handler) {
		final Map<?, ?> map = (Map<?, ?>) object;
		handler.handle(SIZE_SUFFIX, map.size(), false);
		final AtomicInteger index = new AtomicInteger(0);
		map.forEach((k, v) -> {
			final String key = String.valueOf(index.incrementAndGet());
			handler.handle(key + KEY_SUFFIX, k, true);
			handler.handle(key + VALUE_SUFFIX, v, true);
		});
	}
}

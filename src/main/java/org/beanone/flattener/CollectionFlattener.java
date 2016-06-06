package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.SIZE_SUFFIX;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.beanone.flattener.api.FlattenerRegistry;

/**
 * Flattener for all {@link Collection} types.
 *
 * @author Hongyan Li
 *
 */
class CollectionFlattener extends AbstractFlattener {

	protected CollectionFlattener(FlattenerRegistry mapperRegistry) {
		super(mapperRegistry);
	}

	@Override
	protected void doFlat(Object object, KeyValueHandler handler) {
		final Collection<?> collection = (Collection<?>) object;
		final AtomicInteger index = new AtomicInteger(0);
		handler.handle(SIZE_SUFFIX, collection.size(), false);
		collection.forEach(o -> handler
		        .handle(String.valueOf(index.getAndIncrement()), o, true));
	}

}

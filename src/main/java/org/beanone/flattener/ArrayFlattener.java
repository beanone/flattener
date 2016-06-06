package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.ETYPE_SUFFIX;
import static org.beanone.flattener.FlattenerContants.SIZE_SUFFIX;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.beanone.flattener.api.FlattenerRegistry;

/**
 * The flattener for all array types.
 *
 * @author Hongyan Li
 *
 */
class ArrayFlattener extends AbstractFlattener {
	protected ArrayFlattener(FlattenerRegistry mapperRegistry) {
		super(mapperRegistry);
	}

	private List<Object> toList(Object array) {
		final int arrlength = Array.getLength(array);
		final List<Object> outputList = new ArrayList<>();
		for (int i = 0; i < arrlength; ++i) {
			outputList.add(Array.get(array, i));
		}
		return outputList;
	}

	@Override
	protected void doFlat(Object object, KeyValueHandler handler) {
		final List<Object> list = toList(object);
		handler.handle(ETYPE_SUFFIX,
		        object.getClass().getComponentType().getName(), false);
		handler.handle(SIZE_SUFFIX, list.size(), false);
		final AtomicInteger index = new AtomicInteger(0);
		list.forEach(o -> handler
		        .handle(String.valueOf(index.getAndIncrement()), o, true));
	}
}

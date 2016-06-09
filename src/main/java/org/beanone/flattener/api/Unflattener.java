package org.beanone.flattener.api;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.beanone.flattener.exception.FlattenerException;

/**
 * The interface for unflat strategy.
 *
 * @author Hongyan Li
 *
 */
@FunctionalInterface
public interface Unflattener {
	/**
	 * @return the {@link FlattenerRegistry} that holds all Unflatteners.
	 */
	default FlattenerRegistry getFlattenerRegistry() {
		throw new UnsupportedOperationException(
		        "Any Unflattener must be initialized with a UnflattenerRegistry!");
	}

	/**
	 * Unflats the passed in Map that contains all the attributes of a Java
	 * bean.
	 *
	 * @param flatted
	 *            the Map that contains all the attributes of the target object.
	 * @return an Object deserialized from the passed in Map.
	 */
	default Object unflat(Map<String, String> flatted) {
		if (flatted == null || flatted.isEmpty()) {
			return null;
		}
		final KeyStack keyStack = new KeyStack();
		final Comparator<String> c = Comparator.comparing((String x) -> x);
		final Set<String> keySet = new TreeSet<>(c.reversed());
		keySet.addAll(flatted.keySet());
		keySet.forEach(keyStack::push);
		final String className = flatted.get(keyStack.peek());
		return unflat(flatted, keyStack, className);
	}

	Object unflat(Map<String, String> flatted, KeyStack keyStack,
	        Class<?> clazz);

	default Object unflat(Map<String, String> flatted, final KeyStack keyStack,
	        final String className) {
		if (flatted == null || flatted.isEmpty()) {
			return null;
		}
		try {
			final Class<?> clazz = Class.forName(className);
			return getFlattenerRegistry().findUnflattener(clazz).unflat(flatted,
			        keyStack, clazz);
		} catch (final ClassNotFoundException e) {
			throw new FlattenerException(e);
		}
	}
}

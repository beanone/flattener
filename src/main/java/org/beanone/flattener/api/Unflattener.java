package org.beanone.flattener.api;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.beanone.flattener.FlattenerException;

@FunctionalInterface
public interface Unflattener {
	default FlattenerRegistry getFlattenerRegistry() {
		throw new UnsupportedOperationException(
		        "Any Unflattener must be initialized with a UnflattenerRegistry!");
	}

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

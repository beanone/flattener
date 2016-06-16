package org.beanone.flattener;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.FlattenerCallback;
import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.FlattenerResolver;
import org.beanone.flattener.api.TypeNameAbbretionMap;
import org.beanone.flattener.api.Unflattener;
import org.beanone.flattener.api.UnflattenerResolver;
import org.beanone.flattener.api.ValueConverter;

public class FlattenerTool {
	private final FlattenerRegistry flattenerRegistry = new FlattenerRegistryImpl();
	private Comparator<String> sortBy;

	public Map<String, String> flat(Object object) {
		final Flattener flattener = getFlattenerRegistry()
		        .findFlattener(object);
		return sortIfNeeded(flattener.flat(object));
	}

	public Map<String, String> flat(Object object, FlattenerCallback callback) {
		final Flattener flattener = getFlattenerRegistry()
		        .findFlattener(object);
		return sortIfNeeded(flattener.flat(object, callback));
	}

	public Object parsePrimitive(String typedValueStr) {
		return getFlattenerRegistry().parsePrimitive(typedValueStr);
	}

	/**
	 * Print the Object attributes.
	 *
	 * @param object
	 *            the bean object whose attributes are to be printed.
	 */
	public void print(Object object) {
		flat(object).forEach((k, v) -> System.out.println(k + "=" + v));
	}

	public FlattenerTool registerConverter(Class<?> clazz,
	        ValueConverter<?> converter, String abbr) {
		TypeNameAbbretionMap.getInstance().add(abbr, clazz);
		getFlattenerRegistry().getValueTypeRegistry().register(clazz,
		        converter);
		return this;
	}

	public FlattenerTool registerFlattener(FlattenerResolver resolver,
	        Flattener flattener) {
		getFlattenerRegistry().registerFlattener(resolver, flattener);
		return this;
	}

	public FlattenerTool registerUnflattener(UnflattenerResolver resolver,
	        Unflattener unflattener) {
		getFlattenerRegistry().registerUnflattener(resolver, unflattener);
		return this;
	}

	public Object unflat(Map<String, String> flatted) {
		final Unflattener unflattener = getFlattenerRegistry()
		        .findUnflattener(null);
		return unflattener.unflat(flatted);
	}

	public FlattenerTool withSortStrategy(Comparator<String> sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	private Map<String, String> sortIfNeeded(Map<String, String> flat) {
		final Map<String, String> returns = new TreeMap<>(this.sortBy);
		returns.putAll(flat);
		return returns;
	}

	FlattenerRegistry getFlattenerRegistry() {
		return this.flattenerRegistry;
	}
}

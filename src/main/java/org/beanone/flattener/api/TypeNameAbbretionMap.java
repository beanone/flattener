package org.beanone.flattener.api;

import java.util.HashMap;
import java.util.Map;

public class TypeNameAbbretionMap {
	private static final TypeNameAbbretionMap INSTANCE = new TypeNameAbbretionMap();

	public static TypeNameAbbretionMap getInstance() {
		return INSTANCE;
	}

	private final Map<String, Class<?>> toClassMap = new HashMap<>();

	private final Map<Class<?>, String> fromClassMap = new HashMap<>();

	private TypeNameAbbretionMap() {
		add("I", Integer.class);
		add("L", Long.class);
		add("D", Double.class);
		add("F", Float.class);
		add("H", Short.class);
		add("Y", Byte.class);
		add("B", Boolean.class);
		add("C", Character.class);
		add("S", String.class);
		add("T", Class.class);
	}

	public void add(String abbr, Class<?> clazz) {
		if (toClassMap.containsKey(abbr) || fromClassMap.containsKey(clazz)) {
			throw new IllegalArgumentException("Already mapped abbreviation "
			        + abbr + " and class " + clazz);
		}
		toClassMap.put(abbr, clazz);
		fromClassMap.put(clazz, abbr);
	}

	public String fromClass(Class<?> clazz) {
		return fromClassMap.get(clazz);
	}

	public Class<?> toClass(String abbr) {
		return toClassMap.get(abbr);
	}
}

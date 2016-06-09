package org.beanone.flattener.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A registry for abbreviations of all primitive value types. The abbreviation
 * of a type must be defined before it can be registered as primitive value type
 * with {@link PrimitiveValueRegistry}.
 *
 * @author Hongyan Li
 *
 */
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
		add("BI", BigInteger.class);
		add("BD", BigDecimal.class);

		add("d", Date.class);
		add("sd", java.sql.Date.class);
		add("t", Time.class);
		add("ts", Timestamp.class);
	}

	/**
	 * Adds a new abbreviation. The abbreviation and it type is one-to-one. You
	 * will get an {@link IllegalArgumentException} if you attemp to register a
	 * duplicate for either the abbreviation or the class.
	 *
	 * @param abbr
	 * @param clazz
	 */
	public void add(String abbr, Class<?> clazz) {
		if (toClassMap.containsKey(abbr) || fromClassMap.containsKey(clazz)) {
			throw new IllegalArgumentException("Already mapped abbreviation "
			        + abbr + " and class " + clazz);
		}
		toClassMap.put(abbr, clazz);
		fromClassMap.put(clazz, abbr);
	}

	/**
	 * Fetches the abbreviation for the passed in class.
	 *
	 * @param clazz
	 *            a Class whose abbreviation is retrieved.
	 * @return the abbreviation or null if not found.
	 */
	public String fromClass(Class<?> clazz) {
		return fromClassMap.get(clazz);
	}

	/**
	 * Fetches the class for an abbreviation.
	 *
	 * @param abbr
	 *            an abbreviation.
	 * @return a Class.
	 */
	public Class<?> toClass(String abbr) {
		return toClassMap.get(abbr);
	}
}

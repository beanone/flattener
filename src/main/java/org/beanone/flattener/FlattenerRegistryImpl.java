package org.beanone.flattener;

import static org.beanone.flattener.FlattenerContants.TYPE_SEPARATE;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;
import org.beanone.flattener.api.Flattener;
import org.beanone.flattener.api.FlattenerRegistry;
import org.beanone.flattener.api.FlattenerResolver;
import org.beanone.flattener.api.PrimitiveValueRegistry;
import org.beanone.flattener.api.TypeNameAbbretionMap;
import org.beanone.flattener.api.Unflattener;
import org.beanone.flattener.api.UnflattenerResolver;
import org.beanone.flattener.api.ValueConverter;
import org.beanone.flattener.converter.SqlDateConverter;
import org.beanone.flattener.converter.SqlTimeConverter;
import org.beanone.flattener.converter.TimestampConverter;
import org.beanone.flattener.converter.UtilDateConverter;

class FlattenerRegistryImpl implements FlattenerRegistry {
	private final Map<FlattenerResolver, Flattener> flatteners = new HashMap<>();
	private final Map<UnflattenerResolver, Unflattener> unflatteners = new HashMap<>();
	private final Flattener defaultFlattener = new DefaultFlattener(this);
	private final Flattener defaultEnumFlattener = new DefaultEnumFlattener(
	        this);
	private final Unflattener defaultUnflattener = new DefaultUnflattener(this);
	private final Unflattener defaultEnumUnflattener = new DefaultEnumUnflattener(
	        this);
	private final PrimitiveValueRegistry valueTypeRegistry = new PrimitiveValueRegistryImpl();

	public FlattenerRegistryImpl() {
		registerFlattener(value -> value.getClass().isArray(),
		        new ArrayFlattener(this));
		registerFlattener(value -> value instanceof Collection,
		        new CollectionFlattener(this));
		registerFlattener(value -> value instanceof Map,
		        new MapFlattener(this));
		registerUnflattener(clazz -> clazz.isArray(),
		        new ArrayUnflattener(this));
		registerUnflattener(
		        clazz -> ClassUtils.isAssignable(clazz, Collection.class),
		        new CollectionUnflattener(this));
		registerUnflattener(clazz -> ClassUtils.isAssignable(clazz, Map.class),
		        new MapUnflattener(this));

		valueTypeRegistry.register(Integer.class, Integer::valueOf);
		valueTypeRegistry.register(Long.class, Long::valueOf);
		valueTypeRegistry.register(Double.class, Double::valueOf);
		valueTypeRegistry.register(Float.class, Float::valueOf);
		valueTypeRegistry.register(Short.class, Short::valueOf);
		valueTypeRegistry.register(Byte.class, Byte::valueOf);
		valueTypeRegistry.register(Boolean.class, Boolean::valueOf);
		valueTypeRegistry.register(Character.class, v -> v.charAt(0));
		valueTypeRegistry.register(String.class, v -> v);
		valueTypeRegistry.register(Class.class, FlattenerUtil::classValueOf);
		valueTypeRegistry.register(Date.class, new UtilDateConverter());
		valueTypeRegistry.register(java.sql.Date.class, new SqlDateConverter());
		valueTypeRegistry.register(Time.class, new SqlTimeConverter());
		valueTypeRegistry.register(Timestamp.class, new TimestampConverter());
		valueTypeRegistry.register(BigInteger.class, v -> new BigInteger(v));
		valueTypeRegistry.register(BigDecimal.class, v -> new BigDecimal(v));
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public ValueConverter findConverter(Object value) {
		return getValueTypeRegistry().getConverter(value.getClass());
	}

	@Override
	public Flattener findFlattener(Object value) {
		for (final Entry<FlattenerResolver, Flattener> entry : flatteners
		        .entrySet()) {
			if (entry.getKey().accept(value)) {
				return entry.getValue();
			}
		}

		if (value instanceof Enum) {
			return defaultEnumFlattener;
		}
		return defaultFlattener;
	}

	@Override
	public Unflattener findUnflattener(Class<?> clazz) {
		for (final Entry<UnflattenerResolver, Unflattener> entry : unflatteners
		        .entrySet()) {
			if (clazz != null && entry.getKey().accept(clazz)) {
				return entry.getValue();
			}
		}

		if (clazz != null && clazz.isEnum()) {
			return defaultEnumUnflattener;
		}
		return defaultUnflattener;
	}

	@Override
	public PrimitiveValueRegistry getValueTypeRegistry() {
		return valueTypeRegistry;
	}

	@Override
	public Object parsePrimitive(String typedValueStr) {
		final int start = typedValueStr.indexOf(TYPE_SEPARATE);
		final String typeAbbr = typedValueStr.substring(0, start);
		final String valueStr = typedValueStr.substring(start + 1);
		final Class<?> clazz = TypeNameAbbretionMap.getInstance()
		        .toClass(typeAbbr);
		final ValueConverter<?> valueConverter = getValueTypeRegistry()
		        .getConverter(clazz);
		return valueConverter.valueOf(valueStr);
	}

	@Override
	public final void registerFlattener(FlattenerResolver resolver,
	        Flattener mapper) {
		if (resolver == null || mapper == null) {
			throw new IllegalArgumentException();
		}
		flatteners.put(resolver, mapper);
	}

	@Override
	public final void registerUnflattener(UnflattenerResolver resolver,
	        Unflattener mapper) {
		if (resolver == null || mapper == null) {
			throw new IllegalArgumentException();
		}
		unflatteners.put(resolver, mapper);
	}
}

package org.beanone.flattener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;

import org.beanone.flattener.api.MapLoader;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SerializerUtil {
	private static final int CLASS_SUFFIX_LENGTH = FlattenerContants.CTYPE_SUFFIX
	        .length();

	private static final Gson gson = new GsonBuilder().create();

	private SerializerUtil() {
		// private for utility
	}

	public static Map<String, String> deserialize(String string,
	        MapLoader loader) throws IOException {
		final Map<String, String> ckPrefixCache = new HashMap<>();
		final Map<String, String> map = new TreeMap<>(new NaturalComparator());
		final Map<String, String> propsMap = loader.load(string);
		propsMap.forEach((key, value) -> {
			if (Character.isDigit(key.charAt(key.length() - 1))) {
				ckPrefixCache.put(key, value);
			} else {
				final String ckPrefix = getPrefix(key);
				if (ckPrefix.length() == 0) {
					map.put(key, value);
				} else {
					map.put(replaceKey(ckPrefixCache.get(ckPrefix), key),
		                    value);
				}
			}
		});

		return map;
	}

	public static Map<String, String> fromJson(String string)
	        throws IOException {
		return deserialize(string, s -> {
			final Map<String, String> propsMap = new TreeMap<>(
		            new NaturalComparator());
			@SuppressWarnings("serial")
			final Type type = new TypeToken<Map<String, String>>() {
		    }.getType();
			propsMap.putAll(gson.fromJson(string, type));
			return propsMap;
		});
	}

	public static Map<String, String> fromString(String string)
	        throws IOException {
		return deserialize(string, s -> {
			final Map<String, String> propsMap = new TreeMap<>(
		            new NaturalComparator());

			final InputStream stream = new ByteArrayInputStream(
		            string.getBytes());
			final Properties props = new Properties();
			props.load(stream);
			props.forEach((k, v) -> propsMap.put(k.toString(), v.toString()));
			return propsMap;
		});
	}

	public static String toJson(Map<String, String> flattened) {
		final Map<String, String> newMap = new TreeMap<>(
		        new NaturalComparator());
		serialize(flattened, newMap::put);
		return gson.toJson(newMap);
	}

	public static String toString(Map<String, String> flattened) {
		final StringBuilder sb = new StringBuilder();
		serialize(flattened, (k, v) -> appendKeyValuePair(sb, k, v));
		return sb.toString();
	}

	private static void appendKeyValuePair(StringBuilder sb, String key,
	        String value) {
		sb.append(key).append('=').append(value).append('\n');
	}

	private static String extractPrefix(String k) {
		final int index = isAttributeKey(k)
		        ? k.lastIndexOf(FlattenerContants.ATTRIBUTE_SEPARATE)
		        : Math.max(k.lastIndexOf(FlattenerContants.ATTRIBUTE_SEPARATE),
		                k.lastIndexOf(FlattenerContants.SUFFIX_SEPARATE));
		return index < 0 ? "" : k.substring(0, index);
	}

	private static String getPrefix(String k) {
		final int index = k.indexOf('.');
		return index < 0 ? "" : k.substring(0, index);
	}

	private static boolean isAttributeKey(String k) {
		return k.charAt(k.length() - 5) == FlattenerContants.SUFFIX_SEPARATE
		        .charAt(0);
	}

	private static String replaceKey(String prefix, String key) {
		return prefix + key.substring(key.indexOf('.'));
	}

	private static void serialize(Map<String, String> flattened,
	        BiConsumer<String, String> collector) {
		final Map<String, String> keyCache = new HashMap<>();
		final AtomicInteger count = new AtomicInteger(0);
		final AtomicReference<String> ckRef = new AtomicReference<>("");
		final AtomicReference<String> ckPrefix = new AtomicReference<>("");
		final AtomicInteger ckPrefixLen = new AtomicInteger(0);
		flattened.forEach((k, v) -> {
			if (k.endsWith(FlattenerContants.CTYPE_SUFFIX)) {
				ckRef.set("$" + count.incrementAndGet());
				ckPrefix.set(stripClassSuffix(k));
				ckPrefixLen.set(ckPrefix.get().length());
				collector.accept(ckRef.get(), ckPrefix.get());
				keyCache.put(ckPrefix.get(), ckRef.get());
			}

			final String keyPrefix = extractPrefix(k);
			final String replaceKey = keyPrefix.length() == 0 ? k
		            : keyCache.get(keyPrefix) + k.substring(keyPrefix.length());
			collector.accept(replaceKey, v);
		});
	}

	private static String stripClassSuffix(String classKey) {
		return classKey.substring(0, classKey.length() - CLASS_SUFFIX_LENGTH);
	}
}

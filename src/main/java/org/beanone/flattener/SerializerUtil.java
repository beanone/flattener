package org.beanone.flattener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SerializerUtil {
	private static final int CLASS_SUFFIX_LENGTH = FlattenerContants.CTYPE_SUFFIX
	        .length();

	public static Map<String, String> fromString(String string)
	        throws IOException {
		final Map<String, String> ckPrefixCache = new HashMap<>();
		final Map<String, String> map = new TreeMap<>(new NaturalComparator());
		final Map<String, String> propsMap = new TreeMap<>(
		        new NaturalComparator());

		final InputStream stream = new ByteArrayInputStream(string.getBytes());
		final Properties props = new Properties();
		props.load(stream);
		props.forEach((k, v) -> propsMap.put(k.toString(), v.toString()));
		propsMap.forEach((k, v) -> {
			final String key = k.toString();
			final String value = v.toString();
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

	public static String toString(Map<String, String> flattened) {
		final Map<String, String> keyCache = new HashMap<>();
		final StringBuilder sb = new StringBuilder();
		final AtomicInteger count = new AtomicInteger(0);
		final AtomicReference<String> ckRef = new AtomicReference<>("");
		final AtomicReference<String> ckPrefix = new AtomicReference<>("");
		final AtomicInteger ckPrefixLen = new AtomicInteger(0);
		flattened.forEach((k, v) -> {
			if (k.endsWith(FlattenerContants.CTYPE_SUFFIX)) {
				ckRef.set("$" + count.incrementAndGet());
				ckPrefix.set(stripClassSuffix(k));
				ckPrefixLen.set(ckPrefix.get().length());
				appendKeyValuePair(sb, ckRef.get(), ckPrefix.get());
				keyCache.put(ckPrefix.get(), ckRef.get());
			}

			final String keyPrefix = extractPrefix(k);
			final String replaceKey = keyPrefix.length() == 0 ? k
		            : keyCache.get(keyPrefix) + k.substring(keyPrefix.length());
			appendKeyValuePair(sb, replaceKey, v);
		});
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
		return k.endsWith(FlattenerContants.REF_SUFFIX)
		        || k.endsWith(FlattenerContants.KEY_SUFFIX)
		        || k.endsWith(FlattenerContants.VALUE_SUFFIX)
		        || k.endsWith(FlattenerContants.CTYPE_SUFFIX)
		        || k.endsWith(FlattenerContants.SIZE_SUFFIX)
		        || k.endsWith(FlattenerContants.ETYPE_SUFFIX);
	}

	private static String replaceKey(String prefix, String key) {
		return prefix + key.substring(key.indexOf('.'));
	}

	private static String stripClassSuffix(String classKey) {
		return classKey.substring(0, classKey.length() - CLASS_SUFFIX_LENGTH);
	}
}

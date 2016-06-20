package org.beanone.flattener.api;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * A function to load string represent flattened object into map.
 *
 * @author Hongyan Li
 */
@FunctionalInterface
public interface MapLoader {
	/**
	 * Loads from an {@link InputStream} using the default {@link Charset} and
	 * turn it into Map.
	 *
	 * @param stream
	 *            the {@link InputStream} that contains the string to be loaded
	 * @return a Map of attributes ordered in natural order.
	 */
	default Map<String, String> load(InputStream stream) throws IOException {
		return load(IOUtils.toString(stream, Charset.defaultCharset()));
	}

	/**
	 * Loads from an {@link InputStream} using the passed in {@link Charset} and
	 * turn it into Map.
	 *
	 * @param stream
	 *            the {@link InputStream} that contains the string to be loaded
	 * @param charset
	 *            the charset to be used to read the stream into string.
	 * @return a Map of attributes ordered in natural order.
	 */
	default Map<String, String> load(InputStream stream, Charset charset)
	        throws IOException {
		return load(IOUtils.toString(stream, charset));
	}

	/**
	 * Loads the string and turn it into Map.
	 *
	 * @param string
	 *            the string to be loaded
	 * @return a Map of attributes ordered in natural order.
	 */
	Map<String, String> load(String string) throws IOException;
}

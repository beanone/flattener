package org.beanone.flattener.api;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Set;
import java.util.Stack;

/**
 * Keeps the attributes. This is not thread safe. It is used in place of the
 * Java {@link Stack} class which is thread-safe and too expansive to use.
 *
 * @author Hongyan Li
 *
 */
public class KeyStack {
	private final Deque<String> data = new ArrayDeque<>();

	public static KeyStack create(Set<String> keys) {
		final KeyStack keyStack = new KeyStack();
		keys.forEach(keyStack::push);
		return keyStack;
	}

	public static KeyStack create(String[] keys) {
		final KeyStack keyStack = new KeyStack();
		Arrays.asList(keys).forEach(keyStack::push);
		return keyStack;
	}

	public boolean isEmpty() {
		return data.isEmpty();
	}

	public String peek() {
		return data.peekFirst();
	}

	public String pop() {
		return data.removeFirst();
	}

	public void push(String element) {
		data.addFirst(element);
	}
}

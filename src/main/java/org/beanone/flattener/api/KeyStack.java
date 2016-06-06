package org.beanone.flattener.api;

import java.util.ArrayDeque;
import java.util.Deque;

public class KeyStack {
	private final Deque<String> data = new ArrayDeque<>();

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

	@Override
	public String toString() {
		return data.toString();
	}
}

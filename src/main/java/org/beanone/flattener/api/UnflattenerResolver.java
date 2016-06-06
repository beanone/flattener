package org.beanone.flattener.api;

@FunctionalInterface
public interface UnflattenerResolver {
	boolean accept(Class<?> clazz);
}

package org.beanone.flattener.api;

/**
 * Abstraction of {@link Flattener} resolving logic.
 *
 * @author Hongyan Li
 *
 */
@FunctionalInterface
public interface FlattenerResolver {
	boolean accept(Object value);
}

package org.beanone.flattener.converter;

import java.sql.Time;

import org.beanone.flattener.api.ValueConverter;

public class SqlTimeConverter implements ValueConverter<Time> {
	@Override
	public String toString(Object value) {
		final Time time = (Time) value;
		return Long.toString(time.getTime());
	}

	@Override
	public Time valueOf(String string) {
		return new Time(Long.parseLong(string));
	}
}

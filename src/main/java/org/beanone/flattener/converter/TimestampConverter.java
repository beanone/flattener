package org.beanone.flattener.converter;

import java.sql.Timestamp;

import org.beanone.flattener.api.ValueConverter;

public class TimestampConverter implements ValueConverter<Timestamp> {
	@Override
	public String toString(Object value) {
		final Timestamp time = (Timestamp) value;
		return Long.toString(time.getTime());
	}

	@Override
	public Timestamp valueOf(String string) {
		return new Timestamp(Long.parseLong(string));
	}
}

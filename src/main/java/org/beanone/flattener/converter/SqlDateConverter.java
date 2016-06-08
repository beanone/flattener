package org.beanone.flattener.converter;

import java.sql.Date;

import org.beanone.flattener.api.ValueConverter;

public class SqlDateConverter implements ValueConverter<Date> {
	@Override
	public String toString(Object value) {
		final Date date = (Date) value;
		return Long.toString(date.getTime());
	}

	@Override
	public Date valueOf(String string) {
		return new Date(Long.parseLong(string));
	}
}

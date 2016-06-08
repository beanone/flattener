package org.beanone.flattener.converter;

import java.util.Date;

import org.beanone.flattener.api.ValueConverter;

public class UtilDateConverter implements ValueConverter<Date> {
	@Override
	public final String toString(final Object value) {
		final Date date = (Date) value;
		return Long.toString(date.getTime());
	}

	@Override
	public final Date valueOf(final String string) {
		return new Date(Long.parseLong(string));
	}
}

package org.beanone.flattener;

import java.lang.reflect.Field;
import java.util.Comparator;

public class FieldNameNatureComparator implements Comparator<Field> {
	private final NaturalComparator nameComparator = new NaturalComparator();

	@Override
	public int compare(Field o1, Field o2) {
		return this.nameComparator.compare(o1.getName(), o2.getName());
	}

}

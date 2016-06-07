package org.beanone.flattener;

@SuppressWarnings("rawtypes")
public class EnumHolder {
	private final Class clazz;
	private Enum value;

	public EnumHolder(Class clazz) {
		this.clazz = clazz;
	}

	public Class getClazz() {
		return clazz;
	}

	public Enum getValue() {
		return value;
	}

	public void setValue(Enum value) {
		this.value = value;
	}
}

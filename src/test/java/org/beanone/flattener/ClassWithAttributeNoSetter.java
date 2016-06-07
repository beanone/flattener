package org.beanone.flattener;

public class ClassWithAttributeNoSetter {
	private String noSetter;
	private String withSetter;

	public String getNoSetter() {
		return noSetter;
	}

	public String getWithSetter() {
		return withSetter;
	}

	public void setWithSetter(String withSetter) {
		this.withSetter = withSetter;
	}
}

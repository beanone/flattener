package org.beanone.flattener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexTestBean {
	private Integer intVal = 10;
	private List<SimpleTestBean> listOfBeans = createListOfBeans();
	private List<Map<List<SimpleTestBean>, SimpleTestBean>> listOfMapOfBeans = createListOfMapOfBeans();
	private SimpleTestBean[] arrayOfBeans = { new SimpleTestBean(),
	        new SimpleTestBean() };
	private Map<String, String> map = createSimpleMap();

	public SimpleTestBean[] getArrayOfBeans() {
		return arrayOfBeans;
	}

	public Integer getIntVal() {
		return intVal;
	}

	public List<SimpleTestBean> getListOfBeans() {
		return listOfBeans;
	}

	public List<Map<List<SimpleTestBean>, SimpleTestBean>> getListOfMapOfBeans() {
		return listOfMapOfBeans;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setArrayOfBeans(SimpleTestBean[] arrayOfBeans) {
		this.arrayOfBeans = arrayOfBeans;
	}

	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}

	public void setListOfBeans(List<SimpleTestBean> listOfBeans) {
		this.listOfBeans = listOfBeans;
	}

	public void setListOfMapOfBeans(
	        List<Map<List<SimpleTestBean>, SimpleTestBean>> listOfMapOfBeans) {
		this.listOfMapOfBeans = listOfMapOfBeans;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	private List<SimpleTestBean> createListOfBeans() {
		final List<SimpleTestBean> returns = new ArrayList<>();
		returns.add(new SimpleTestBean());
		returns.add(new SimpleTestBean());
		return returns;
	}

	private List<Map<List<SimpleTestBean>, SimpleTestBean>> createListOfMapOfBeans() {
		final List<Map<List<SimpleTestBean>, SimpleTestBean>> returns = new ArrayList<>();
		returns.add(createMapOfBeans());
		returns.add(createMapOfBeans());
		return returns;
	}

	private Map<List<SimpleTestBean>, SimpleTestBean> createMapOfBeans() {
		final Map<List<SimpleTestBean>, SimpleTestBean> returns = new HashMap<>();
		returns.put(createListOfBeans(), new SimpleTestBean());
		returns.put(createListOfBeans(), new SimpleTestBean());
		return returns;
	}

	private Map<String, String> createSimpleMap() {
		final Map<String, String> returns = new HashMap<>();
		returns.put("aaa", "AAA");
		returns.put("bbb", "BBB");
		return returns;
	}
}

package org.beanone.flattener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexTestBean {
	private static final String A_CONSTANT = "some constant";
	private Integer intVal = 10;
	// Make sure that empty list and maps are properly serialized NOT as
	// references (the VM might optimize it and thus we have to treat it
	// specially so that they are not serialized as references). For it to be
	// tested, we need to have at least two attributes that are empty list or
	// map. One is here and another is in the SimpleTestBean
	private List<String> emptyList = new ArrayList<>();
	private Map<String, String> emptyMap = new HashMap<String, String>();
	private List<SimpleTestBean> listOfBeans = createListOfBeans();
	private List<Map<List<SimpleTestBean>, SimpleTestBean>> listOfMapOfBeans = createListOfMapOfBeans();
	private SimpleTestBean[] arrayOfBeans = { new SimpleTestBean(),
	        new SimpleTestBean() };
	private Map<String, String> map = createSimpleMap();

	public SimpleTestBean[] getArrayOfBeans() {
		return this.arrayOfBeans;
	}

	public String getConstant() {
		return A_CONSTANT;
	}

	public List<String> getEmptyList() {
		return this.emptyList;
	}

	public Map<String, String> getEmptyMap() {
		return this.emptyMap;
	}

	public Integer getIntVal() {
		return this.intVal;
	}

	public List<SimpleTestBean> getListOfBeans() {
		return this.listOfBeans;
	}

	public List<Map<List<SimpleTestBean>, SimpleTestBean>> getListOfMapOfBeans() {
		return this.listOfMapOfBeans;
	}

	public Map<String, String> getMap() {
		return this.map;
	}

	public void setArrayOfBeans(SimpleTestBean[] arrayOfBeans) {
		this.arrayOfBeans = arrayOfBeans;
	}

	// to fake it to trick Java reflection and make sure that our test still
	// pass
	public void setConstant(String val) {
		// do nothing;
	}

	public void setEmptyList(List<String> emptyList) {
		this.emptyList = emptyList;
	}

	public void setEmptyMap(Map<String, String> emptyMap) {
		this.emptyMap = emptyMap;
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

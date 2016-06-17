package org.beanone.flattener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseTestBean implements Serializable {
	private static final long serialVersionUID = -4833442901921297521L;
	private String strBase;
	private List<String> listBase = new ArrayList<>();
	private Map<String, String> mapBase = new HashMap<>();

	public List<String> getListBase() {
		return this.listBase;
	}

	public Map<String, String> getMapBase() {
		return this.mapBase;
	}

	public String getStrBase() {
		return this.strBase;
	}

	public void setListBase(List<String> listBase) {
		this.listBase = listBase;
	}

	public void setMapBase(Map<String, String> mapBase) {
		this.mapBase = mapBase;
	}

	public void setStrBase(String strBase) {
		this.strBase = strBase;
	}
}

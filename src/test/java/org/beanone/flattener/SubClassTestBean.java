package org.beanone.flattener;

import java.util.ArrayList;
import java.util.List;

public class SubClassTestBean extends BaseTestBean {
	private static final long serialVersionUID = -1512496755544886233L;
	private String strSub;
	private List<String> listSub = new ArrayList<>();

	public List<String> getListSub() {
		return this.listSub;
	}

	public String getStrSub() {
		return this.strSub;
	}

	public void setListSub(List<String> listSub) {
		this.listSub = listSub;
	}

	public void setStrSub(String strSub) {
		this.strSub = strSub;
	}
}

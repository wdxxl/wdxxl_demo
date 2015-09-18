package com.wdxxl.tapestry.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.corelib.components.PageLink;

public class Another {
	@Component(parameters = { "page=start" })
	private PageLink goToStart;

	//@Persist
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	String onPassivate() {
		return value;
	}

	void onActivate(String value) {
		this.value = value;
	}
}

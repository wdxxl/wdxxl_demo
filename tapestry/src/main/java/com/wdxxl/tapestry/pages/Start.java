package com.wdxxl.tapestry.pages;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;

public class Start {
	@InjectPage
	private Another anotherPage;
	
	@Property
	private String theValue = "Init Value";

	public String getGreeting() {
		return "Hello Tapestry!";
	}

	@OnEvent(value = "action", component = "actionLink1")
	Object toAnotherPage() {
		return anotherPage;
	}

	Object onSuccessFromForm1() {
		anotherPage.setValue(theValue);
		return anotherPage;
	}
}
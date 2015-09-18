package com.wdxxl.tapestry.pages;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

public class Login {
	@Property
	private String userName;
	@Property
	private String password;
	@SessionState
	private String user;
	
	Class<?> onSuccess(){
		if("abc".equals(userName) && "123".equals(password)){
			user = userName;
			return Welcome.class;
		}
		return null;
	}
}

package com.wdxxl.tapestry.pages;

import org.apache.tapestry5.annotations.SessionState;

public class Welcome {
	@SessionState
	private String theUser;
	private boolean theUserExists;
	
	public String getTheUser(){
		return theUser;
	}
	
	public boolean isTheUserExists(){
		return theUserExists;
	}
	
	void onActionFromLogout(){
		theUser = null;
	}
}

package com.wdxxl.mockito.service;

public class UserService {
	private final UserManager manager;

	public UserService() {
		this(new UserManager());
	}
	
	UserService(UserManager manager){
		this.manager = manager;
	}
	
	public int getUserCount(){
		try {
			return manager.getUserCount();
		} catch (Exception e) {
			return -1;
		}
	}
}

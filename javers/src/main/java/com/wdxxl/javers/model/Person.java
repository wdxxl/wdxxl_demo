package com.wdxxl.javers.model;

import javax.persistence.Id;

public class Person {
	@Id
	private final String login;
	private final String name;
	private final String title;

	public Person(String login, String name, String title) {
		this.login = login;
		this.name = name;
		this.title = title;
	}

	public String getLogin() {
		return login;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}
}

package com.wdxxl.model;

import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "wdxxl", name = "fullname")
public class Fullname {
	private String firstname;
	private String lastname;

	public Fullname() {
	}

	public Fullname(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "Fullname [firstname=" + firstname + ", lastname=" + lastname + "]";
	}

}

package com.wdxxl.model;

import java.util.List;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

@UDT(keyspace = "wdxxl", name = "address")
public class Address {
	private String street;
	private String city;
	@Field(name = "zip_code")
	private int zipCode;
	private List<String> phones;

	public Address() {
	}

	public Address(String street, String city, int zipCode, List<String> phones) {
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.phones = phones;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public List<String> getPhones() {
		return phones;
	}
	public void setPhones(List<String> phones) {
		this.phones = phones;
	}
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", zipCode=" + zipCode + ", phones=" + phones + "]";
	}

}

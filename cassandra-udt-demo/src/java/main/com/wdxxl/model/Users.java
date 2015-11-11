package com.wdxxl.model;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.Frozen;
import com.datastax.driver.mapping.annotations.FrozenValue;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "wdxxl", name = "users")
public class Users {
	@PartitionKey
	private UUID id;
	@Frozen
	private Fullname name;
	@Column(name = "direct_reports")
	@FrozenValue
	private Set<Fullname> directReports;
	@FrozenValue
	private Map<String,Address> addresses;

	public Users() {
	}

	public Users(UUID id, Fullname name, Set<Fullname> directReports, Map<String, Address> addresses) {
		this.id = id;
		this.name = name;
		this.directReports = directReports;
		this.addresses = addresses;
	}

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public Fullname getName() {
		return name;
	}
	public void setName(Fullname name) {
		this.name = name;
	}
	public Set<Fullname> getDirectReports() {
		return directReports;
	}
	public void setDirectReports(Set<Fullname> directReports) {
		this.directReports = directReports;
	}
	public Map<String, Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(Map<String, Address> addresses) {
		this.addresses = addresses;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", name=" + name + ", directReports=" + directReports + ", addresses=" + addresses
				+ "]";
	}

}

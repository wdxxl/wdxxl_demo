package com.wdxxl.javers.test.basic;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.junit.Test;

import com.wdxxl.javers.model.Person;

public class BasicEntityDiffExampleTest {
	@Test
	public void shouldCompareTwoEntityObjects() {
		Javers javers = JaversBuilder.javers().build();

		Person tommyOld = new Person("tommy", "Tommy Smart", "aa");
		Person tommyNew = new Person("tommy", "Tommy C. Smart", "a1Aa");

		Diff diff = javers.compare(tommyOld, tommyNew);
		System.out.println(diff);
		// Diff:
		// 1. ValueChange{globalId:'com.wdxxl.javers.model.Person/tommy',
		// property:'name', oldVal:'Tommy Smart', newVal:'Tommy C. Smart'}
		
		// 2. ValueChange{globalId:'com.wdxxl.javers.model.Person/tommy',
		// property:'title', oldVal:'aa', newVal:'a1Aa'}
	}
}

package com.wdxxl.mockito.real;

import org.junit.Test;
import org.mockito.Mockito;

public class RealClassTest {
	
	@Test
	public void testRealClass(){
		InnerClassA innerClassA = Mockito.mock(InnerClassA.class);
		Mockito.doCallRealMethod().when(innerClassA).goHome();
		Mockito.doCallRealMethod().when(innerClassA).doSomethingA();
		
		innerClassA.goHome();
		
		Mockito.verify(innerClassA).doSomethingA();
		Mockito.verify(innerClassA).doSomethingB();
	}
	
	
	class InnerClassA {
		public void goHome() {
			doSomethingA();
			doSomethingB();
		}
		
		//real invoke it
		public void doSomethingA() {
			System.out.println("I Say doSomethingA!!");
		}
		
		//auto mock method by mockito
		public void doSomethingB() {
			System.out.println("you should not see this message.");
		}
	}
}

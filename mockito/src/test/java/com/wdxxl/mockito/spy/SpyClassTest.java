package com.wdxxl.mockito.spy;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SpyClassTest {

	@Test
	public void testSpyClass() {
		InnerClassA innerClassA = Mockito.spy(new InnerClassA());
		
		Mockito.when(innerClassA.goWhen()).thenReturn(false);
		Assert.assertFalse(innerClassA.goWhen());
		
		Mockito.doReturn(false).when(innerClassA).goDoReturn();
		Assert.assertFalse(innerClassA.goDoReturn());
	}

	class InnerClassA {
		public boolean goWhen() {
			System.out.println("I Say Go When!!");
			return true;
		}
		
		public boolean goDoReturn() {
			System.out.println("I Say Go DoReturn!!");
			return true;
		}
	}
}

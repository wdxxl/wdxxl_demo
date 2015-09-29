package com.wdxxl.mockito.spy;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SpyClassTest {

	@Test
	public void testSpyClass() {
		InnerClassA innerClassA = Mockito.spy(new InnerClassA());
		
		//使用spy的桩实现实际还是会调用stub的方法，只是返回了stub的值
		Mockito.when(innerClassA.goWhen()).thenReturn(false);
		Assert.assertFalse(innerClassA.goWhen());
		
		//spy方法需要使用doReturn方法才不会调用实际方法。
		Mockito.doReturn(false).when(innerClassA).goDoReturn();
		Assert.assertFalse(innerClassA.goDoReturn());
	}

	class InnerClassA {
		public boolean goWhen() {
			System.out.println("I Say Go When!!");
			return true;
		}
		
		public boolean goDoReturn() {
			System.out.println("you should not see this message.!!");
			return true;
		}
	}
}

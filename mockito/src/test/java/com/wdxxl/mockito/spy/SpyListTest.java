package com.wdxxl.mockito.spy;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SpyListTest {
	
	@Test
	public void testSpyList() {
		List<String> list = new LinkedList<>();
		List<String> spy = Mockito.spy(list);
		//spy的原理是，如果不打桩默认都会执行真实的方法，如果打桩则返回桩实现。
		Mockito.when(spy.size()).thenReturn(100);
		
		spy.add("one");
		spy.add("two");
		
		Assert.assertEquals("one", spy.get(0));
		Assert.assertEquals(100, spy.size());
	}
}

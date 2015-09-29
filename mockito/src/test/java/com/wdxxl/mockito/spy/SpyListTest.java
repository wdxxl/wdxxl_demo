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
		Mockito.when(spy.size()).thenReturn(100);
		
		spy.add("one");
		spy.add("two");
		
		Assert.assertEquals("one", spy.get(0));
		Assert.assertEquals(100, spy.size());
	}
}

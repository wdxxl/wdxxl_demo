package com.wdxxl.mockito.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

//https://www.youtube.com/watch?v=DyuWgBHfxNQ
public class UserServiceTest {
	private UserManager manager;

	@Before
	public void setUp(){
		manager = Mockito.mock(UserManager.class);
	}
	
	@Test 
	public void testThenReturn(){
		Mockito.when(manager.getUserCount()).thenReturn(50);
		UserService userService = new UserService(manager);
		Assert.assertEquals(50,userService.getUserCount());
	}
	
	@Test 
	public void testThenThrow(){
		Mockito.when(manager.getUserCount()).thenThrow(new RuntimeException());
		UserService userService = new UserService(manager);
		Assert.assertEquals(-1,userService.getUserCount());
	}
	
	@Test 
	public void testThenAnswer(){
		Mockito.when(manager.getUserCount()).thenAnswer(new Answer<Object>(){
			int count = 0;
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return ++count;
			}
			
		});
		UserService userService = new UserService(manager);
		Assert.assertEquals(1,userService.getUserCount());
		Assert.assertEquals(2,userService.getUserCount());
		Assert.assertEquals(3,userService.getUserCount());
	}
	
}

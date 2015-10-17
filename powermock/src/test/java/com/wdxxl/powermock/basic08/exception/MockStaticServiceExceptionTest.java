package com.wdxxl.powermock.basic08.exception;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// http://stackoverflow.com/questions/14954322/powermock-mockito-does-not-throw-exception-when-told-to
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticService.class})
public class MockStaticServiceExceptionTest {

    @Test(expected = IllegalArgumentException.class)
    public void testMockStaticException1() throws Exception {
        /* Setup */
        String sayString = "hello";
        PowerMockito.mockStatic(StaticService.class);
        /* Mocks */
        PowerMockito.doThrow(new IllegalArgumentException("Mockerror")).when(StaticService.class,
                "say", Matchers.eq(sayString));
        /* Test */
        StaticService.say(sayString);
        /* Asserts */
        Assert.fail("Expected exception not thrown.");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMockStaticException2() throws Exception {
        /* Setup */
        String sayString = "hello";
        PowerMockito.mockStatic(StaticService.class);
        /* Mocks */
        PowerMockito.doThrow(new IllegalArgumentException("Mockerror")).when(StaticService.class);
        StaticService.say(Matchers.eq(sayString));
        /* Test */
        StaticService.say(sayString);
        /* Asserts */
        Assert.fail("Expected exception not thrown.");
    }
}

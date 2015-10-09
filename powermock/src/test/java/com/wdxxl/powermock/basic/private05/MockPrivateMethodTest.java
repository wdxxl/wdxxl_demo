package com.wdxxl.powermock.basic.private05;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * PowerMock基本用法
 * (5) Mock 私有方法
 * 和Mock普通方法一样，只是需要加注解@PrepareForTest(ClassUnderTest.class)，
 * 注解里写的类是私有方法所在的类。 
 */
@RunWith(PowerMockRunner.class)
public class MockPrivateMethodTest {

    @Test
    @PrepareForTest(CallPrivateMethod.class)
    public void callPrivateTest() throws Exception {
        CallPrivateMethod callPrivateMethod = PowerMockito.mock(CallPrivateMethod.class);

        PowerMockito.when(callPrivateMethod.callPrivateMethod()).thenCallRealMethod();
        PowerMockito.when(callPrivateMethod, "isExist").thenReturn(true);

        Assert.assertTrue(callPrivateMethod.callPrivateMethod());
    }
}

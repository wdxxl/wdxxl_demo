package com.wdxxl.powermock.basic.final03;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * PowerMock基本用法
 * (3) Mock普通对象的final方法
 * 当需要mock final方法的时候，必须加注解@PrepareForTest和@RunWith。
 * 注解@PrepareForTest里写的类是final方法所在的类。
 */
@RunWith(PowerMockRunner.class)
public class MockFinalMthodTest {

    @Test
    @PrepareForTest(FinalMethod.class)
    public void testFinal() {
        FinalMethod finalMethod = PowerMockito.mock(FinalMethod.class);
        PowerMockito.when(finalMethod.isAlive()).thenReturn(true);

        CallFinalMethod callFinalMethod = new CallFinalMethod();
        Assert.assertTrue(callFinalMethod.callFinalMethod(finalMethod));
    }
}

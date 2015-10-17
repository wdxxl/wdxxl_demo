package com.wdxxl.powermock.basic04.statics;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * PowerMock基本用法
 * (4) Mock普通类的静态方法
 * 当需要mock静态方法的时候，必须加注解@PrepareForTest和@RunWith。
 * 注解@PrepareForTest里写的类是静态方法所在的类。
 */
@RunWith(PowerMockRunner.class)
public class MockStaticMethodTest {

    @Test
    @PrepareForTest(StaticMethod.class)
    public void testStatic() {
        PowerMockito.mockStatic(StaticMethod.class);
        PowerMockito.when(StaticMethod.isExist()).thenReturn(true);

        CallStaticMethod callStaticMethod = new CallStaticMethod();
        Assert.assertTrue(callStaticMethod.callStaticMethod());
    }
}

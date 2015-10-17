package com.wdxxl.powermock.basic02.news;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * PowerMock基本用法
 * (2) Mock方法内部new出来的对象
 * 当使用PowerMockito.whenNew方法时，必须加注解@PrepareForTest和@RunWith。
 * 注解@PrepareForTest里写的类是需要mock的new对象代码所在的类。
 */
@RunWith(PowerMockRunner.class)
public class MockNewInMethodTest {

    @Test
    @PrepareForTest(NewInMethod.class)
    public void testCallInternalInstance() throws Exception {
        File file = PowerMockito.mock(File.class);
        PowerMockito.whenNew(File.class).withArguments("bbb").thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);

        NewInMethod newInMethod = new NewInMethod();
        Assert.assertTrue(newInMethod.callNewInMethod("bbb"));

        // Assert.assertTrue(newInMethod.callNewInMethod("ccc"));
        // java.lang.NullPointerException
    }

}

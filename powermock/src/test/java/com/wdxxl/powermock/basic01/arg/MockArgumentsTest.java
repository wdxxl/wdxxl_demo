package com.wdxxl.powermock.basic01.arg;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

/**
 *  PowerMock基本用法
 *  (1) 普通Mock： Mock参数传递的对象
 */
public class MockArgumentsTest {
    public boolean callArgumentInstance(File file) {
        System.out.println("I Say callArgumentInstance!!");
        return file.exists();
    }

    @Test
    public void testCallArgumentInstance() {
        File file = PowerMockito.mock(File.class);
        PowerMockito.when(file.exists()).thenReturn(true);

        MockArgumentsTest mockArgumentsTest = new MockArgumentsTest();
        Assert.assertTrue(mockArgumentsTest.callArgumentInstance(file));
    }
}

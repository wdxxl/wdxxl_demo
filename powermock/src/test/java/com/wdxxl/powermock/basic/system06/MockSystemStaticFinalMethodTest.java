package com.wdxxl.powermock.basic.system06;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SystemStaticFinalMethod.class)
public class MockSystemStaticFinalMethodTest {

    @Test
    public void testCallSystemStaticMethod() {
        PowerMockito.mockStatic(System.class);
        PowerMockito.when(System.getProperty("aa")).thenReturn("bb");

        SystemStaticFinalMethod systemStaticFinalMethod = new SystemStaticFinalMethod();
        Assert.assertEquals("bb", systemStaticFinalMethod.callSystemStaticMethod("aa"));
    }

    @Test
    public void testCallSystemFinalMethod() {
        String str = PowerMockito.mock(String.class);
        PowerMockito.when(str.isEmpty()).thenReturn(true);

        SystemStaticFinalMethod systemStaticFinalMethod = new SystemStaticFinalMethod();
        Assert.assertTrue(systemStaticFinalMethod.callSystemFinalMethod(str));
    }

}

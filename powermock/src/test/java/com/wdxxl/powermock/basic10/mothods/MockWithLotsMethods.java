package com.wdxxl.powermock.basic10.mothods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// http://stackoverflow.com/questions/19302207/how-to-verify-mocked-method-not-called-with-any-combination-of-parameters-using

@RunWith(PowerMockRunner.class)
@PrepareForTest(WithLotsMethods.class)
public class MockWithLotsMethods {
    private final WithLotsMethods withLotsMethods = PowerMockito.mock(WithLotsMethods.class);

    @Test
    public void testMethodOfString() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfString", Matchers.any(String.class))
                .thenReturn(outPuts);
        Assert.assertEquals(outPuts, withLotsMethods.methodOfString("biu..."));

        PowerMockito.when(withLotsMethods, "methodOfString", Matchers.anyString()).thenReturn(
                outPuts);
        Assert.assertEquals(outPuts, withLotsMethods.methodOfString("biu..."));
    }

    @Test
    public void testMethodOfDataObjects() throws Exception {
        String outPuts = "Mock to call method of String";
        // Integer i, Long l, Short s, Boolean flag, Float f, Double d
        PowerMockito.when(withLotsMethods, "methodOfDataObjects", Matchers.anyInt(),
                Matchers.anyLong(), Matchers.anyShort(), Matchers.anyBoolean(),
                Matchers.anyFloat(), Matchers.anyDouble()).thenReturn(outPuts);
        Integer i = new Integer(7);
        Long l = new Long(7);
        Short s = (short) 7;
        Float f = new Float(7.7f);
        Double d = new Double(7.7d);
        Assert.assertEquals(outPuts, withLotsMethods.methodOfDataObjects(i, l, s, false, f, d));
    }

    @Test
    public void testMethodOfByte() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfByte", Matchers.any(Byte.class)).thenReturn(
                outPuts);
        Assert.assertEquals(outPuts, withLotsMethods.methodOfByte((byte) 'a'));
    }

    @Test
    public void testMethodOfChar() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfChar", Matchers.any(char.class)).thenReturn(
                outPuts);
        Assert.assertEquals(outPuts, withLotsMethods.methodOfChar('a'));
    }

    @Test
    public void testMethodOfClass() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfClass", Matchers.any(MyClass.class))
                .thenReturn(outPuts);
        Assert.assertEquals(outPuts, withLotsMethods.methodOfClass(new MyClass()));
    }

    @Test
    public void testMethodOfListClass() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfListClass", Matchers.anyListOf(MyClass.class))
                .thenReturn(outPuts);

        List<MyClass> list = new ArrayList<>();
        list.add(new MyClass());

        Assert.assertEquals(outPuts, withLotsMethods.methodOfListClass(list));
    }

    @Test
    public void testMethodOfSetClass() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfSetClass", Matchers.anySetOf(MyClass.class))
                .thenReturn(outPuts);

        Set<MyClass> set = new HashSet<>();
        set.add(new MyClass());
        Assert.assertEquals(outPuts, withLotsMethods.methodOfSetClass(set));
    }

    @Test
    public void testMethodOfMapClass() throws Exception {
        String outPuts = "Mock to call method of String";
        PowerMockito.when(withLotsMethods, "methodOfMapClass",
                Matchers.anyMapOf(MyClass.class, MyClass.class)).thenReturn(outPuts);
        Map<MyClass, MyClass> map = new HashMap<>();
        map.put(new MyClass(), new MyClass());
        Assert.assertEquals(outPuts, withLotsMethods.methodOfMapClass(map));
    }

}

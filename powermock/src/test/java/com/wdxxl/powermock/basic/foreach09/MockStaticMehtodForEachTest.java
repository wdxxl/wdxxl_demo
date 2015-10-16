package com.wdxxl.powermock.basic.foreach09;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class MockStaticMehtodForEachTest {

    @Test
    @PrepareForTest(StaticMethodReturnList.class)
    public void testForEach() {
        // Build for forEach iterator mock
        List<String> mockList = PowerMockito.mock(List.class);
        Iterator<String> mockIter = PowerMockito.mock(Iterator.class);

        PowerMockito.when(mockList.size()).thenReturn(2);
        PowerMockito.when(mockList.iterator()).thenReturn(mockIter);
        PowerMockito.when(mockIter.hasNext()).thenReturn(true, true, true, false);
        PowerMockito.when(mockIter.next()).thenReturn("mockIter 1", "mockIter 2", "mockIter 3");

        PowerMockito.mockStatic(StaticMethodReturnList.class);
        PowerMockito.when(StaticMethodReturnList.getList()).thenReturn(mockList);

        CallStaticMethodWithList callStaticMethodWithList = Mockito.spy(CallStaticMethodWithList.class);
        callStaticMethodWithList.runForEach();
    }
}

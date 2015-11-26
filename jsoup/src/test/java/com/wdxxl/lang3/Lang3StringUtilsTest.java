package com.wdxxl.lang3;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

public class Lang3StringUtilsTest {

    @Test
    public void testUnescapeHtml4() {
        String description = StringEscapeUtils
                .unescapeHtml4("<![CDATA[<name><first>Bill</first><last>Gates</last></name>]]>");
        description.replaceAll("\\s+", " ");
        System.out.println(description);
    }
}

package com.wdxxl.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

public class ParsingImplicitUlTest {

    @Test
    public void testImplicitTag() {
        String expected = "<ul> <li>Cotton.</li></ul>";

        String htmlLi = "<li>Cotton.</li>";
        Assert.assertEquals(expected, ifEndWithLi(htmlLi));

        String htmlUl = "<ul><li>Cotton.</li>";
        Assert.assertEquals(expected, implicitTag(htmlUl));

        String htmlUlLi = "<ul><li>Cotton.</l";
        Assert.assertEquals(expected, implicitTag(htmlUlLi));
    }

    /*
     * private static void implicitTableTag() { String html = "<td>Table data</td>"; Document doc =
     * Jsoup.parseBodyFragment(html); System.out.println(doc.body().html().toString()); }
     */
    private String implicitTag(String source) {
        String result;
        Document doc = Jsoup.parse(source);
        result = StringUtils.repalceAllSeparate(doc.body().html().toString());
        return result;
    }

    private String ifEndWithLi(String source) {
        String result = source;
        if (source.endsWith("</li>")) {
            result = implicitTag(new StringBuilder("<ul>").append(source).toString());
        }
        return result;
    }

}

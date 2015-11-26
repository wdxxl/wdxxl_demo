package com.wdxxl.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

public class ParsingImplicitPtagTest {

    @Test
    public void testImplicitPTag() {
        String expected = "<p>Lorm </p><p>Ipsum</p>";

        String htmlP = "<p>Lorm <p>Ipsum";
        Assert.assertEquals(expected, unClosedTag(htmlP));
    }

    private String unClosedTag(String source) {
        String result;
        Document doc = Jsoup.parse(source);
        result = StringUtils.repalceAllSeparate(doc.body().html().toString());
        return result;
    }
}

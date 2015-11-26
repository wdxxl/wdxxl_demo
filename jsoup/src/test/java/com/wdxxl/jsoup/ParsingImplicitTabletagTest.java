package com.wdxxl.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;

public class ParsingImplicitTabletagTest {

    @Test
    public void testImplicitTableTag() {
        String expected = "Table data";
        String htmlP = "<td>Table data</td>";
        Assert.assertEquals(expected, unClosedTag(htmlP));

        String expected2 = "<table> <tbody>  <tr>   <td>Table data</td>  </tr> </tbody></table>";
        String htmlTable = "<table><td>Table data</td>";
        Assert.assertEquals(expected2, unClosedTag(htmlTable));
    }

    private String unClosedTag(String source) {
        String result;
        Document doc = Jsoup.parse(source);
        result = StringUtils.repalceAllSeparate(doc.body().html().toString());
        return result;
    }
}

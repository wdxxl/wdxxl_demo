package com.wdxxl.xml.sax.product.main;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.wdxxl.xml.sax.product.handler.ProductProcessorImpl;
import com.wdxxl.xml.sax.product.handler.ProductSaxHandlerWithProcessor;

import org.junit.Test;
import org.xml.sax.SAXException;

public class ProductSaxHandlerWithProcessorTest {

    @Test
    public void testBurberry() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            ProductSaxHandlerWithProcessor handler =
                    new ProductSaxHandlerWithProcessor(new ProductProcessorImpl());
            saxParser.parse(new File(System.getProperty("user.dir")
                    + "/src/test/resources/burberry_usa.xml"), handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}

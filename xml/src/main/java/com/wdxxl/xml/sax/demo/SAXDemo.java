package com.wdxxl.xml.sax.demo;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDemo {
    public static void main(String[] argv) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser parser = factory.newSAXParser();
        SaxHandler handler = new SaxHandler();
        parser.parse(new File(System.getProperty("user.dir") + "/src/test/resources/sample.xml"),
                handler);
    }
}


class SaxHandler extends DefaultHandler {
    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        if (systemId.equals("http://www.my-company.com/order-1.0.dtd")) {
            return new InputSource(getClass().getResourceAsStream("order.dtd"));
        } else {
            return null;
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attrs)
            throws SAXException {
        if (qName.equals("order")) {
        }
    }

    @Override
    public void error(SAXParseException ex) throws SAXException {
        System.out.println("ERROR: [at " + ex.getLineNumber() + "] " + ex);
    }

    @Override
    public void fatalError(SAXParseException ex) throws SAXException {
        System.out.println("FATAL_ERROR: [at " + ex.getLineNumber() + "] " + ex);
    }

    @Override
    public void warning(SAXParseException ex) throws SAXException {
        System.out.println("WARNING: [at " + ex.getLineNumber() + "] " + ex);
    }
}
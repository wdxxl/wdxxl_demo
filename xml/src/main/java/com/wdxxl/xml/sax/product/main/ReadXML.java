package com.wdxxl.xml.sax.product.main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wdxxl.xml.sax.product.handler.SaxProductHandler;
import com.wdxxl.xml.sax.product.model.Product;

import org.xml.sax.SAXException;

public class ReadXML {
    public static void main(String args[]) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxProductHandler handler = new SaxProductHandler();
            saxParser
                    .parse(new File(System.getProperty("user.dir") + "/lib/products.xml"),
                    handler);
            List<Product> productList = handler.getProductList();
            System.out.println(productList.size());
            for (Product prod : productList) {
                String jsonString = JSON.toJSONString(prod, SerializerFeature.UseSingleQuotes);
                System.out.println(jsonString);
                Product parsedProduct = JSON.parseObject(jsonString, Product.class);
                System.out.println(parsedProduct.getBrand());
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

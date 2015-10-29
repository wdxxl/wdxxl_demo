package com.wdxxl.xml.sax.product.main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wdxxl.xml.sax.product.handler.SaxProductHandler;
import com.wdxxl.xml.sax.product.model.Product;
import com.wdxxl.xml.sax.product.model.SplitProductInStock;

import org.apache.commons.beanutils.BeanUtils;
import org.xml.sax.SAXException;

public class ReadXML {
    public static void main(String args[]) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SaxProductHandler handler = new SaxProductHandler();
            saxParser.parse(new File(System.getProperty("user.dir") + "/lib/burberry_usa.xml"),
                    handler);
            List<Product> productList = handler.getProductList();
            System.out.println(productList.size());
            for (Product product : productList) {
                String jsonString = JSON.toJSONString(product, SerializerFeature.UseSingleQuotes);
                // System.out.println(jsonString);
                // Product parsedProduct = JSON.parseObject(jsonString, Product.class);
                // System.out.println(parsedProduct.getBrand());

                SplitProductInStock split = new SplitProductInStock();
                try {
                    BeanUtils.copyProperties(split, product);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < product.getInStock().size(); i++) {
                    split.setProductColor(product.getInStock().get(i).getColor());
                    split.setColorImageURL(product.retrieveColor(product.getInStock().get(i)
                            .getColor()));
                    split.setSku(product.getInStock().get(i).getSku());
                    split.setInStockSize(product.getInStock().get(i).getSize());
                    split.setInStockColor(product.getInStock().get(i).getColor());
                    split.setInStockPrice(product.getInStock().get(i).getPrice());

                    jsonString = JSON.toJSONString(split, SerializerFeature.UseSingleQuotes);
                    System.out.println(jsonString);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

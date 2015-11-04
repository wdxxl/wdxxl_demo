package com.wdxxl.xml.sax.product.main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdxxl.xml.sax.product.handler.ProductSaxHandler;
import com.wdxxl.xml.sax.product.model.Product;
import com.wdxxl.xml.sax.product.model.SplitProductInStock;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

public class ProductSaxHandlerTest {

    @Test
    public void testBurberry() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            ProductSaxHandler handler = new ProductSaxHandler();
            saxParser.parse(new File(System.getProperty("user.dir")
                    + "/src/test/resources/burberry_usa.xml"), handler);
            List<String> productJsonList = handler.getProductJsonList();
            Assert.assertEquals(3, productJsonList.size());

            ObjectMapper mapper = new ObjectMapper();
            int count = 0;
            for (String productStr : productJsonList) {
                Product product = mapper.readValue(productStr, Product.class);
                SplitProductInStock split = new SplitProductInStock();
                try {
                    BeanUtils.copyProperties(split, product);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < product.getInStock().size(); i++) {
                    split.setProductColor(product.getInStock().get(i).getColor());
                    split.setColorImageUrl(product.retrieveColor(product.getInStock().get(i)
                            .getColor()));
                    split.setSku(product.getInStock().get(i).getSku());
                    split.setInStockSize(product.getInStock().get(i).getSize());
                    split.setInStockColor(product.getInStock().get(i).getColor());
                    split.setInStockPrice(product.getInStock().get(i).getPrice());

                    count++;
                }
            }
            Assert.assertEquals(11, count);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

}

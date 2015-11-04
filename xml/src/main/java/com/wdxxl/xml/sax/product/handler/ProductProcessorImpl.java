package com.wdxxl.xml.sax.product.handler;

import java.lang.reflect.InvocationTargetException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdxxl.xml.sax.product.model.Product;
import com.wdxxl.xml.sax.product.model.SplitProductInStock;

import org.apache.commons.beanutils.BeanUtils;

public class ProductProcessorImpl implements ProductSaxHandlerWithProcessor.ProductProcessor {

    @Override
    public void processProduct(Product product) {
        SplitProductInStock split = new SplitProductInStock();
        try {
            BeanUtils.copyProperties(split, product);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < product.getInStock().size(); i++) {
            split.setProductColor(product.getInStock().get(i).getColor());
            split.setColorImageUrl(product.retrieveColor(product.getInStock().get(i).getColor()));
            split.setSku(product.getInStock().get(i).getSku());
            split.setInStockSize(product.getInStock().get(i).getSize());
            split.setInStockColor(product.getInStock().get(i).getColor());
            split.setInStockPrice(product.getInStock().get(i).getPrice());

            parsingToJson(split);
        }
    }

    // TODO write SplitProductInStock into database
    private void parsingToJson(SplitProductInStock split) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(split);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

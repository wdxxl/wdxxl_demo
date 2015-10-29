package com.wdxxl.xml.sax.product.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wdxxl.xml.sax.product.model.AlternateImages;
import com.wdxxl.xml.sax.product.model.Category;
import com.wdxxl.xml.sax.product.model.Color;
import com.wdxxl.xml.sax.product.model.InStock;
import com.wdxxl.xml.sax.product.model.Product;
import com.wdxxl.xml.sax.product.model.ProductURL;
import com.wdxxl.xml.sax.product.model.SplitProductInStock;

import org.apache.commons.beanutils.BeanUtils;

public class WriteToJSON {
    public static void main(String[] args) {
        Product product = new Product();
        product.setRetailer("Burberry");
        product.setProductId("Grainy Leather Pencil Sleeve-10000042769");
        product.setName("Burberry Grainy Leather Pencil Sleeve");
        product.setBrand("Burberry");
        product.setPrice("$ 150.00");
        product.setDescription("An Italian-crafted grainy leather pencil sleeve filled with three traditional Derwent pencils made in the Lake District, England. The design is finished with a soft faux suede lining, tonal topstitch and hand-painted edges.");
        List<ProductURL> productURL = new ArrayList<>();
        productURL
                .add(new ProductURL(
                        "feed",
                        "http://click.pump.to/fm-d0123/TY9Ba8MwDIX~StA5ieOmo4sPhdGOMdjGoFdfXEdZTB3HyM4gjP33Od6lp4ee9OlJP7CQBQFjjF5IJpmnoR6dZNoafUuiJsJBcN5w256fJYurR6G9lqzHEI1T0cxObHjI~BLq60JXJFprPU-SfZEybq0sqjgiVR6dNrYKFvEbK992h7bjnEMJAwi-a0swSfePhxI8iD1PqpNRggPRJY9Oc4~p4DvQ04eaNu8lRxVv~1HFZ44qLjkqzxmdxvhDk~eomJB3dMWxeNIaQ5jJYEjVJeankNZUnDHc7vvboT6xrz2I5vcP"));
        productURL.add(new ProductURL("direct",
                "https://us.burberry.com/grainy-leather-pencil-sleeve-p39739111"));
        product.setProductURL(productURL);

        product.setImageURL("https://assets.burberry.com/is/image/Burberryltd/96f2454f1a9bd5e4870081157540d6af00fb60c7?$prod_zoom$");

        List<String> alternateImages = new ArrayList<>();
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/8f5d6d34de6fe989a720b11c9a8783812b2ba6b4?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/2d5dd251843771a6c34ab463f59847ff7113608d?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/bbc896011c8b744f5a62a8b37306f4b329130f31?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/96f2454f1a9bd5e4870081157540d6af00fb60c7?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/8829fb8ecea73300e3b7934fb9a3da554f98f039?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/b87ce912ac2df7ca2f9c11f90c3f9c477e647a3c?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/5d280c706873a43358911ea28144989c2836e9a1?$prod_main$");
        alternateImages
                .add("https://assets.burberry.com/is/image/Burberryltd/e888c69c4ec19bd3dab36971312783ce7a615e7e?$prod_main$");
        product.setAlternateImages(new AlternateImages(alternateImages));

        List<Category> categories = new ArrayList<>();
        List<String> part = new ArrayList<>();
        part.add("Men");
        part.add("Accessories");
        part.add("Stationery");
        part.add("Desk Accessories");
        categories.add(new Category(part));
        product.setCategories(categories);

        List<String> sizes = new ArrayList<>();
        sizes.add("One Size");
        product.setSize(sizes);

        List<Color> colors = new ArrayList<>();
        colors.add(new Color(
                "Tan",
                "https://assets.burberry.com/is/image/Burberryltd/96f2454f1a9bd5e4870081157540d6af00fb60c7?$prod_zoom$"));
        colors.add(new Color(
                "Ebony Red",
                "https://assets.burberry.com/is/image/Burberryltd/e888c69c4ec19bd3dab36971312783ce7a615e7e?$prod_zoom$"));
        product.setColor(colors);

        List<InStock> inStocks = new ArrayList<>();
        inStocks.add(new InStock("1271289941", "One Size", "Tan", "$ 150.00"));
        inStocks.add(new InStock("438568871", "One Size", "Ebony Red", "$ 150.00"));
        product.setInStock(inStocks);

        product.setTime("Tue, 27 Oct 2015 03:03:23 GMT");

        String jsonString = JSON.toJSONString(product, SerializerFeature.UseSingleQuotes);
        // System.out.println(jsonString);

        for (int i = 0; i < product.getInStock().size(); i++) {
            SplitProductInStock split = new SplitProductInStock();
            try {
                BeanUtils.copyProperties(split, product);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            split.setProductColor(product.getInStock().get(i).getColor());
            split.setColorImageURL(product.retrieveColor(product.getInStock().get(i).getColor()));
            split.setSku(product.getInStock().get(i).getSku());
            split.setInStockSize(product.getInStock().get(i).getSize());
            split.setInStockColor(product.getInStock().get(i).getColor());
            split.setInStockPrice(product.getInStock().get(i).getPrice());

            jsonString = JSON.toJSONString(split, SerializerFeature.UseSingleQuotes);
            System.out.println(jsonString);
        }



        // Product parsedProduct = JSON.parseObject(jsonString, Product.class);
        // System.out.println(parsedProduct.getBrand());

    }
}

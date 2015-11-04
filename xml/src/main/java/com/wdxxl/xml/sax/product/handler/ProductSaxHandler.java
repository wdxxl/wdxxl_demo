package com.wdxxl.xml.sax.product.handler;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdxxl.xml.sax.product.model.Color;
import com.wdxxl.xml.sax.product.model.InStock;
import com.wdxxl.xml.sax.product.model.Product;
import com.wdxxl.xml.sax.product.model.ProductUrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class ProductSaxHandler extends DefaultHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ProductSaxHandler.class);
    private static ObjectMapper mapper = new ObjectMapper();

    private List<String> productJsonList = null;
    private Product product = null;
    private List<String> alternateImages = null;
    private List<String> categories = null;
    private Color color = null;
    private InStock inStock = null;
    private ProductUrl productUrl = null;

    private final StringBuilder builder = new StringBuilder();
    private String productUrlType;

    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attrs)
            throws SAXException {
        if ("Product".equals(qualifiedName)) {
            product = new Product();
            if (productJsonList == null) {
                productJsonList = new ArrayList<>();
            }
            alternateImages = null;
            categories = null;
            color = null;
            inStock = null;
            productUrl = null;
        } else if ("Category".equals(qualifiedName)) {
            categories = new ArrayList<>();
        } else if ("AlternateImages".equals(qualifiedName)) {
            alternateImages = new ArrayList<>();
        } else if ("InStock".equals(qualifiedName)) {
            inStock = new InStock();
        } else if ("Color".equals(qualifiedName)) {
            color = new Color();
        } else if ("ProductURL".equals(qualifiedName)) {
            productUrl = new ProductUrl();
            productUrlType = attrs.getValue("type");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        builder.append(ch, start, length);
    }

    private String accumulatedString() {
        String str = builder.toString().trim();
        builder.delete(0, builder.length());
        return str;
    }

    @Override
    public void endElement(String uri, String localName, String qualifiedName) throws SAXException {
        if ("Retailer".equals(qualifiedName)) {
            String retailer = accumulatedString();
            product.setRetailer(retailer);
        } else if ("ProductId".equals(qualifiedName)) {
            String productId = accumulatedString();
            product.setProductId(productId);
        } else if ("Name".equals(qualifiedName)) {
            String name = accumulatedString();
            if (color != null) {
                color.setName(name);
            } else {
                product.setName(name);
            }
        } else if ("Brand".equals(qualifiedName)) {
            String brand = accumulatedString();
            product.setBrand(brand);
        } else if ("Price".equals(qualifiedName)) {
            String price = accumulatedString();
            if (inStock == null) {
                product.setPrice(price);
            } else {
                inStock.setPrice(price);
            }
        } else if ("Description".equals(qualifiedName)) {
            String description = accumulatedString();
            product.setDescription(description);
        } else if ("ProductURL".equals(qualifiedName)) {
            String imageUrl = accumulatedString();
            productUrl.setImageUrl(imageUrl);
            productUrl.setType(productUrlType);
            product.appendProductUrl(productUrl);
            productUrl = null;
        } else if ("ImageURL".equals(qualifiedName)) {
            String imageUrl = accumulatedString();
            if (color != null) {
                color.setImageUrl(imageUrl);
            } else if (alternateImages != null) {
                alternateImages.add(imageUrl);
            } else {
                product.setImageUrl(imageUrl);
            }
        } else if ("AlternateImages".equals(qualifiedName)) {
            product.appendAlternateImages(alternateImages);
            alternateImages = null;
        } else if ("Category".equals(qualifiedName)) {
            product.appendCategory(categories);
            categories = null;
        } else if ("Part".equals(qualifiedName)) {
            String part = accumulatedString();
            if (categories != null) {
                categories.add(part);
            }
        } else if ("Size".equals(qualifiedName)) {
            String size = accumulatedString();
            if (inStock != null) {
                inStock.setSize(size);
            } else {
                product.appendSize(size);
            }
        } else if ("Color".equals(qualifiedName)) {
            if (inStock != null) {
                String color = accumulatedString();
                inStock.setColor(color);
            } else {
                product.appendColor(color);
                color = null;
            }
        } else if ("InStock".equals(qualifiedName)) {
            product.appendInStock(inStock);
            inStock = null;
        } else if ("SKU".equals(qualifiedName)) {
            String sku = accumulatedString();
            inStock.setSku(sku);
        } else if ("Time".equals(qualifiedName)) {
            String time = accumulatedString();
            product.setTime(time);
        } else if ("Product".equals(qualifiedName)) {
            productJsonList.add(parsingProductToJson(product));
            product = null;
        }
    }

    public List<String> getProductJsonList() {
        return productJsonList;
    }

    public String parsingProductToJson(Product product) {
        String json = null;
        try {
            json = mapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }


    @Override
    public void error(SAXParseException e) throws SAXException {
        LOG.error("ERROR while parsing Product XML" + e);
    }
}

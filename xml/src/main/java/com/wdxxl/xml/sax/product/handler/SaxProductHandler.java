package com.wdxxl.xml.sax.product.handler;

import java.util.ArrayList;
import java.util.List;

import com.wdxxl.xml.sax.product.model.AlternateImages;
import com.wdxxl.xml.sax.product.model.Category;
import com.wdxxl.xml.sax.product.model.Color;
import com.wdxxl.xml.sax.product.model.InStock;
import com.wdxxl.xml.sax.product.model.Product;
import com.wdxxl.xml.sax.product.model.ProductURL;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxProductHandler extends DefaultHandler {

    private List<Product> productList = null;
    private Product product = null;
    private AlternateImages alternateImages = null;
    private Category category = null;
    private Color color = null;
    private InStock inStock = null;
    private ProductURL productURL = null;

    private boolean bRetailer = false;
    private boolean bProductId = false;
    private boolean bName = false;
    private boolean bBrand = false;
    private boolean bPrice = false;
    private boolean bDescription = false;
    private boolean bProductURL = false;
    private boolean bImageURL = false;
    private boolean bAlternateImages = false;
    private boolean bCategory = false;
    private boolean bPart = false;
    private boolean bSize = false;
    private boolean bColor = false;
    private boolean bInStock = false;
    private boolean bSKU = false;
    private boolean bTime = false;

    private String productURLTempType;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("Product")) {
            product = new Product();
            if (productList == null) {
                productList = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("Retailer")) {
            bRetailer = true;
        } else if (qName.equalsIgnoreCase("ProductId")) {
            bProductId = true;
        } else if (qName.equalsIgnoreCase("Name")) {
            bName = true;
        } else if (qName.equalsIgnoreCase("Brand")) {
            bBrand = true;
        } else if (qName.equalsIgnoreCase("Price")) {
            bPrice = true;
        } else if (qName.equalsIgnoreCase("Description")) {
            bDescription = true;
        } else if (qName.equalsIgnoreCase("ProductURL")) {
            productURLTempType = attributes.getValue("type");
            bProductURL = true;
        } else if (qName.equalsIgnoreCase("ImageURL")) {
            bImageURL = true;
        } else if (qName.equalsIgnoreCase("AlternateImages")) {
            alternateImages = new AlternateImages();
            bAlternateImages = true;
        } else if (qName.equalsIgnoreCase("Category")) {
            bCategory = true;
        } else if (qName.equalsIgnoreCase("Part")) {
            bPart = true;
        } else if (qName.equalsIgnoreCase("Size")) {
            bSize = true;
        } else if (qName.equalsIgnoreCase("Color")) {
            color = new Color();
            bColor = true;
        } else if (qName.equalsIgnoreCase("InStock")) {
            inStock = new InStock();
            bInStock = true;
        } else if (qName.equalsIgnoreCase("SKU")) {
            bSKU = true;
        } else if (qName.equalsIgnoreCase("Time")) {
            bTime = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String converted = new String(ch, start, length);
        if (bRetailer) {
            product.setRetailer(converted);
            bRetailer = false;
        } else if (bProductId) {
            product.setProductId(converted);
            bProductId = false;
        } else if (bName) {
            if (bColor) {
                color.setName(converted);
            } else {
                product.setName(converted);
            }
            bName = false;
        } else if (bBrand) {
            product.setBrand(converted);
            bBrand = false;
        } else if (bPrice) {
            if (bInStock) {
                inStock.setPrice(converted);
            } else {
                product.setPrice(converted);
            }
            bPrice = false;
        } else if (bDescription) {
            product.setDescription(converted);
            bDescription = false;
        } else if (bProductURL) {
            productURL = new ProductURL(productURLTempType, converted);
            product.appendProductURL(productURL);
            bProductURL = false;
        } else if (bImageURL) {
            if (bColor) {
                color.setImageURL(converted);
            } else if (bAlternateImages) {
                alternateImages.appendImageURL(converted);
            } else {
                product.setImageURL(converted);
            }
            bImageURL = false;
        } else if (bCategory) {
            category = new Category();
            bCategory = false;
        } else if (bPart) {
            if (category != null) {
                category.appendPart(converted);
            }
            bPart = false;
        } else if (bSize) {
            if (bInStock) {
                inStock.setSize(converted);
            } else {
                product.appendSize(converted);
            }
            bSize = false;
        } else if (bColor) {
            if (bInStock) {
                inStock.setColor(converted);
                bColor = false;
            }
        } else if (bSKU) {
            inStock.setSku(converted);
            bSKU = false;
        } else if (bTime) {
            product.setTime(converted);
            bTime = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("Category")) {
            product.appendCategory(category);
            category = null;
        } else if (qName.equalsIgnoreCase("AlternateImages")) {
            product.setAlternateImages(alternateImages);
            alternateImages = null;
            bAlternateImages = false;
        } else if (qName.equalsIgnoreCase("InStock")) {
            product.appendInStock(inStock);
            inStock = null;
        } else if (qName.equalsIgnoreCase("Color")) {
            if (!bInStock) {
                product.appendColor(color);
                color = null;
                bColor = false;
            }
        } else if (qName.equalsIgnoreCase("ProductURL")) {
            product.appendProductURL(productURL);
            productURL = null;
        } else if (qName.equalsIgnoreCase("Product")) {
            productList.add(product);
            product = null;
            falseAllFlag();
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    private void falseAllFlag() {
        bRetailer = false;
        bProductId = false;
        bName = false;
        bBrand = false;
        bPrice = false;
        bDescription = false;
        bProductURL = false;
        bImageURL = false;
        bAlternateImages = false;
        bCategory = false;
        bPart = false;
        bSize = false;
        bColor = false;
        bInStock = false;
        bSKU = false;
        bTime = false;
    }
}

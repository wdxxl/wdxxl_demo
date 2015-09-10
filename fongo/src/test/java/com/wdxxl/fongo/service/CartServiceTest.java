package com.wdxxl.fongo.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.github.fakemongo.junit.FongoRule;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import com.wdxxl.fongo.model.Cart;
import com.wdxxl.fongo.model.CartItem;
import com.wdxxl.fongo.model.Cartlet;
import com.wdxxl.fongo.model.Coupon;
import com.wdxxl.fongo.model.ShippingAddress;

public class CartServiceTest {
	@Rule
	public FongoRule fongoRule = new FongoRule(false); // if true it will create
														// real.
	private DBCollection collection;

	@Before
	public void setUp() throws Exception {
		collection = fongoRule.getDB("fongo").getCollection("carts");

		CartItem cartItem1 = new CartItem("1", "CartItem1");
		CartItem cartItem2 = new CartItem("2", "CartItem2");
		List<CartItem> cartItemList1 = new ArrayList<CartItem>();
		cartItemList1.add(cartItem1);
		cartItemList1.add(cartItem2);

		CartItem cartItem3 = new CartItem("3", "CartItem3");
		CartItem cartItem4 = new CartItem("4", "CartItem4");
		List<CartItem> cartItemList2 = new ArrayList<CartItem>();
		cartItemList1.add(cartItem3);
		cartItemList1.add(cartItem4);

		Coupon coupon1 = new Coupon("1", "coupon1");
		Coupon coupon2 = new Coupon("2", "coupon2");

		Cartlet cartlet1 = new Cartlet("1", "cartlet1", cartItemList1, coupon1);
		Cartlet cartlet2 = new Cartlet("2", "cartlet2", cartItemList2, coupon2);
		List<Cartlet> cartlets = new ArrayList<Cartlet>();
		cartlets.add(cartlet1);
		cartlets.add(cartlet2);

		ShippingAddress address = new ShippingAddress("1", "address");
		Cart cart = new Cart("1", "cart1", cartlets, address);

		// remove decoder&encoder just use JSON string parse to DBObject.
		DBObject dbObject = (DBObject) JSON.parse(com.alibaba.fastjson.JSON
				.toJSONString(cart));
		collection.insert(dbObject);
	}

	@Test
	public void testJSON() {
		BasicDBObject query = new BasicDBObject("cartId", "1");
        try {
            DBObject dbCartQuery = collection.findOne(query);
            if (dbCartQuery != null) {
            	BasicDBObject result = (BasicDBObject) dbCartQuery;
            	System.out.println(result.toString());
            	//Looks like still need decoder. (convert DBObject to Cart)
            }
        } catch (MongoException e) {
        	
        }
    }
}

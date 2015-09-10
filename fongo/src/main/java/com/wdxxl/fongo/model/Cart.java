package com.wdxxl.fongo.model;

import java.util.List;

public class Cart {
	private String _id;
	private String cartId;
	private String cartName;
	private List<Cartlet> cartlets;
	private ShippingAddress shippingAddress;
	
	public Cart() {
		super();
	}

	public Cart(String cartId, String cartName, List<Cartlet> cartlets,
			ShippingAddress shippingAddress) {
		super();
		this.cartId = cartId;
		this.cartName = cartName;
		this.cartlets = cartlets;
		this.shippingAddress = shippingAddress;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getCartName() {
		return cartName;
	}

	public void setCartName(String cartName) {
		this.cartName = cartName;
	}

	public List<Cartlet> getCartlets() {
		return cartlets;
	}

	public void setCartlets(List<Cartlet> cartlets) {
		this.cartlets = cartlets;
	}

	public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}
	
}
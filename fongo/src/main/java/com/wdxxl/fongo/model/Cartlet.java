package com.wdxxl.fongo.model;

import java.util.List;

public class Cartlet {
	private String cartletId;
	private String cartletName;
	private List<CartItem> cartItems;
	private Coupon coupon;

	public Cartlet(String cartletId, String cartletName,
			List<CartItem> cartItems, Coupon coupon) {
		super();
		this.cartletId = cartletId;
		this.cartletName = cartletName;
		this.cartItems = cartItems;
		this.coupon = coupon;
	}

	public String getCartletId() {
		return cartletId;
	}

	public void setCartletId(String cartletId) {
		this.cartletId = cartletId;
	}

	public String getCartletName() {
		return cartletName;
	}

	public void setCartletName(String cartletName) {
		this.cartletName = cartletName;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}

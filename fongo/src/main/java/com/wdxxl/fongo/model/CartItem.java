package com.wdxxl.fongo.model;

public class CartItem {
	private String cartItemId;
	private String cartItemName;

	public CartItem(String cartItemId, String cartItemName) {
		super();
		this.cartItemId = cartItemId;
		this.cartItemName = cartItemName;
	}

	public String getCartItemId() {
		return cartItemId;
	}

	public void setCartItemId(String cartItemId) {
		this.cartItemId = cartItemId;
	}

	public String getCartItemName() {
		return cartItemName;
	}

	public void setCartItemName(String cartItemName) {
		this.cartItemName = cartItemName;
	}

}

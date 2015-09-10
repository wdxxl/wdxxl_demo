package com.wdxxl.fongo.model;

public class Coupon {
	private String couponId;
	private String couponName;

	public Coupon(String couponId, String couponName) {
		super();
		this.couponId = couponId;
		this.couponName = couponName;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

}

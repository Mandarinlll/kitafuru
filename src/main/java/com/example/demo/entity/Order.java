package com.example.demo.entity;

import java.sql.Timestamp;

public class Order {

	// 注文ID
	private int id;

	// ユーザーID
	private int userId;

	// 合計金額
	private int totalPrice;

	// 支払い方法
	private String paymentMethod;

	// 配送先住所
	private String shippingAddress;

	// 注文日時
	private Timestamp orderAt;

	// ===== Getter / Setter =====

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Timestamp getOrderAt() {
		return orderAt;
	}

	public void setOrderAt(Timestamp orderAt) {
		this.orderAt = orderAt;
	}
}
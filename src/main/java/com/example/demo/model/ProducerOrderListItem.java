package com.example.demo.model;

import java.time.LocalDateTime;

public class ProducerOrderListItem {
	private int id;
	private LocalDateTime orderAt;
	private String customerName;
	private String customerEmail;
	private String productSummary;
	private int totalQuantity;
	private int totalPrice;
	private String paymentMethod;
	private String shippingAddress;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return "#HKD-" + String.format("%06d", id);
	}

	public LocalDateTime getOrderAt() {
		return orderAt;
	}

	public void setOrderAt(LocalDateTime orderAt) {
		this.orderAt = orderAt;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getProductSummary() {
		return productSummary;
	}

	public void setProductSummary(String productSummary) {
		this.productSummary = productSummary;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusLabel() {
		if ("preparing".equals(status)) {
			return "準備中";
		}
		if ("shipped".equals(status)) {
			return "出荷済";
		}
		if ("delivered".equals(status)) {
			return "配送完了";
		}
		if ("cancelled".equals(status)) {
			return "キャンセル";
		}
		return "新規受注";
	}

	public String getStatusClass() {
		if (status == null || status.isBlank()) {
			return "status-new";
		}
		return "status-" + status;
	}

	public String getPaymentMethodLabel() {
		if ("credit_card".equals(paymentMethod)) {
			return "クレジットカード";
		}
		if ("bank_transfer".equals(paymentMethod)) {
			return "銀行振込";
		}
		if ("convenience_store".equals(paymentMethod)) {
			return "コンビニ払い";
		}
		if ("cash_on_delivery".equals(paymentMethod)) {
			return "代金引換";
		}
		return paymentMethod;
	}
}

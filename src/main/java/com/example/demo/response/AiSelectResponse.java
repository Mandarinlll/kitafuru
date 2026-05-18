package com.example.demo.response;

import java.util.List;

public class AiSelectResponse {
	private String message;
	private List<Integer> productIds;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}

}

package com.example.demo.entity;

public class ProductRelation {

	// 関連商品ID
	private int id;

	// 商品ID
	private int productId;

	// 関連商品ID
	private int relatedProductId;

	// ===== Getter / Setter =====

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getRelatedProductId() {
		return relatedProductId;
	}

	public void setRelatedProductId(int relatedProductId) {
		this.relatedProductId = relatedProductId;
	}
}
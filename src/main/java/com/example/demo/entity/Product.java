package com.example.demo.entity;

import java.sql.Timestamp;

public class Product {

	// 商品ID
	private int id;

	// 生産者ID
	private int producerId;

	// 商品名
	private String name;

	// 商品説明
	private String body;

	// 価格
	private int price;

	// 在庫数
	private int stock;

	// カテゴリID
	private int categoryId;

	// 産地
	private String originArea;

	// 商品画像
	private String image;

	// おすすめスコア
	private double recommendationScore;

	// 作成日時
	private Timestamp createAt;

	// 更新日時
	private Timestamp updateAt;
	private String categoryName;

	// ===== Getter / Setter =====

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProducerId() {
		return producerId;
	}

	public void setProducerId(int producerId) {
		this.producerId = producerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getOriginArea() {
		return originArea;
	}

	public void setOriginArea(String originArea) {
		this.originArea = originArea;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public double getRecommendationScore() {
		return recommendationScore;
	}

	public void setRecommendationScore(double recommendationScore) {
		this.recommendationScore = recommendationScore;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}

	public Timestamp getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Timestamp updateAt) {
		this.updateAt = updateAt;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
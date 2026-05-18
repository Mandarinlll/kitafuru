package com.example.demo.model;

import java.time.LocalDateTime;

public class ProducerProductListItem {
	private int id;
	private String name;
	private int price;
	private int stock;
	private int categoryId;
	private String categoryName;
	private String originArea;
	private String image;
	private LocalDateTime updateAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSku() {
		return "PRD-" + String.format("%03d", id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	public LocalDateTime getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}

	public String getStockLabel() {
		if (stock == 0) {
			return "在庫切れ";
		}
		if (stock <= 10) {
			return "在庫少";
		}
		return "公開中";
	}

	public String getStockClass() {
		if (stock == 0) {
			return "status-cancelled";
		}
		if (stock <= 10) {
			return "status-preparing";
		}
		return "status-delivered";
	}
}

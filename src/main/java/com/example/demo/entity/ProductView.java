package com.example.demo.entity;

import java.security.Timestamp;

public class ProductView {
	private int id;
	private int user_id;
	private Timestamp view_at;
	private int product;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Timestamp getView_at() {
		return view_at;
	}

	public void setView_at(Timestamp view_at) {
		this.view_at = view_at;
	}

	public int getProduct() {
		return product;
	}

	public void setProduct(int product) {
		this.product = product;
	}

}

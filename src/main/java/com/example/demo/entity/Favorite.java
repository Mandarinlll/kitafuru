package com.example.demo.entity;

import java.security.Timestamp;

public class Favorite {
	private int id;
	private int user_id;
	private int product_id;
	private Timestamp creat_at;

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

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public Timestamp getCreat_at() {
		return creat_at;
	}

	public void setCreat_at(Timestamp creat_at) {
		this.creat_at = creat_at;
	}
}

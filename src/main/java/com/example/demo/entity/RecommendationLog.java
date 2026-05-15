package com.example.demo.entity;

import java.security.Timestamp;

public class RecommendationLog {
	private int id;
	private int user_id;
	private int product_id;
	private Timestamp create_at;
	private String recommendation_reason;

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

	public Timestamp getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Timestamp create_at) {
		this.create_at = create_at;
	}

	public String getRecommendation_reason() {
		return recommendation_reason;
	}

	public void setRecommendation_reason(String recommendation_reason) {
		this.recommendation_reason = recommendation_reason;
	}
}

package com.example.demo.entity;

import java.sql.Timestamp;

public class User {

	// ユーザーID
	private int id;

	// ユーザー名
	private String name;

	// 住所
	private String address;

	// メールアドレス
	private String email;

	// パスワード
	private String password;

	// プロフィール画像
	private String image;

	// 作成日時
	private Timestamp createAt;

	// ===== Getter / Setter =====

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Timestamp getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Timestamp createAt) {
		this.createAt = createAt;
	}
}
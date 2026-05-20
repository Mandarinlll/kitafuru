package com.example.demo.form;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserForm {

	@NotBlank(message = "名前を入力してください")
	private String name;

	@NotBlank(message = "住所を入力してください")
	private String address;

	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式が正しくありません")
	private String email;

	@NotBlank(message = "パスワードを入力してください")
	private String password;

	private boolean agreedToTerms;

	@AssertTrue(message = "利用規約への同意が必要です")
	public boolean isAgreedToTerms() {
		return agreedToTerms;
	}

	public void setAgreedToTerms(boolean agreedToTerms) {
		this.agreedToTerms = agreedToTerms;
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
}
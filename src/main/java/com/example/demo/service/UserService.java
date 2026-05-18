package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;

public interface UserService {

	/**
	 * ユーザを登録する。
	 * @param form 登録フォームの入力値
	 */
	void register(UserForm form);

	/**
	 * ログイン認証を行う。
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return ログイン成功時はUser、失敗時はnull
	 */
	User login(String email, String password);
}
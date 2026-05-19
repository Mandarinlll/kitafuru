package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	// UserMapper
	private final UserMapper userMapper;

	// パスワード暗号化
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// コンストラクタインジェクション
	public UserServiceImpl(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	/**
	 * ユーザー登録
	 */
	@Override
	public void register(UserForm form) {

		User user = new User();

		user.setName(form.getName());
		user.setAddress(form.getAddress());
		user.setEmail(form.getEmail());

		// パスワードをハッシュ化して保存
		user.setPassword(passwordEncoder.encode(form.getPassword()));

		// DB登録
		userMapper.insert(user);
	}

	/**
	 * ログイン認証
	 */
	@Override
	public User login(String email, String password) {

		// メールアドレスでユーザー検索
		User user = userMapper.findByEmail(email);

		// ユーザーが存在しない
		if (user == null) {
			return null;
		}

		// パスワード一致確認
		if (passwordEncoder.matches(password, user.getPassword())) {
			return user;
		}

		// 不一致
		return null;
	}
}
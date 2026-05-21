package com.example.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Producer;
import com.example.demo.mapper.ProducerMapper;

@Service
public class ProducerLoginServiceImpl implements ProducerLoginService {
	private final ProducerMapper producerMapper;

	// パスワード暗号化
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// コンストラクタインジェクション
	public ProducerLoginServiceImpl(ProducerMapper producerMapper) {
		this.producerMapper = producerMapper;
	}

	/**
	 * ログイン認証
	 */
	@Override
	public Producer login(String email, String password) {

		// メールアドレス検索
		Producer producer = producerMapper.findByEmail(email);

		// 存在しない
		if (producer == null) {
			return null;
		}

		// パスワード一致
		if (passwordEncoder.matches(
				password,
				producer.getPassword())) {

			return producer;
		}

		// 不一致
		return null;
	}
}

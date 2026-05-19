package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	/** メールアドレスでユーザを検索する */
	User findByEmail(String email);

	/** ユーザを登録する */
	void insert(User user);
}
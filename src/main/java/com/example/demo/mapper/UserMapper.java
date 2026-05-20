package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	/** メールアドレスでユーザを検索する */
	User findByEmail(String email);

	/** ユーザを登録する */
	void insert(User user);

	/** 基本情報を更新する */
	void updateBasic(User user);

	/** 住所を更新する */
	void updateAddress(
			@Param("id") int id,
			@Param("address") String address);

	/** 支払い方法を更新する */
	void updateDefaultPayment(
			@Param("id") int id,
			@Param("defaultPayment") String defaultPayment);
}
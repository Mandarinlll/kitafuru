package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.Product;

@Mapper
public interface ProductMapper {

	// 商品一覧取得
	List<Product> findAll();

	// 季節に合った商品一覧を取得する
	// month に現在の月を渡して検索する
	List<Map<String, Object>> findAllSeason(int month);

	// 商品IDから商品情報を1件取得する
	// カート画面の商品名・価格表示などに使用
	Product findById(int id);

	// ユーザーにおすすめの商品を取得する
	// userId を元にレコメンド商品を表示
	List<Product> findRecommendProducts(Integer userId);

	// AI検索用
	// キーワードから商品検索する
	List<Product> searchForAi(String keyword);

	// AI検索用
	// キーワード + 最大価格で商品検索する
	List<Product> searchForAiWithMaxPrice(String keyword, Integer maxPrice);

	// 複数の商品IDから商品一覧を取得する
	// ids に含まれる商品をまとめて検索
	List<Product> findByIds(List<Integer> ids);

	/*
	 * 商品詳細取得
	 */
	@Select("""
			SELECT
				*
			FROM
				products
			WHERE
				id = #{id}
			""")
	Product findById(@Param("id") int id);
	
	List<Product> findTopGiftProducts();

	List<Product> findOtherGiftProducts();
}

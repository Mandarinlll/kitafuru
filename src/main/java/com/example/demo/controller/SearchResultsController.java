package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Controller
public class SearchResultsController {

	@Autowired
	private ProductMapper productMapper;

	/*
	 * 検索結果ページ
	 */
	@GetMapping("/search/result")
	public String searchResults(
			@RequestParam(name = "keyword", defaultValue = "") String keyword,
			Model model) {

		// 商品検索
		List<Product> productList = productMapper.searchByKeyword(keyword);

		// HTMLへ渡す
		model.addAttribute("productList", productList);

		// キーワードを渡す
		model.addAttribute("keyword", keyword);

		// 件数を渡す
		model.addAttribute("count", productList.size());

		return "search/results";
	}
}
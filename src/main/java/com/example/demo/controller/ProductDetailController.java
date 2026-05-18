package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * 商品詳細画面Controller
 */
@Controller
public class ProductDetailController {

	/*
	 * 商品詳細画面
	 * 
	 * /products/1
	 * /products/2
	 * のようなURLを受け取る
	 */
	@GetMapping("/products/{product_id}")
	public String showDetail(
			@PathVariable("product_id") int productId,
			Model model) {

		// HTMLへ商品IDを渡す
		model.addAttribute("productId", productId);

		// product/detail.html を表示
		return "product/detail";
	}
}
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

/*
 * 商品詳細画面Controller
 */
@Controller
public class ProductDetailController {

	private final ProductService productService;

	public ProductDetailController(ProductService productService) {
		this.productService = productService;
	}

	/*
	 * 商品詳細画面
	 */
	@GetMapping("/products/{product_id}")
	public String showDetail(
			@PathVariable("product_id") int productId,
			Model model) {

		// 商品取得
		Product product = productService.getProductById(productId);

		// HTMLへ渡す
		model.addAttribute("product", product);

		// product/detail.html
		return "product/detail";
	}
}
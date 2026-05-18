package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

/*
 * 商品一覧画面のController
 */
@Controller
public class ProductListController {

	// 商品取得用Service
	private final ProductService productService;

	// Serviceを受け取る
	public ProductListController(ProductService productService) {
		this.productService = productService;
	}

	/*
	 * /products にアクセスされた時の処理
	 */
	@GetMapping("/products")
	public String showProductList(Model model) {

		// DBから商品一覧取得
		List<Product> productList = productService.getAllProducts();

		// HTMLへ渡す
		model.addAttribute("productList", productList);

		// product/list.html を表示
		return "product/list";
	}
}
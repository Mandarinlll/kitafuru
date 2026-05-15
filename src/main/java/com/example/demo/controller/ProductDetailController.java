package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetailController {
	@GetMapping("/products/{product_id}")
	public String showDetail() {
		//product/detail.html を表示する
		return "product/detail";
	}
}

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductListController {
	@GetMapping("/products")
	public String showProductList() {
		return "product/list";
	}
}

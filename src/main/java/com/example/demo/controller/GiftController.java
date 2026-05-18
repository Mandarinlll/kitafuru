package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.ProductService;

@Controller
public class GiftController {

	private final ProductService productService;

	public GiftController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/gift")
	public String gift(Model model) {
		//人気ギフトTOP3をHTMLへ返す
		model.addAttribute(
				"topGiftProducts",
				productService.findTopGiftProducts());
		//その他のギフト商品をHTMLへ返す
		model.addAttribute(
				"otherGiftProducts",
				productService.findOtherGiftProducts());

		return "gift";
	}
}
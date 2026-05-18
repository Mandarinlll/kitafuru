package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Product;
import com.example.demo.service.AIRecommendService;

@Controller
public class AIRecommendController {

	private final AIRecommendService aiRecommendService;

	public AIRecommendController(AIRecommendService aiRecommendService) {
		this.aiRecommendService = aiRecommendService;
	}

	@GetMapping("/ai-recommend")
	public String aiRecommend(Model model) {
		//仮のユーザID
		Integer userId = 1;

		List<Product> recommendedProducts = aiRecommendService.findRecommendProducts(userId);
		model.addAttribute("recommendedProducts", recommendedProducts);
		return "ai-recommend";
	}
}

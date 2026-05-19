package com.example.demo.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.AIRecommendService;

@Controller
public class AIRecommendController {

	private final AIRecommendService aiRecommendService;

	public AIRecommendController(AIRecommendService aiRecommendService) {
		this.aiRecommendService = aiRecommendService;
	}

	@GetMapping("/ai-recommend")
	public String aiRecommend(HttpSession session, Model model) {
		//sessionからログインユーザ取得
		User loginUser = (User) session.getAttribute("loginUser");
		//未ログイン対策
		if (loginUser == null) {
			return "redirect:/login";
		}

		//ログインユーザのIDを取得
		Integer userId = loginUser.getId();

		//おすすめ商品取得
		List<Product> recommendedProducts = aiRecommendService.findRecommendProducts(userId);
		model.addAttribute("recommendedProducts", recommendedProducts);
		return "ai-recommend";
	}
}

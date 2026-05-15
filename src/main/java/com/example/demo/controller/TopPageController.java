package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.ProductSeasonService;

@Controller
public class TopPageController {

	private final ProductSeasonService productSeasonService;

	public TopPageController(ProductSeasonService productSeasonService, RegisterController registerController) {
		this.productSeasonService = productSeasonService;
	}

	@GetMapping("/home")
	public String home(Model model) {
		model.addAttribute("seasonProducts", productSeasonService.getSeasonProducts());
		return "home";
	}
}

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.ProducerProfile;
import com.example.demo.service.ProducerProfileService;
import com.example.demo.service.ProductService;

@Controller
public class ProducerDetailController {
	private final ProducerProfileService producerProfileService;
	private final ProductService productService;

	public ProducerDetailController(ProducerProfileService producerProfileService, ProductService productService) {
		this.producerProfileService = producerProfileService;
		this.productService = productService;
	}

	@GetMapping("/producer-page")
	public String showDetail(@RequestParam(name = "id", defaultValue = "1") int producerId, Model model) {
		ProducerProfile profile = producerProfileService.getProfile(producerId);
		model.addAttribute("profile", profile);
		model.addAttribute("products", productService.getProductsByProducerId(profile.getId(), 4));
		return "producer/detail";
	}
}

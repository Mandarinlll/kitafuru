package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoriteController {
	@GetMapping("/favorite-products")
	public String showFavorite() {
		return "favorite";
	}
}

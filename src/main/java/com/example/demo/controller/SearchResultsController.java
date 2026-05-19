package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;

@Controller
public class SearchResultsController {

	@Autowired
	private ProductMapper productMapper;

	@GetMapping("/search/result")
	public String searchResults(

			@RequestParam(name = "keyword", defaultValue = "") String keyword,

			@RequestParam(name = "category", required = false) List<Integer> categories,

			@RequestParam(name = "region", required = false) List<String> regions,

			Model model) {

		List<Product> productList = productMapper.searchProducts(
				keyword,
				categories,
				regions);

		model.addAttribute("productList", productList);

		model.addAttribute("keyword", keyword);

		model.addAttribute("count", productList.size());

		model.addAttribute("categories", categories);

		model.addAttribute("regions", regions);

		return "search/results";
	}
}
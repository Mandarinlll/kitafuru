package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AreaSearchController {
	@GetMapping("/area-search")
	public String areaSearch() {
		return "area-search";
	}
}

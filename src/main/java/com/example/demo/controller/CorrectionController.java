package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CorrectionController {
	@GetMapping("user-register")
	public String Correction() {
		//correction.htmlを表示する
		return "correction";
	}
}

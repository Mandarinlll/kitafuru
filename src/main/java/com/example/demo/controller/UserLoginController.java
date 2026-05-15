package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {
	@GetMapping("/user-login")
	public String login() {
		//login.htmlを表示する
		return "/login";
	}
}

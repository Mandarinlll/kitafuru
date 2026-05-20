package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.User;

@Controller
public class MypageProfileController {

	@GetMapping("/mypage-profile")
	public String showProfile(HttpSession session, Model model) {

		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("user", user);

		return "mypage-profile";
	}
}
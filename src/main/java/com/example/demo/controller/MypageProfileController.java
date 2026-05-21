package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

@Controller
public class MypageProfileController {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public MypageProfileController(
			UserMapper userMapper,
			PasswordEncoder passwordEncoder) {

		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping("/mypage-profile")
	public String showProfile(HttpSession session, Model model) {

		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			return "redirect:/login";
		}

		model.addAttribute("user", user);

		return "mypage-profile";
	}

	@PostMapping("/mypage-profile/basic")
	public String updateBasic(
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			return "redirect:/login";
		}

		user.setName(name);
		user.setEmail(email);

		if (password != null && !password.isBlank()) {
			String encodedPassword = passwordEncoder.encode(password);
			user.setPassword(encodedPassword);
		}

		userMapper.updateBasic(user);

		session.setAttribute("loginUser", user);

		redirectAttributes.addFlashAttribute(
				"successMessage",
				"登録情報変更を受け付けました。");

		return "redirect:/mypage-profile";
	}

	@PostMapping("/mypage-profile/address")
	public String updateAddress(
			@RequestParam String address,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			return "redirect:/login";
		}

		userMapper.updateAddress(user.getId(), address);

		user.setAddress(address);
		session.setAttribute("loginUser", user);

		redirectAttributes.addFlashAttribute(
				"successMessage",
				"登録情報変更を受け付けました。");

		return "redirect:/mypage-profile";
	}

	@PostMapping("/mypage-profile/payment")
	public String updatePayment(
			@RequestParam String defaultPayment,
			HttpSession session) {

		User user = (User) session.getAttribute("loginUser");

		if (user == null) {
			return "redirect:/login";
		}

		userMapper.updateDefaultPayment(
				user.getId(),
				defaultPayment);

		user.setDefaultPayment(defaultPayment);

		session.setAttribute("loginUser", user);

		return "redirect:/mypage-profile";
	}
}
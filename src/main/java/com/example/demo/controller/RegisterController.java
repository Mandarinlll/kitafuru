package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UserForm;
import com.example.demo.service.UserService;

@Controller
public class RegisterController {

	private final UserService userService;

	public RegisterController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 新規登録画面を表示
	 */
	@GetMapping("/register")
	public String showRegisterForm(Model model) {

		model.addAttribute("form", new UserForm());

		return "/register";
	}

	/**
	 * 新規登録処理
	 */
	@PostMapping("/register")
	public String register(
			@Validated @ModelAttribute("form") UserForm form,
			BindingResult bindingResult,
			Model model) {

		// バリデーションエラー
		if (bindingResult.hasErrors()) {
			return "/register";
		}

		// ユーザー登録
		userService.register(form);

		// ログイン画面へリダイレクト
		return "redirect:/login";
	}
}
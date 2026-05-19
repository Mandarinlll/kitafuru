package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.form.LoginForm;
import com.example.demo.service.UserService;

@Controller
public class UserLoginController {

	@Autowired
	private UserService userService;

	// ログイン画面表示
	@GetMapping("/login")
	public String showLoginForm(Model model) {

		model.addAttribute("form", new LoginForm());

		return "/login";
	}

	// ログイン処理
	@PostMapping("/login")
	public String login(
			@Validated @ModelAttribute("form") LoginForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes) {

		// バリデーションエラー
		if (bindingResult.hasErrors()) {
			return "login";
		}

		// ログイン認証
		User user = userService.login(
				form.getEmail(),
				form.getPassword());

		// ログイン失敗
		if (user == null) {

			model.addAttribute(
					"loginError",
					"メールアドレスまたはパスワードが違います");

			return "login";
		}

		// session保存
		session.setAttribute("loginUser", user);

		redirectAttributes.addFlashAttribute(
				"successMessage",
				"おかえりなさい。" + user.getName() + "様!");

		// ホームへ
		return "redirect:/home";
	}

	// ログアウト
	@PostMapping("/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/login";
	}
}
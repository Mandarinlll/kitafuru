package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Producer;
import com.example.demo.form.LoginProducerForm;
import com.example.demo.service.ProducerLoginService;

@Controller
public class ProducerLoginController {
	private final ProducerLoginService producerLoginService;

	public ProducerLoginController(ProducerLoginService producerLoginService) {
		this.producerLoginService = producerLoginService;
	}

	@GetMapping("/producer/login")
	public String showProducerLogin(Model model) {
		model.addAttribute("form", new LoginProducerForm());
		return "producer-login";
	}

	@PostMapping("/producer/login")
	public String producerLogin(
			@Validated @ModelAttribute("form") LoginProducerForm form,
			BindingResult bindingResult,
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "producer-login";
		}

		Producer producer = producerLoginService.login(
				form.getEmail(),
				form.getPassword());

		if (producer == null) {
			model.addAttribute("loginError", "メールアドレスまたはパスワードが違います");
			return "producer-login";
		}

		session.setAttribute("loginProducer", producer);

		redirectAttributes.addFlashAttribute(
				"successMessage",
				"おかえりなさい。" + producer.getName() + "さん!");

		return "redirect:/producer-dashboard";
	}

	@PostMapping("/producer/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/producer/login";
	}
}

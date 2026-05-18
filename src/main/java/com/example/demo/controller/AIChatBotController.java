package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.form.ChatForm;
import com.example.demo.response.ChatResponse;
import com.example.demo.service.DifyService;

@Controller
public class AIChatBotController {
	private final DifyService difyService;

	public AIChatBotController(DifyService difyService) {
		this.difyService = difyService;
	}

	@GetMapping("/ai-chatbot")
	public String aiChat() {
		return "ai-chatbot";
	}

	@ResponseBody
	@PostMapping("/api/ai/chat")
	public ChatResponse chat(
			@RequestBody ChatForm chatForm) {

		return difyService.chat(chatForm.getMessage(), chatForm.getConversationId());

	}
}

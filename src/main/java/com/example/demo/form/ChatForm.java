package com.example.demo.form;

public class ChatForm {
	private String message;
	//会話履歴保持用
	private String conversationId;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getConversationId() {
		return conversationId;
	}

	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}

}

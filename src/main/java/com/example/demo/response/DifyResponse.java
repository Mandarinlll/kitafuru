package com.example.demo.response;

public class DifyResponse {
	private String answer;
	//会話履歴保持用
	private String conversation_id;

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getConversation_id() {
		return conversation_id;
	}

	public void setConversation_id(String conversation_id) {
		this.conversation_id = conversation_id;
	}

}

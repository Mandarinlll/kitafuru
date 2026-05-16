package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.response.AiSelectResponse;
import com.example.demo.response.ChatResponse;
import com.example.demo.response.DifyResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DifyService {

	private final ProductMapper productMapper;

	public DifyService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	private Integer extractMaxPrice(String message) {
		var matcher = java.util.regex.Pattern
				.compile("(\\d{3,6})\\s*円")
				.matcher(message);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}

		return null;
	}

	public ChatResponse chat(String message, String conversationId) {

		RestTemplate restTemplate = new RestTemplate();

		Integer maxPrice = extractMaxPrice(message);

		List<Product> candidateProducts;

		if (maxPrice != null) {
			candidateProducts = productMapper.searchForAiWithMaxPrice(message, maxPrice);
		} else {
			candidateProducts = productMapper.searchForAi(message);
		}

		if (candidateProducts.isEmpty()) {
			ChatResponse chatResponse = new ChatResponse();
			chatResponse.setAnswerHtml("該当する商品が見つかりませんでした。");
			chatResponse.setConversationId(conversationId);
			return chatResponse;
		}

		String conversationJson = "";

		if (conversationId != null && !conversationId.isBlank()) {
			conversationJson = """
					,
					  "conversation_id": "%s"
					""".formatted(conversationId);
		}

		String productText = candidateProducts.stream()
				.map(product -> """
						商品ID: %d
						商品名: %s
						価格: %d円
						説明: %s
						産地: %s
						""".formatted(
						product.getId(),
						product.getName(),
						product.getPrice(),
						product.getBody(),
						product.getOriginArea()))
				.collect(Collectors.joining("\n"));

		String query = """
				質問:%s

				候補:
				%s

				JSONのみ:
				{
				 "message":"短い理由",
				 "productIds":[1,2,3]
				}
				""".formatted(message, productText);

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth("app-dmwFshroDs5MIstlF5EiRBth");
		headers.setContentType(MediaType.APPLICATION_JSON);

		String body = """
				{
				  "inputs": {},
				  "query": "%s",
				  "response_mode": "blocking",
				  "user": "test-user"%s
				}
				""".formatted(
				query.replace("\\", "\\\\")
						.replace("\"", "\\\"")
						.replace("\n", "\\n"),
				conversationJson);

		HttpEntity<String> request = new HttpEntity<>(body, headers);

		DifyResponse response = restTemplate.postForObject(
				"http://localhost/v1/chat-messages",
				request,
				DifyResponse.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();

			AiSelectResponse aiSelectResponse = objectMapper.readValue(response.getAnswer(), AiSelectResponse.class);

			List<Product> selectedProducts = productMapper.findByIds(aiSelectResponse.getProductIds());

			String productHtml = selectedProducts.stream()
					.map(product -> """
							<div class="ai-product">
								<p><strong>%s</strong></p>
								<p>価格：%d円</p>
								<p>%s</p>
								<p><a href="/products/%d">商品詳細を見る</a></p>
							</div>
							""".formatted(
							product.getName(),
							product.getPrice(),
							product.getBody(),
							product.getId()))
					.collect(Collectors.joining("\n"));

			ChatResponse chatResponse = new ChatResponse();

			chatResponse.setAnswerHtml("""
					<p>%s</p>
					%s
					""".formatted(
					aiSelectResponse.getMessage(),
					productHtml));

			chatResponse.setConversationId(response.getConversation_id());

			return chatResponse;

		} catch (Exception e) {
			e.printStackTrace();

			ChatResponse chatResponse = new ChatResponse();
			chatResponse.setAnswerHtml("AIの回答形式を読み取れませんでした。もう一度お試しください。");
			chatResponse.setConversationId(
					response != null ? response.getConversation_id() : conversationId);

			return chatResponse;
		}
	}
}
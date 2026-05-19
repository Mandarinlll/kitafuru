package com.example.demo.service;

import java.util.List;
//複数の商品情報を1つの文字列やHTMLにまとめるために使用
import java.util.stream.Collectors;

//Dify APIにHTTPリクエストを送るため
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
//Dify APIに通信するため
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.response.AiSelectResponse;
import com.example.demo.response.ChatResponse;
import com.example.demo.response.DifyResponse;
//JSON文字列をJavaオブジェクトに変換するため
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DifyService {

	private final ProductMapper productMapper;

	public DifyService(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	/**
	 * ユーザの入力文に金額があるか探すメソッド
	 * @param message
	 * @return
	 */
	private Integer extractMaxPrice(String message) {
		var matcher = java.util.regex.Pattern
				.compile("(\\d{3,6})\\s*円")
				.matcher(message);

		if (matcher.find()) {
			return Integer.parseInt(matcher.group(1));
		}

		return null;
	}

	/**
	 * チャット処理
	 * @param message = ユーザの質問
	 * @param conversationId = Difyの会話ID
	 * @return
	 */
	public ChatResponse chat(String message, String conversationId) {

		RestTemplate restTemplate = new RestTemplate();
		//ユーザの入力文から最大価格を取り出す
		Integer maxPrice = extractMaxPrice(message);
		//AIに渡す候補商品リストを入れる変数
		List<Product> candidateProducts;
		//価格が入力されていれば、価格条件付きで商品検索
		//されていなければ、通常の商品検索を行う
		if (maxPrice != null) {
			candidateProducts = productMapper.searchForAiWithMaxPrice(message, maxPrice);
		} else {
			candidateProducts = productMapper.searchForAi(message);
		}
		//商品候補が一つもなかった場合の処理
		if (candidateProducts.isEmpty()) {
			ChatResponse chatResponse = new ChatResponse();
			chatResponse.setAnswerHtml("該当する商品が見つかりませんでした。");
			chatResponse.setConversationId(conversationId);
			return chatResponse;
		}
		//Difyに渡す会話ID用のJSON文字列
		String conversationJson = "";
		//会話IDが存在する場合だけ処理
		if (conversationId != null && !conversationId.isBlank()) {
			//Difyに送るJSONへconversation_idを追加し、前回の会話内容を引き継ぐ
			conversationJson = """
					,
					  "conversation_id": "%s"
					""".formatted(conversationId);
		}
		//候補商品リストを１件ずつ処理
		String productText = candidateProducts.stream()
				//商品１件をAIが読みやすいテキスト形式に変換し、
				//商品のID、名前、価格、説明、産地を埋め込む
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
				//複数の商品テキストを改行でつなげて、１つの文字列にする
				.collect(Collectors.joining("\n"));
		//Difyに送る質問文
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
		//Dify APIに送るJSON本文の形式
		String body = """
				{
				  "inputs": {},
				  "query": "%s",
				  "response_mode": "blocking",
				  "user": "test-user"%s
				}
				""".formatted(
				//query内の\,",改行をJSON用に安全な形へ変換
				query.replace("\\", "\\\\")
						.replace("\"", "\\\"")
						.replace("\n", "\\n"),
				//会話IDがあればJSONに追加
				conversationJson);

		HttpEntity<String> request = new HttpEntity<>(body, headers);
		//返ってきたJSONをDifyResponseクラスに変換して受け取る
		DifyResponse response = restTemplate.postForObject(
				"http://localhost/v1/chat-messages",
				request,
				DifyResponse.class);
		//Difyの回答を読み取る処理
		try {
			//JSONをJavaオブジェクトに変換する道具
			ObjectMapper objectMapper = new ObjectMapper();
			//Difyの回答文字列をAiSelectResponseに変換
			AiSelectResponse aiSelectResponse = objectMapper.readValue(response.getAnswer(), AiSelectResponse.class);
			//AIが選んだ商品IDを使って、DBから商品情報を取得
			List<Product> selectedProducts = productMapper.findByIds(aiSelectResponse.getProductIds());
			//選ばれた商品を１件ずつHTMLに変換
			String productHtml = selectedProducts.stream()
					.map(product -> """
							<div class="ai-product">
								<img src="%s" alt="%s" class="ai-product-image">
								<p><strong>%s</strong></p>
								<p>価格：%d円</p>
								<p>%s</p>
								<p><a href="/products/%d">商品詳細を見る</a></p>
							</div>
							""".formatted(
							product.getImage(),
							product.getName(),
							product.getName(),
							product.getPrice(),
							product.getBody(),
							product.getId()))
					.collect(Collectors.joining("\n"));
			//最終的に画面へ返すレスポンス
			ChatResponse chatResponse = new ChatResponse();
			//AIの短い理由と商品HTMLをまとめる
			chatResponse.setAnswerHtml("""
					<p>%s</p>
					%s
					""".formatted(
					aiSelectResponse.getMessage(),
					productHtml));
			//Difyから帰ってきた会話IDをセットし、次回の会話継続に使用
			chatResponse.setConversationId(response.getConversation_id());
			return chatResponse;
			//Difyの回答がJSON形式でなかった場合のエラー時の処理
		} catch (Exception e) {
			e.printStackTrace();

			ChatResponse chatResponse = new ChatResponse();
			chatResponse.setAnswerHtml("AIの回答形式を読み取れませんでした。もう一度お試しください。");
			//responseがあればDifyの会話IDを使い、なければ元の会話IDを使う
			chatResponse.setConversationId(
					response != null ? response.getConversation_id() : conversationId);

			return chatResponse;
		}
	}
}
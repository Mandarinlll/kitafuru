package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;

@Service
public class CartService {
	// session に保存するときのキー名
	private static final String CART_KEY = "cart";

	// カート情報を取得する
	@SuppressWarnings("unchecked")
	public List<CartItem> getCart(
			HttpSession session) {
		// session から cart を取得
		List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_KEY);
		// cart が存在しない場合
		if (cart == null) {
			// 新しいカートを作成
			cart = new ArrayList<>();
			// session に保存
			session.setAttribute(
					CART_KEY,
					cart);
		}
		// カート情報を返す
		return cart;

	}

	// 商品をカートから削除する
	public void removeItem(
			HttpSession session,
			int productId) {
		// カート取得
		List<CartItem> cart = getCart(session);
		// 商品IDが一致する商品を削除
		cart.removeIf(item -> item.getProduct_id() == productId);
	}

	// 商品数量を1増やす
	public void increaseQuantity(
			HttpSession session,
			int productId) {
		// カート取得
		List<CartItem> cart = getCart(session);
		// カートの商品を順番に確認
		for (CartItem item : cart) {
			// 商品IDが一致した場合
			if (item.getProduct_id() == productId) {
				// 数量を +1
				item.setQuantity(
						item.getQuantity() + 1);

			}
		}
	}

	// 商品数量を1減らす
	public void decreaseQuantity(
			HttpSession session,
			int productId) {
		// カート取得
		List<CartItem> cart = getCart(session);
		// カートの商品を順番に確認
		for (CartItem item : cart) {
			// 商品IDが一致した場合
			if (item.getProduct_id() == productId) {
				// 数量を -1
				int qty = item.getQuantity() - 1;
				// 数量が0以下になった場合
				if (qty <= 0) {
					// 商品をカートから削除
					cart.removeIf(
							c -> c.getProduct_id() == productId);

				} else {
					// 数量を更新
					item.setQuantity(qty);

				}
				// 処理終了
				break;
			}
		}
	}

}
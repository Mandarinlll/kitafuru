package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.demo.entity.CartItem;

@Service
public class CartService {

	private static final String CART_KEY = "cart";

	@SuppressWarnings("unchecked")
	public List<CartItem> getCart(
			HttpSession session) {

		List<CartItem> cart = (List<CartItem>) session.getAttribute(CART_KEY);

		if (cart == null) {

			cart = new ArrayList<>();

			session.setAttribute(
					CART_KEY,
					cart);
		}

		return cart;

	}

	public void removeItem(
			HttpSession session,
			int productId) {

		List<CartItem> cart = getCart(session);

		cart.removeIf(item -> item.getProduct_id() == productId);
	}

	public void increaseQuantity(
			HttpSession session,
			int productId) {

		List<CartItem> cart = getCart(session);

		for (CartItem item : cart) {

			if (item.getProduct_id() == productId) {

				item.setQuantity(
						item.getQuantity() + 1);

			}
		}
	}

	public void decreaseQuantity(
			HttpSession session,
			int productId) {

		List<CartItem> cart = getCart(session);

		for (CartItem item : cart) {

			if (item.getProduct_id() == productId) {

				int qty = item.getQuantity() - 1;

				if (qty <= 0) {

					cart.remove(item);

				} else {

					item.setQuantity(qty);

				}

				break;
			}
		}
	}

}
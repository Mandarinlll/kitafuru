package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.Product;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.service.CartService;

@Controller
public class FavoriteController {

	private final ProductMapper productMapper;
	private final CartService cartService;

	public FavoriteController(
			ProductMapper productMapper,
			CartService cartService) {

		this.productMapper = productMapper;
		this.cartService = cartService;
	}

	@GetMapping("/favorite-products")
	public String showFavorite(
			HttpSession session,
			Model model,
			@RequestParam(required = false, defaultValue = "all") String category,
			@RequestParam(required = false, defaultValue = "new") String sort,
			@RequestParam(required = false, defaultValue = "false") boolean stockOnly) {

		List<CartItem> favorite = getFavorite(session);

		List<Product> favoriteProducts = new ArrayList<>();

		for (CartItem item : favorite) {

			Product product = productMapper.findById(item.getProduct_id());

			if (product == null) {
				continue;
			}

			if (stockOnly && product.getStock() <= 0) {
				continue;
			}

			if (!"all".equals(category)) {

				String productCategory = product.getCategoryName();

				if (productCategory == null || !productCategory.equals(category)) {
					continue;
				}
			}

			favoriteProducts.add(product);
		}

		if ("old".equals(sort)) {
			favoriteProducts.sort(Comparator.comparing(Product::getId));
		} else {
			favoriteProducts.sort(Comparator.comparing(Product::getId).reversed());
		}

		model.addAttribute("favoriteProducts", favoriteProducts);
		model.addAttribute("category", category);
		model.addAttribute("sort", sort);
		model.addAttribute("stockOnly", stockOnly);

		return "favorite";
	}

	@PostMapping("/favorite-products/add")
	public String addFavorite(
			@RequestParam("productId") int productId,
			HttpSession session) {

		List<CartItem> favorite = getFavorite(session);

		for (CartItem item : favorite) {
			if (item.getProduct_id() == productId) {
				return "redirect:/favorite-products";
			}
		}

		CartItem item = new CartItem();
		item.setProduct_id(productId);
		item.setQuantity(1);

		favorite.add(item);

		return "redirect:/favorite-products";
	}

	@PostMapping("/favorite-products/delete")
	public String deleteFavorite(
			@RequestParam("productId") int productId,
			HttpSession session) {

		List<CartItem> favorite = getFavorite(session);

		favorite.removeIf(item -> item.getProduct_id() == productId);

		return "redirect:/favorite-products";
	}

	@PostMapping("/favorite-products/delete-all")
	public String deleteAllFavorites(HttpSession session) {

		session.removeAttribute("favorite");

		return "redirect:/favorite-products";
	}

	@PostMapping("/favorite-products/cart-all")
	public String addAllFavoritesToCart(HttpSession session) {

		List<CartItem> favorite = getFavorite(session);
		List<CartItem> cart = cartService.getCart(session);

		for (CartItem favoriteItem : favorite) {

			boolean exists = false;

			for (CartItem cartItem : cart) {
				if (cartItem.getProduct_id() == favoriteItem.getProduct_id()) {
					exists = true;
					break;
				}
			}

			if (!exists) {
				CartItem item = new CartItem();
				item.setProduct_id(favoriteItem.getProduct_id());
				item.setQuantity(1);

				cart.add(item);
			}
		}

		return "redirect:/cart";
	}

	@SuppressWarnings("unchecked")
	private List<CartItem> getFavorite(HttpSession session) {

		List<CartItem> favorite = (List<CartItem>) session.getAttribute("favorite");

		if (favorite == null) {
			favorite = new ArrayList<>();
			session.setAttribute("favorite", favorite);
		}

		return favorite;
	}
}
package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.ZoneId;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Producer;
import com.example.demo.model.ProducerProductForm;
import com.example.demo.service.ProducerProductManagementService;

@Controller
public class ProducerProductManagementController {
	private final ProducerProductManagementService productManagementService;

	public ProducerProductManagementController(ProducerProductManagementService productManagementService) {
		this.productManagementService = productManagementService;
	}

	@GetMapping("/producer/products")
	public String showProductList(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "stockFilter", required = false) String stockFilter,
			Model model, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		int producerId = loginProducer.getId();
		model.addAttribute("products",
				productManagementService.findProducts(producerId, keyword, categoryId, stockFilter));
		model.addAttribute("categories", productManagementService.findCategories());
		model.addAttribute("keyword", keyword);
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("stockFilter", stockFilter);
		model.addAttribute("businessDate", LocalDate.now(ZoneId.of("Asia/Tokyo")));
		return "producer/products";
	}

	@GetMapping("/producer/products/new")
	public String showCreateForm(Model model, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		int producerId = loginProducer.getId();
		model.addAttribute("productForm", productManagementService.createNewForm(producerId));
		addFormAttributes(model, "商品登録");
		return "producer/product-form";
	}

	@PostMapping("/producer/products")
	public String createProduct(@ModelAttribute("productForm") ProducerProductForm productForm,
			RedirectAttributes redirectAttributes, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		int producerId = loginProducer.getId();
		productManagementService.createProduct(producerId, productForm);
		redirectAttributes.addFlashAttribute("savedMessage", "商品をデータベースに登録しました。");
		return "redirect:/producer/products";
	}

	@PostMapping("/producer/products/save")
	public String saveProduct(@ModelAttribute("productForm") ProducerProductForm productForm,
			@RequestParam("imageFile") MultipartFile imageFile,
			RedirectAttributes redirectAttributes, HttpSession session) throws IOException {
		if (!imageFile.isEmpty()) {

			String fileName = imageFile.getOriginalFilename();

			Path savePath = Paths.get(
					"src/main/resources/static/images/products/",
					fileName);

			Files.copy(
					imageFile.getInputStream(),
					savePath,
					StandardCopyOption.REPLACE_EXISTING);

			productForm.setImage("/images/products/" + fileName);
		}
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		if (loginProducer == null) {
			return "redirect:/producer/login";
		}
		int producerId = loginProducer.getId();
		if (productForm.getId() == 0) {
			productManagementService.createProduct(producerId, productForm);
			redirectAttributes.addFlashAttribute("savedMessage", "商品をデータベースに登録しました。");
		} else {
			productManagementService.updateProduct(producerId, productForm.getId(), productForm);
			redirectAttributes.addFlashAttribute("savedMessage", "商品情報をデータベースに保存しました。");
		}
		return "redirect:/producer/products";
	}

	@GetMapping("/producer/products/{id}/edit")
	public String showEditForm(@PathVariable int id, Model model, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		if (loginProducer == null) {
			return "redirect:/producer/login";
		}
		int producerId = loginProducer.getId();
		model.addAttribute("productForm", productManagementService.findProductForm(producerId, id));
		addFormAttributes(model, "商品編集");
		return "producer/product-form";
	}

	@PostMapping("/producer/products/{id}")
	public String updateProduct(@PathVariable int id,
			@ModelAttribute("productForm") ProducerProductForm productForm,
			RedirectAttributes redirectAttributes, HttpSession session) {
		Producer loginProducer = (Producer) session.getAttribute("loginProducer");
		if (loginProducer == null) {
			return "redirect:/producer/login";
		}
		int producerId = loginProducer.getId();
		productManagementService.updateProduct(producerId, id, productForm);
		redirectAttributes.addFlashAttribute("savedMessage", "商品情報をデータベースに保存しました。");
		return "redirect:/producer/products";
	}

	private void addFormAttributes(Model model, String pageTitle) {
		model.addAttribute("categories", productManagementService.findCategories());
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("businessDate", LocalDate.now(ZoneId.of("Asia/Tokyo")));
	}
}

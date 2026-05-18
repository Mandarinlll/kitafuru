package com.example.demo.service;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Category;
import com.example.demo.model.ProducerProductForm;
import com.example.demo.model.ProducerProductListItem;

@Service
public class ProducerProductManagementService {
	private static final int LOW_STOCK_THRESHOLD = 10;

	private final JdbcTemplate jdbcTemplate;

	public ProducerProductManagementService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProducerProductListItem> findProducts(int producerId, String keyword, Integer categoryId,
			String stockFilter) {
		StringBuilder sql = new StringBuilder("""
				SELECT
				    p.id,
				    p.name,
				    p.price,
				    p.stock,
				    p.category_id,
				    p.origin_area,
				    p.image,
				    p.update_at,
				    c.name AS category_name
				FROM products p
				INNER JOIN categories c ON p.category_id = c.id
				WHERE p.producer_id = ?
				""");
		List<Object> args = new ArrayList<>();
		args.add(producerId);

		if (keyword != null && !keyword.isBlank()) {
			sql.append("""
					 AND (
					     LOWER(p.name) LIKE ?
					     OR LOWER(p.origin_area) LIKE ?
					     OR LOWER(c.name) LIKE ?
					 )
					""");
			String keywordLike = "%" + keyword.trim().toLowerCase() + "%";
			args.add(keywordLike);
			args.add(keywordLike);
			args.add(keywordLike);
		}

		if (categoryId != null && categoryId > 0) {
			sql.append(" AND p.category_id = ?");
			args.add(categoryId);
		}

		if ("out".equals(stockFilter)) {
			sql.append(" AND p.stock = 0");
		} else if ("low".equals(stockFilter)) {
			sql.append(" AND p.stock > 0 AND p.stock <= ?");
			args.add(LOW_STOCK_THRESHOLD);
		} else if ("in".equals(stockFilter)) {
			sql.append(" AND p.stock > ?");
			args.add(LOW_STOCK_THRESHOLD);
		}

		sql.append(" ORDER BY p.update_at DESC, p.id DESC");

		return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
			ProducerProductListItem item = new ProducerProductListItem();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setPrice(rs.getInt("price"));
			item.setStock(rs.getInt("stock"));
			item.setCategoryId(rs.getInt("category_id"));
			item.setCategoryName(rs.getString("category_name"));
			item.setOriginArea(rs.getString("origin_area"));
			item.setImage(rs.getString("image"));
			Timestamp updateAt = rs.getTimestamp("update_at");
			item.setUpdateAt(updateAt == null ? null : updateAt.toLocalDateTime());
			return item;
		}, args.toArray());
	}

	public ProducerProductForm createNewForm(int producerId) {
		ProducerProductForm form = new ProducerProductForm();
		form.setProducerId(producerId);
		form.setCategoryId(getDefaultCategoryId());
		form.setOriginArea("十勝");
		return form;
	}

	public ProducerProductForm findProductForm(int producerId, int productId) {
		String sql = """
				SELECT id, producer_id, name, body, price, stock, category_id, origin_area, image
				FROM products
				WHERE producer_id = ?
				  AND id = ?
				""";
		return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
			ProducerProductForm form = new ProducerProductForm();
			form.setId(rs.getInt("id"));
			form.setProducerId(rs.getInt("producer_id"));
			form.setName(rs.getString("name"));
			form.setBody(rs.getString("body"));
			form.setPrice(rs.getInt("price"));
			form.setStock(rs.getInt("stock"));
			form.setCategoryId(rs.getInt("category_id"));
			form.setOriginArea(rs.getString("origin_area"));
			form.setImage(rs.getString("image"));
			return form;
		}, producerId, productId);
	}

	@Transactional
	public int createProduct(int producerId, ProducerProductForm form) {
		int categoryId = resolveCategoryId(form.getCategoryId());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		String sql = """
				INSERT INTO products (
				    producer_id, name, body, price, stock, origin_area, image,
				    recommendation_score, category_id, create_at, update_at
				)
				VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
				""";

		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
			ps.setInt(1, producerId);
			ps.setString(2, emptyToDefault(form.getName(), "名称未設定"));
			ps.setString(3, emptyToDefault(form.getBody(), ""));
			ps.setInt(4, Math.max(0, form.getPrice()));
			ps.setInt(5, Math.max(0, form.getStock()));
			ps.setString(6, emptyToDefault(form.getOriginArea(), "十勝"));
			ps.setString(7, emptyToNull(form.getImage()));
			ps.setInt(8, categoryId);
			return ps;
		}, keyHolder);

		Number key = keyHolder.getKey();
		if (key == null) {
			throw new IllegalStateException("商品IDを取得できませんでした。");
		}
		int productId = key.intValue();
		syncProductCategory(productId, categoryId);
		return productId;
	}

	@Transactional
	public void updateProduct(int producerId, int productId, ProducerProductForm form) {
		int categoryId = resolveCategoryId(form.getCategoryId());
		String sql = """
				UPDATE products
				SET name = ?,
				    body = ?,
				    price = ?,
				    stock = ?,
				    origin_area = ?,
				    image = ?,
				    category_id = ?,
				    update_at = CURRENT_TIMESTAMP
				WHERE id = ?
				  AND producer_id = ?
				""";
		int updated = jdbcTemplate.update(sql,
				emptyToDefault(form.getName(), "名称未設定"),
				emptyToDefault(form.getBody(), ""),
				Math.max(0, form.getPrice()),
				Math.max(0, form.getStock()),
				emptyToDefault(form.getOriginArea(), "十勝"),
				emptyToNull(form.getImage()),
				categoryId,
				productId,
				producerId);
		if (updated == 0) {
			throw new IllegalArgumentException("更新対象の商品が見つかりません。");
		}
		syncProductCategory(productId, categoryId);
	}

	public List<Category> findCategories() {
		String sql = "SELECT id, name, description FROM categories ORDER BY id";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Category category = new Category();
			category.setId(rs.getInt("id"));
			category.setName(rs.getString("name"));
			category.setDescription(rs.getString("description"));
			return category;
		});
	}

	private void syncProductCategory(int productId, int categoryId) {
		jdbcTemplate.update("DELETE FROM products_categories WHERE product_id = ?", productId);
		jdbcTemplate.update("INSERT INTO products_categories (product_id, category_id) VALUES (?, ?)", productId,
				categoryId);
	}

	private int getDefaultCategoryId() {
		Integer categoryId = jdbcTemplate.queryForObject("SELECT id FROM categories ORDER BY id LIMIT 1",
				Integer.class);
		return categoryId == null ? 1 : categoryId;
	}

	private int resolveCategoryId(int categoryId) {
		return categoryId > 0 ? categoryId : getDefaultCategoryId();
	}

	private String emptyToDefault(String value, String defaultValue) {
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		return value.trim();
	}

	private String emptyToNull(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return value.trim();
	}
}

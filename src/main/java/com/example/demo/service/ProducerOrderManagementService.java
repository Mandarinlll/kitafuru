package com.example.demo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.ProducerOrderListItem;
import com.example.demo.model.StatusOption;

@Service
public class ProducerOrderManagementService {
	private static final Set<String> VALID_STATUSES = Set.of("new", "preparing", "shipped", "delivered",
			"cancelled");

	private final JdbcTemplate jdbcTemplate;

	public ProducerOrderManagementService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ProducerOrderListItem> findOrders(int producerId, String keyword, String status) {
		StringBuilder sql = new StringBuilder("""
				SELECT
				    o.id,
				    o.order_at,
				    o.payment_method,
				    o.shipping_address,
				    o.status,
				    u.name AS customer_name,
				    u.email AS customer_email,
				    COALESCE(SUM(oi.quantity), 0) AS total_quantity,
				    COALESCE(SUM(oi.quantity * p.price), 0) AS total_price,
				    STRING_AGG(p.name || ' × ' || oi.quantity::TEXT, ' / ' ORDER BY p.id) AS product_summary
				FROM orders o
				INNER JOIN users u ON o.user_id = u.id
				INNER JOIN order_items oi ON o.id = oi.order_id
				INNER JOIN products p ON oi.product_id = p.id
				WHERE p.producer_id = ?
				""");
		List<Object> args = new ArrayList<>();
		args.add(producerId);

		if (status != null && !status.isBlank()) {
			sql.append(" AND o.status = ?");
			args.add(status);
		}

		if (keyword != null && !keyword.isBlank()) {
			sql.append("""
					 AND (
					     CAST(o.id AS TEXT) LIKE ?
					     OR LOWER(u.name) LIKE ?
					     OR LOWER(p.name) LIKE ?
					 )
					""");
			String keywordLike = "%" + keyword.trim().toLowerCase() + "%";
			args.add(keywordLike);
			args.add(keywordLike);
			args.add(keywordLike);
		}

		sql.append("""
				GROUP BY
				    o.id,
				    o.order_at,
				    o.payment_method,
				    o.shipping_address,
				    o.status,
				    u.name,
				    u.email
				ORDER BY o.order_at DESC, o.id DESC
				""");

		return jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
			ProducerOrderListItem item = new ProducerOrderListItem();
			item.setId(rs.getInt("id"));
			Timestamp orderAt = rs.getTimestamp("order_at");
			item.setOrderAt(orderAt == null ? null : orderAt.toLocalDateTime());
			item.setTotalPrice(rs.getInt("total_price"));
			item.setPaymentMethod(rs.getString("payment_method"));
			item.setShippingAddress(rs.getString("shipping_address"));
			item.setStatus(rs.getString("status"));
			item.setCustomerName(rs.getString("customer_name"));
			item.setCustomerEmail(rs.getString("customer_email"));
			item.setTotalQuantity(rs.getInt("total_quantity"));
			item.setProductSummary(rs.getString("product_summary"));
			return item;
		}, args.toArray());
	}

	@Transactional
	public int updateStatus(int producerId, List<Integer> orderIds, String status) {
		if (orderIds == null || orderIds.isEmpty()) {
			return 0;
		}
		if (!VALID_STATUSES.contains(status)) {
			throw new IllegalArgumentException("不正な注文ステータスです。");
		}

		String placeholders = String.join(",", Collections.nCopies(orderIds.size(), "?"));
		String sql = """
				UPDATE orders
				SET status = ?
				WHERE id IN (
				""" + placeholders + """
				)
				  AND id IN (
				      SELECT DISTINCT o.id
				      FROM orders o
				      INNER JOIN order_items oi ON o.id = oi.order_id
				      INNER JOIN products p ON oi.product_id = p.id
				      WHERE p.producer_id = ?
				  )
				""";
		List<Object> args = new ArrayList<>();
		args.add(status);
		args.addAll(orderIds);
		args.add(producerId);
		return jdbcTemplate.update(sql, args.toArray());
	}

	public List<StatusOption> getStatusOptions() {
		List<StatusOption> statuses = new ArrayList<>();
		statuses.add(new StatusOption("new", "新規受注"));
		statuses.add(new StatusOption("preparing", "準備中"));
		statuses.add(new StatusOption("shipped", "出荷済"));
		statuses.add(new StatusOption("delivered", "配送完了"));
		statuses.add(new StatusOption("cancelled", "キャンセル"));
		return statuses;
	}
}

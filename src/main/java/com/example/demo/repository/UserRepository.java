package com.example.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
public class UserRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public User findById(int id) {

		String sql = "SELECT * FROM users WHERE id=?";

		return jdbcTemplate.queryForObject(
				sql,
				(rs, rowNum) -> {

					User user = new User();

					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setAddress(rs.getString("address"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setImage(rs.getString("image"));
					user.setCreateAt(rs.getTimestamp("create_at"));

					return user;
				},
				id);
	}
}
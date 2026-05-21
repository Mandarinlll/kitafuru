package com.example.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.model.ProducerProfile;

@Service
public class ProducerProfileService {
	private final JdbcTemplate jdbcTemplate;
	private ProducerProfile cachedProfile;

	public ProducerProfileService(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.cachedProfile = createDefaultProfile();
	}

	public synchronized ProducerProfile getProfile(int producerId) {
		String sql = """
				SELECT id, name, body, area, image, email, phone, sns_link
				FROM producers
				WHERE id = ?
				""";

		try {
			ProducerProfile profile = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> mapProfile(rs), producerId);
			if (profile == null) {
				return copyProfile(cachedProfile);
			}
			mergeExtendedFields(profile);
			cachedProfile = copyProfile(profile);
			return copyProfile(profile);
		} catch (DataAccessException ex) {
			return copyProfile(cachedProfile);
		}
	}

	public synchronized void updateProfile(ProducerProfile profile) {
		cachedProfile = copyProfile(profile);

		String sql = """
				UPDATE producers
				SET name = ?,
				    body = ?,
				    area = ?,
				    email = ?,
				    phone = ?,
				    sns_link = ?
				WHERE id = ?
				""";

		try {
			jdbcTemplate.update(sql, emptyToDefault(profile.getShopName(), "北ふる海産工房"),
					emptyToDefault(profile.getBrandDescription(), ""), emptyToDefault(profile.getArea(), "北海道"),
					emptyToNull(profile.getEmail()), emptyToNull(profile.getPhone()), emptyToNull(profile.getSnsLink()),
					profile.getId());
		} catch (DataAccessException ex) {
			// Keep the edited profile available when the database is offline.
		}
	}

	private ProducerProfile mapProfile(ResultSet rs) throws SQLException {
		ProducerProfile profile = createDefaultProfile();
		profile.setId(rs.getInt("id"));
		profile.setShopName(rs.getString("name"));
		profile.setBrandDescription(rs.getString("body"));
		profile.setArea(rs.getString("area"));
		profile.setImage(rs.getString("image"));
		profile.setEmail(rs.getString("email"));
		profile.setPhone(rs.getString("phone"));
		profile.setSnsLink(rs.getString("sns_link"));
		return profile;
	}

	private void mergeExtendedFields(ProducerProfile profile) {
		profile.setCompanyName(emptyToDefault(cachedProfile.getCompanyName(), "株式会社 北ふる"));
		profile.setRepresentativeName(emptyToDefault(cachedProfile.getRepresentativeName(), "田中 牧夫"));
		profile.setPostalAddress(emptyToDefault(cachedProfile.getPostalAddress(), "〒080-0010 北海道帯広市西○条南○丁目○-○"));
	}

	private ProducerProfile createDefaultProfile() {
		ProducerProfile profile = new ProducerProfile();
		profile.setId(0);
		profile.setShopName("テスト工房");
		profile.setCompanyName("株式会社テスト");
		profile.setRepresentativeName("テスト太郎");
		profile.setArea("テスト県");
		profile.setPostalAddress("〒080-0010 北海道帯広市西○条南○丁目○-○");
		profile.setPhone("0155-XX-XXXX");
		profile.setEmail("test@kitafuru.example.com");
		profile.setBrandDescription("");
		profile.setSnsLink("https://example.com/producers/01");
		profile.setImage("/images/producers/producer-01.jpg");
		return profile;
	}

	private ProducerProfile copyProfile(ProducerProfile source) {
		ProducerProfile profile = new ProducerProfile();
		profile.setId(source.getId());
		profile.setShopName(source.getShopName());
		profile.setCompanyName(source.getCompanyName());
		profile.setRepresentativeName(source.getRepresentativeName());
		profile.setArea(source.getArea());
		profile.setPostalAddress(source.getPostalAddress());
		profile.setPhone(source.getPhone());
		profile.setEmail(source.getEmail());
		profile.setBrandDescription(source.getBrandDescription());
		profile.setSnsLink(source.getSnsLink());
		profile.setImage(source.getImage());
		return profile;
	}

	private String emptyToDefault(String value, String defaultValue) {
		if (value == null || value.isBlank()) {
			return defaultValue;
		}
		return value;
	}

	private String emptyToNull(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return value;
	}
}

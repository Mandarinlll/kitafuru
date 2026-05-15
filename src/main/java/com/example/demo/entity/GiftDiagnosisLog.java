package com.example.demo.entity;

import java.security.Timestamp;

public class GiftDiagnosisLog {
	private int id;
	private int userId;
	private String diagnosisResult;
	private Timestamp diagnosisAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDiagnosisResult() {
		return diagnosisResult;
	}

	public void setDiagnosisResult(String diagnosisResult) {
		this.diagnosisResult = diagnosisResult;
	}

	public Timestamp getDiagnosisAt() {
		return diagnosisAt;
	}

	public void setDiagnosisAt(Timestamp diagnosisAt) {
		this.diagnosisAt = diagnosisAt;
	}
}

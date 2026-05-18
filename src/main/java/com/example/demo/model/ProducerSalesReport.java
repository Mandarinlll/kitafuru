package com.example.demo.model;

import java.time.LocalDate;

public class ProducerSalesReport {
	private LocalDate reportDate;
	private int orderCount;
	private int totalQuantity;
	private int salesAmount;

	public ProducerSalesReport() {
	}

	public ProducerSalesReport(LocalDate reportDate, int orderCount, int totalQuantity, int salesAmount) {
		this.reportDate = reportDate;
		this.orderCount = orderCount;
		this.totalQuantity = totalQuantity;
		this.salesAmount = salesAmount;
	}

	public LocalDate getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDate reportDate) {
		this.reportDate = reportDate;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}

	public int getAverageOrderAmount() {
		if (orderCount == 0) {
			return 0;
		}
		return salesAmount / orderCount;
	}
}

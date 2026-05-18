package com.example.demo.model;

import java.time.LocalDate;

public class ProducerDashboardSummary {
	private LocalDate businessDate;
	private int todayOrderCount;
	private int yesterdayOrderCount;
	private int todaySales;
	private int yesterdaySales;
	private int monthlySales;

	public ProducerDashboardSummary() {
	}

	public ProducerDashboardSummary(LocalDate businessDate, int todayOrderCount, int yesterdayOrderCount,
			int todaySales, int yesterdaySales, int monthlySales) {
		this.businessDate = businessDate;
		this.todayOrderCount = todayOrderCount;
		this.yesterdayOrderCount = yesterdayOrderCount;
		this.todaySales = todaySales;
		this.yesterdaySales = yesterdaySales;
		this.monthlySales = monthlySales;
	}

	public LocalDate getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(LocalDate businessDate) {
		this.businessDate = businessDate;
	}

	public int getTodayOrderCount() {
		return todayOrderCount;
	}

	public void setTodayOrderCount(int todayOrderCount) {
		this.todayOrderCount = todayOrderCount;
	}

	public int getYesterdayOrderCount() {
		return yesterdayOrderCount;
	}

	public void setYesterdayOrderCount(int yesterdayOrderCount) {
		this.yesterdayOrderCount = yesterdayOrderCount;
	}

	public int getTodaySales() {
		return todaySales;
	}

	public void setTodaySales(int todaySales) {
		this.todaySales = todaySales;
	}

	public int getYesterdaySales() {
		return yesterdaySales;
	}

	public void setYesterdaySales(int yesterdaySales) {
		this.yesterdaySales = yesterdaySales;
	}

	public int getMonthlySales() {
		return monthlySales;
	}

	public void setMonthlySales(int monthlySales) {
		this.monthlySales = monthlySales;
	}

	public int getOrderCountDiff() {
		return todayOrderCount - yesterdayOrderCount;
	}

	public int getSalesGrowthRate() {
		if (yesterdaySales == 0) {
			return todaySales > 0 ? 100 : 0;
		}
		return (int) Math.round(((double) (todaySales - yesterdaySales) / yesterdaySales) * 100);
	}
}

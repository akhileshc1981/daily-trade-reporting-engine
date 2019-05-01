package com.jpmc.trade;

public class TradeData {

	public String getSettledDate() {
		return settledDate;
	}

	public void setSettledDate(String settledDate) {
		this.settledDate = settledDate;
	}

	public double getBuyAmountUSD() {
		return buyAmountUSD;
	}

	public void setBuyAmountUSD(double buyAmountUSD) {
		this.buyAmountUSD = buyAmountUSD;
	}

	public double getSellAmountUSD() {
		return sellAmountUSD;
	}

	public void setSellAmountUSD(double sellAmountUSD) {
		this.sellAmountUSD = sellAmountUSD;
	}

	private String settledDate;
	private double buyAmountUSD;
	private double sellAmountUSD;

}

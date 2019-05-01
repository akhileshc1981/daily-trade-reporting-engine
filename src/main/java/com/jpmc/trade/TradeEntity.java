package com.jpmc.trade;

public class TradeEntity implements Comparable<TradeEntity> {

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getBuyOrSell() {
		return buyOrSell;
	}

	public void setBuyOrSell(String buyOrSell) {
		this.buyOrSell = buyOrSell;
	}

	public double getAgreedFx() {
		return agreedFx;
	}

	public void setAgreedFx(double agreedFx) {
		this.agreedFx = agreedFx;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getInstructionDate() {
		return instructionDate;
	}

	public void setInstructionDate(String instructionDate) {
		this.instructionDate = instructionDate;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public double getPricePerUnit() {
		return pricePerUnit;
	}

	public void setPricePerUnit(double pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}

	public double getAmountUSD() {
		return amountUSD;
	}

	public void setAmountUSD(double amountUSD) {
		this.amountUSD = amountUSD;
	}

	@Override
	public int compareTo(TradeEntity entity) {
		return new Double(entity.getAmountUSD()).compareTo(this.getAmountUSD());
	}

	private String entityName;
	private String buyOrSell;
	private double agreedFx;
	private String currency;
	private String instructionDate;
	private String settlementDate;
	private int units;
	private double pricePerUnit;
	private double amountUSD;

}

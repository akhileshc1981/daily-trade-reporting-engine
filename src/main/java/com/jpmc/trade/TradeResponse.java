package com.jpmc.trade;

import java.util.ArrayList;
import java.util.List;

public class TradeResponse {

	private ArrayList<TradeData> tradeData;

	private String entityRaning;

	public String getEntityRaning() {
		return entityRaning;
	}

	public void setEntityRaning(String entityRaning) {
		this.entityRaning = entityRaning;
	}

	public ArrayList<TradeData> getTradeData() {
		return tradeData;
	}

	public void setTradeData(ArrayList<TradeData> tradeData) {
		this.tradeData = tradeData;
	}

}

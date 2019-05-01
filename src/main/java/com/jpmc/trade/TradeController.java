package com.jpmc.trade;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradeController {

	@RequestMapping(value = "/generateTradeReport", method = RequestMethod.POST)
	public ResponseEntity<Object> generateTradeReport(@RequestBody Trade tradeList) throws Exception {

		caluclateSettlementDateAndUSDAmount(tradeList.getTradeEntity());
		TradeResponse response = generateReport(tradeList.getTradeEntity());
		response.setEntityRaning(generateEntityRanking(tradeList.getTradeEntity()));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public TradeEntity[] caluclateSettlementDateAndUSDAmount(TradeEntity[] tradeEntity) throws Exception {
		String[] nonWorkDaysAEDSAR = { "Fri", "Sat" };
		String[] nonWorkDaysOtherCurrency = { "Sat", "Sun" };

		for (TradeEntity entity : tradeEntity) {
			Date settlementDate = new SimpleDateFormat("dd/MM/yyyy").parse(entity.getSettlementDate());
			SimpleDateFormat newSettlementDate = new SimpleDateFormat("dd/MM/yyyy");

			SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
			String day = simpleDateformat.format(settlementDate);
			Calendar c = Calendar.getInstance();
			c.setTime(settlementDate);
			if (entity.getCurrency().equalsIgnoreCase("AED") || entity.getCurrency().equalsIgnoreCase("SAR")) {
				if (Arrays.asList(nonWorkDaysAEDSAR).contains(day)) {
					if (day.equalsIgnoreCase("Fri"))
						c.add(Calendar.DATE, 2);
					else
						c.add(Calendar.DATE, 1);
					entity.setSettlementDate(newSettlementDate.format(c.getTime()));
				}
			} else {
				if (Arrays.asList(nonWorkDaysOtherCurrency).contains(day)) {
					if (day.equalsIgnoreCase("Sat"))
						c.add(Calendar.DATE, 2);
					else
						c.add(Calendar.DATE, 1);
					entity.setSettlementDate(newSettlementDate.format(c.getTime()));

				}
			}
			entity.setAmountUSD(entity.getPricePerUnit() * entity.getUnits() * entity.getAgreedFx());
		}

		return tradeEntity;
	}

	public TradeResponse generateReport(TradeEntity[] tradeEntity) {
		HashMap<String, ArrayList<TradeEntity>> dateWiseEntityList = new HashMap<>();
		TradeResponse tradeResponse = new TradeResponse();
		for (TradeEntity entity : tradeEntity) {
			if (dateWiseEntityList.containsKey(entity.getSettlementDate())) {
				ArrayList list = dateWiseEntityList.get(entity.getSettlementDate());
				list.add(entity);
				dateWiseEntityList.put(entity.getSettlementDate(), list);

			} else {
				ArrayList list = new ArrayList();
				list.add(entity);
				dateWiseEntityList.put(entity.getSettlementDate(), list);
			}
		}

		ArrayList tradeResponseList = new ArrayList<>();
		for (Iterator tradeData = dateWiseEntityList.entrySet().iterator(); tradeData.hasNext();) {

			Map.Entry trade = (Map.Entry) tradeData.next();
			String strKey = (String) trade.getKey();
			ArrayList<TradeEntity> tradeList = (ArrayList) trade.getValue();

			double sellUSDAmount = 0;
			double buyUSDAmount = 0;
			TradeData data = new TradeData();
			for (int i = 0; i < tradeList.size(); i++) {
				TradeEntity entity = tradeList.get(i);
				if (entity.getBuyOrSell().equalsIgnoreCase("B")) {
					buyUSDAmount = buyUSDAmount + entity.getAmountUSD();
				} else {
					sellUSDAmount = sellUSDAmount + entity.getAmountUSD();
				}

			}
			data.setSettledDate(strKey);
			data.setSellAmountUSD(sellUSDAmount);
			data.setBuyAmountUSD(buyUSDAmount);
			tradeResponseList.add(data);

		}
		tradeResponse.setTradeData(tradeResponseList);
		return tradeResponse;
	}

	public String generateEntityRanking(TradeEntity[] tradeEntity) {

		String response = "";
		ArrayList arr = new ArrayList(Arrays.asList(tradeEntity));

		Collections.sort(arr);
		TradeEntity entity = (TradeEntity) arr.get(0);
		response = "entity " + entity.getEntityName() + " instructs the highest amount " + entity.getAmountUSD()
				+ " for a for " + (entity.getBuyOrSell().equalsIgnoreCase("B") ? "Incoming" : "Outgoing");
		return response;
	}

}

package com.eaugusto.investorpdf.model.clear;

import java.math.BigDecimal;

import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.api.Ticker;

import static com.eaugusto.investorpdf.utils.MoneyUtils.*;

public class ClearOrder implements Order {

	Boolean isBuyElseAsk;
	String ticker;
	BigDecimal amount;
	BigDecimal price;
	BigDecimal operationPrice;
	
	Ticker tickerUtils = new ClearTicker();
	
	public ClearOrder(String line) {
		String isBuy = line.substring(10, 11);
		isBuyElseAsk = isBuy.equals("C") ? Boolean.TRUE : isBuy.equals("V") ? Boolean.FALSE : null;
		
		String reverseString = new StringBuilder(line).reverse().toString();
		
		int currentIndex = -1;
		for(int i = 0; i < 4; i++) {
			currentIndex = reverseString.indexOf(" ", ++currentIndex);
		}
		
		int tickerIndexStart = line.indexOf("FRACIONARIO") >= 0 ? 24 : line.indexOf("VISTA") >= 0 ? 18 : -1;
		if(tickerIndexStart >= 0) {
			String tickerLine = line.substring(tickerIndexStart, line.length() - currentIndex);
			ticker = tickerUtils.getTicker(tickerLine);
		}
		
		String[] columns = line.substring(line.length() - currentIndex).split(" ");
		amount = newBigDecimal(columns[0]);
		price = newBigDecimal(columns[1]);
		operationPrice = newBigDecimal(columns[2]);
	}
	
	@Override
	public Boolean isBuyElseAsk() {
		return isBuyElseAsk;
	}

	@Override
	public String getTicker() {
		return ticker;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public BigDecimal getOperationPrice() {
		return operationPrice;
	}
}
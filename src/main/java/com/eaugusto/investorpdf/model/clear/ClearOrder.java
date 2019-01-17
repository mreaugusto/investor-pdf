package com.eaugusto.investorpdf.model.clear;

import java.math.BigDecimal;

import com.eaugusto.investorpdf.api.BrokerReport;
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
	private BrokerReport brokerReport;
	
	public ClearOrder(String line, BrokerReport brokerReport) {
		this.brokerReport = brokerReport;
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
			if(ticker == null) 
				System.err.println("Ticker não encontrado: " + tickerLine);
		}
		
		String[] columns = line.substring(line.length() - currentIndex).split(" ");
		amount = newBigDecimal(columns[0]);
		operationPrice = newBigDecimal(columns[2]);
		if(!isBuyElseAsk) {
			amount = amount.negate();
			operationPrice = operationPrice.negate();
		}
		price = newBigDecimal(columns[1]);
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

	@Override
	public Order same() {
		return this;
	}

	@Override
	public String toString() {
		return "ClearOrder [ticker=" + ticker + ", amount=" + amount + ", price="
				+ price + ", operationPrice=" + operationPrice + "]";
	}

	@Override
	public BrokerReport getBrokerReport() {
		return brokerReport;
	}
}
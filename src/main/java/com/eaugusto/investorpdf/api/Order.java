package com.eaugusto.investorpdf.api;

import java.math.BigDecimal;

public interface Order {

	Boolean isBuyElseAsk();
	String getTicker();
	BigDecimal getAmount();
	BigDecimal getPrice();
	BigDecimal getOperationPrice();
	
}
package com.eaugusto.investorpdf.api;

import java.math.BigDecimal;

public interface Order {

	String getTicker();
	BigDecimal getAmount();
	BigDecimal getPrice();
	BigDecimal getOperationPrice();
	Order same();
	BrokerReport getBrokerReport();
	InvestmentType getInvestmentType();
}
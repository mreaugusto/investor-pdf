package com.eaugusto.investorpdf.api;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.eaugusto.investorpdf.model.clear.SectionMapper;

public interface BrokerReport {
	
	LocalDate getExecutionDate();
	String getBrokerName();
	int getReportNumber();
	String getTaxId();
	List<Order> getExecutedOrders();
	Map<String, SectionMapper> getSections();
	BigDecimal getNetPrice();
	BigDecimal getTradingFee();
	BigDecimal getSettlementFee();
	BigDecimal getReportTotal();
}
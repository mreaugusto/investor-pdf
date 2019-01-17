package com.eaugusto.investorpdf.model.clear;

import static com.eaugusto.investorpdf.utils.MoneyUtils.newBigDecimal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.model.clear.SectionMapper.FindMode;

public class ClearReport implements BrokerReport {

	private Map<String, SectionMapper> sections = new HashMap<>();
	
	public ClearReport() {
		sections.put("TaxId", new SectionMapper("C.P.F./C.N.P.J/C.V.M./C.O.B.", false, 1));
		sections.put("ExecutionDate", new SectionMapper("Data pregão", true, 1));
		sections.put("ReportNumber", new SectionMapper("Nr. nota", true, 1));
		sections.put("Orders", new SectionMapper(FindMode.START_WITH, "1-BOVESPA"));
		sections.put("NetPrice", new SectionMapper(FindMode.CONTAINS, "Valor líquido das operações "));
		sections.put("SettlementFee", new SectionMapper(FindMode.CONTAINS, "Taxa de liquidação D"));
		sections.put("TradingFee", new SectionMapper(FindMode.CONTAINS, "Emolumentos D"));
		sections.put("ReportTotal", new SectionMapper(FindMode.CONTAINS, "Líquido para "));
	}
	
	@Override
	public LocalDate getExecutionDate() {
		String dtPregao = getOneLineSectionContent("ExecutionDate");
		return LocalDate.parse(dtPregao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	@Override
	public String getBrokerName() {
		return "Clear";
	}

	@Override
	public int getReportNumber() {
		String reportNumber = getOneLineSectionContent("ReportNumber");
		return Integer.parseInt(reportNumber);
	}

	@Override
	public String getTaxId() {
		String taxId = getOneLineSectionContent("TaxId");
		return taxId;
	}

	@Override
	public List<Order> getExecutedOrders() {
		List<String> orderLines = sections.get("Orders").getSectionContent();
		return orderLines.stream().map(l -> {return new ClearOrder(l, this);}).collect(Collectors.toList());
	}

	@Override
	public Map<String, SectionMapper> getSections() {
		return sections;
	}

	@Override
	public BigDecimal getNetPrice() {
		String netPriceLine = getOneLineSectionContent("NetPrice");
		int labelIndexStart = netPriceLine.indexOf("Valor líquido das operações ");
		return newBigDecimal(netPriceLine.substring(0, labelIndexStart));
	}

	@Override
	public BigDecimal getTradingFee() {
		String tradingFeeLine = getOneLineSectionContent("TradingFee");
		int labelIndexStart = tradingFeeLine.indexOf("Emolumentos D");
		return newBigDecimal(tradingFeeLine.substring(0, labelIndexStart));
	}

	@Override
	public BigDecimal getSettlementFee() {
		String settlementFeeLine = getOneLineSectionContent("SettlementFee");
		int labelIndexStart = settlementFeeLine.indexOf("Taxa de liquidação D");
		return newBigDecimal(settlementFeeLine.substring(0, labelIndexStart));
	}
	
	private String getOneLineSectionContent(String section) {
		return sections.get(section).getSectionContent().get(0);
	}

	@Override
	public BigDecimal getReportTotal() {
		String reportTotalLine = getOneLineSectionContent("ReportTotal");
		int labelIndexStart = reportTotalLine.indexOf("Líquido para ");
		return newBigDecimal(reportTotalLine.substring(0, labelIndexStart));
	}

	@Override
	public String toString() {
		return "ClearReport [getExecutionDate()=" + getExecutionDate() + ", getBrokerName()=" + getBrokerName()
				+ ", getReportNumber()=" + getReportNumber() + ", getTaxId()=" + getTaxId() + ", getExecutedOrders()="
				+ getExecutedOrders() + ", getSections()=" + getSections() + ", getNetPrice()=" + getNetPrice()
				+ ", getTradingFee()=" + getTradingFee() + ", getSettlementFee()=" + getSettlementFee()
				+ ", getReportTotal()=" + getReportTotal() + "]";
	}
	
	
}
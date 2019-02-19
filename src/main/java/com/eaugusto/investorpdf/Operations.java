package com.eaugusto.investorpdf;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.stream.Collectors;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.InvestmentType;
import com.eaugusto.investorpdf.facade.ClearFacade;
import com.eaugusto.investorpdf.utils.PDFUtils;

public class Operations {

	public static void main(String[] args) {

		File folder = null;
		try {
			folder = new File(PDFUtils.class.getResource("/clear").toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		File[] files = folder.listFiles(f -> f.getName().endsWith(".pdf"));

		Arrays.asList(files)
		.stream()
		.map(ClearFacade::getReport)
		.map(BrokerReport::getExecutedOrders)
		.flatMap(Collection::stream)
		.filter(o -> o.getTicker() != null)
		.sorted((a, b) -> a.getTicker().compareTo(b.getTicker()) * 100000 + a.getBrokerReport().getExecutionDate().compareTo(b.getBrokerReport().getExecutionDate()))//garante ordenacao
		.peek(o -> {
			InvestmentType investmentType = o.getInvestmentType();
			String ticker = o.getTicker();
			BigDecimal price = o.getPrice();
			BigDecimal amount = o.getAmount();
			BigDecimal operationPrice = o.getOperationPrice();
			BigDecimal liquidity = o.getBrokerReport().getTradingFee();
			BigDecimal settlement = o.getBrokerReport().getSettlementFee();
			LocalDate executionDate = o.getBrokerReport().getExecutionDate();
			System.out.printf(new Locale("pt", "BR"), "%s; %s; %,.2f; %,.2f; %,.2f; %,.2f; %,.2f; %s\n", investmentType, ticker, price, amount, operationPrice, liquidity, settlement, executionDate);
		})
		.collect(Collectors.toList());
	}
}
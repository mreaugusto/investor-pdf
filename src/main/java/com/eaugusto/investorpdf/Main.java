package com.eaugusto.investorpdf;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.InvestmentType;
import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.facade.ClearFacade;
import com.eaugusto.investorpdf.utils.PDFUtils;

public class Main {

	private static void operation() {
		File folder = null;
		try {
			folder = new File(PDFUtils.class.getResource("/clear").toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		File[] files = folder.listFiles();
		
		List<Order> allOrders = Arrays.asList(files)
				.stream()
				.map(ClearFacade::getReport)
				.map(BrokerReport::getExecutedOrders)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		for(Order o : allOrders) {
			InvestmentType investmentType = o.getInvestmentType();
			String ticker = o.getTicker();
			BigDecimal price = o.getPrice();
			BigDecimal amount = o.getAmount();
			BigDecimal operationPrice = o.getOperationPrice();
			BigDecimal liquidity = null;
			BigDecimal settlement = null;
			LocalDate executionDate = o.getBrokerReport().getExecutionDate();
			System.out.printf(new Locale("pt", "BR"), "%s; %s; %,.2f; %,.2f; %,.2f; %,.2f; %,.2f; %s\n", investmentType, ticker, price, amount, operationPrice, liquidity, settlement, executionDate);
		}
		//inconsistencias: ITUB
		
	}
	
	public static void main(String[] args) {

		try {
			System.out.println("INICIADO: " + LocalDateTime.now());
			operation();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			System.out.println("FINALIZADO: " + LocalDateTime.now());
		}
	}
}
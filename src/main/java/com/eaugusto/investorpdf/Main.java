package com.eaugusto.investorpdf;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.facade.ClearFacade;
import com.eaugusto.investorpdf.utils.PDFUtils;

public class Main {

	public static void main(String[] args) {

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
//			.sorted(Comparator.comparing(Order::getTicker))
//			.sorted(Comparator.comparing(Order::getBrokerReport().getExecutionDate()))
			.collect(Collectors.toList());

		for(Order o : allOrders) {
			String ticker = o.getTicker();
			BigDecimal price = o.getPrice();
			BigDecimal amount = o.getAmount();
			BigDecimal operationPrice = o.getOperationPrice();
			BigDecimal liquidity = null;
			BigDecimal settlement = null;
			LocalDate executionDate = o.getBrokerReport().getExecutionDate();
			System.out.printf(new Locale("pt", "BR"), "%s; %,.2f; %,.2f; %,.2f; %,.2f; %,.2f; %s\n", ticker, price, amount, operationPrice, liquidity, settlement, executionDate);
		}

	}
//inconsistencias: ITUB
}
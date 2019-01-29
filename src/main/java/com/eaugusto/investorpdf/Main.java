package com.eaugusto.investorpdf;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.InvestmentType;
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

		File[] files = folder.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".pdf");
			}
		});
		
		Map<String, Integer> assetsAmount = new HashMap<>();	
		Map<String, LocalDate> assetsStartOrder = new HashMap<>();

		List<Order> allOrders = Arrays.asList(files)
				.stream()
				.map(ClearFacade::getReport)
				.map(BrokerReport::getExecutedOrders)
				.flatMap(Collection::stream)
				.map(o -> {
					Integer assetAmount = assetsAmount.get(o.getTicker());
					assetAmount = assetAmount == null ? 0 : assetAmount;
					System.out.printf("%s, %d\n", o.getTicker(), assetAmount);
					int assetTotal = assetAmount + o.getAmount().intValue();
					assetsAmount.put(o.getTicker(), assetTotal);
					if(assetTotal == 0) {
						assetsStartOrder.put(o.getTicker(), o.getBrokerReport().getExecutionDate());
					}
					return o;
				})
				.filter(o-> {
					LocalDate startDate = assetsStartOrder.get(o.getTicker());
					return startDate == null || o.getBrokerReport().getExecutionDate().compareTo(startDate) >= 0;
				})
				.collect(Collectors.toList());

		for(Order o : allOrders) {
			InvestmentType investmentType = o.getInvestmentType();
			String ticker = o.getTicker();
			BigDecimal price = o.getPrice();
			BigDecimal amount = o.getAmount();
			BigDecimal operationPrice = o.getOperationPrice();
			BigDecimal liquidity = o.getBrokerReport().getTradingFee();
			BigDecimal settlement = o.getBrokerReport().getSettlementFee();
			LocalDate executionDate = o.getBrokerReport().getExecutionDate();
			System.out.printf(new Locale("pt", "BR"), "%s; %s; %,.2f; %,.2f; %,.2f; %,.2f; %,.2f; %s\n", investmentType, ticker, price, amount, operationPrice, liquidity, settlement, executionDate);
		}
		//inconsistencias: ITUB
	}
}
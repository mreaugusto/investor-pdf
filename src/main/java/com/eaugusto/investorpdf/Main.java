package com.eaugusto.investorpdf;

import java.io.File;
import java.io.FileFilter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
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

		File[] files = folder.listFiles(p -> p.getName().endsWith(".pdf"));

		Map<String, Integer> assetsAmount = new HashMap<>();	
		Map<String, List<Order>> assetsOrders = new HashMap<>();

		Arrays.asList(files)
		.stream()
		.map(ClearFacade::getReport)
		.map(BrokerReport::getExecutedOrders)
		.flatMap(Collection::stream)
		.filter(o -> o.getTicker() != null)
		.sorted((a, b) -> a.getBrokerReport().getExecutionDate().compareTo(b.getBrokerReport().getExecutionDate()))//garante ordenacao
		.map(o -> {
			//Set asset amount by ticker
			Integer assetAmount = assetsAmount.get(o.getTicker());
			assetAmount = assetAmount == null ? 0 : assetAmount;
			int assetTotal = assetAmount + o.getAmount().intValue();
			assetsAmount.put(o.getTicker(), assetTotal);

			//Group orders by ticker
			List<Order> assetOrders = assetsOrders.get(o.getTicker());
			if(assetOrders == null) {
				assetOrders = new ArrayList<>();
			}
			assetOrders.add(o);

			//Clear asset ticker if amount equals to 0
			if(assetTotal == 0) {
				assetOrders.clear();
			}
			assetsOrders.put(o.getTicker(), assetOrders);
			return o;
		})
		.collect(Collectors.toList());

		assetsOrders.entrySet().stream()
		.map(e -> e.getValue())
		.flatMap(Collection::stream)
		.filter(o -> !o.getTicker().equals("ABCP11") && !o.getTicker().equals("ITUB4"))
		.sorted((a,b) -> a.getBrokerReport().getExecutionDate().compareTo(b.getBrokerReport().getExecutionDate()))
		.forEach(o -> {
			InvestmentType investmentType = o.getInvestmentType();
			String ticker = o.getTicker();
			BigDecimal price = o.getPrice();
			BigDecimal amount = o.getAmount();
			BigDecimal operationPrice = o.getOperationPrice();
			BigDecimal liquidity = o.getBrokerReport().getTradingFee();
			BigDecimal settlement = o.getBrokerReport().getSettlementFee();
			LocalDate executionDate = o.getBrokerReport().getExecutionDate();
			System.out.printf(new Locale("pt", "BR"), "%s; %s; %,.2f; %,.2f; %,.2f; %,.2f; %,.2f; %s\n", investmentType, ticker, price, amount, operationPrice, liquidity, settlement, executionDate);
			//inconsistencias: ITUB
		});
	}
}
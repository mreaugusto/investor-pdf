package com.eaugusto.investorpdf;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
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

		Map<String, BigDecimal> wallet = Arrays.asList(files)
			.stream()
			.map(ClearFacade::getReport)
			.map(BrokerReport::getExecutedOrders)
			.flatMap(Collection::stream)
			.sorted(Comparator.comparing(Order::getTicker))
			.collect(Collectors.toMap(Order::getTicker, Order::getAmount, BigDecimal::add));
		
		for(String ticker : wallet.keySet()) {
			System.out.println(ticker + " -> " + wallet.get(ticker));
		}
	}
//inconsistencias: ITUB, KNCR11, KNRI11
}
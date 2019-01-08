package com.eaugusto.investorpdf;

import java.io.File;
import java.net.URISyntaxException;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.model.Broker;
import com.eaugusto.investorpdf.utils.BrokerParser;
import com.eaugusto.investorpdf.utils.PDFUtils;

public class Main {

	public static void main(String[] args) {

		File folder = null;
		try {
			folder = new File(PDFUtils.class.getResource("/clear").toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		String ticker = "BRSR6";

		File[] files = folder.listFiles();

		for(File file : files) {
			BrokerReport brokerReport = BrokerParser.getReport(PDFUtils.getTextFromPDF(file), Broker.CLEAR);

			System.out.println("Data do Relatório " + brokerReport.getExecutionDate());
			System.out.println("Relatório de número " + brokerReport.getReportNumber());
			for(Order order : brokerReport.getExecutedOrders()) {
				if(ticker == "ALL" || order.getTicker().equals(ticker)) {
					System.out.print(order.isBuyElseAsk() ? "COMPRA " : "VENDA ");
					System.out.printf("%s => %f * %f = %f\n", order.getTicker(), order.getAmount(), order.getPrice(), order.getOperationPrice());
				}
			}

			System.out.println("Taxa de Liquidação " + brokerReport.getSettlementFee());
			System.out.println("Emolumentos " + brokerReport.getTradingFee());
			System.out.println("Total do Relatório " + brokerReport.getReportTotal() + "\n\n\n");

		}
	}

}
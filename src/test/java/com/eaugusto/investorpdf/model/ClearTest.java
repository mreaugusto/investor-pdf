package com.eaugusto.investorpdf.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.utils.BrokerParser;
import com.eaugusto.investorpdf.utils.PDFUtils;

public class ClearTest {

	private static String pdfText;
	private static BrokerReport clearReport;

	@BeforeClass
	public static void initClass() throws URISyntaxException {
		File file = new File(PDFUtils.class.getResource("/clear/NotaCorretagem_274280_20181029.pdf").toURI());
		pdfText = PDFUtils.getTextFromPDF(file);
		clearReport = BrokerParser.getReport(pdfText, Broker.CLEAR);
	}

	@Test
	public void testaCarregaDataDoPregao() {
		LocalDate executionDate = clearReport.getExecutionDate();
		assertEquals(LocalDate.of(2018, 10, 29), executionDate);
	}

	@Test
	public void testaCarregaCPF() {
		String taxId = clearReport.getTaxId();
		assertEquals("404.927.468-00", taxId);
	}

	@Test
	public void testaCarregaNumeroDeNota() {
		int reportNumber = clearReport.getReportNumber();
		assertEquals(1366176, reportNumber);
	}

	@Test
	public void testaSeCarregaOrdens() {
		assertEquals(28, clearReport.getExecutedOrders().size());
	}

	@Test
	public void testaSeAPrimeiraOperacaoEstaOK() {
		Order firstOpperation = clearReport.getExecutedOrders().get(0);
		assertEquals(120, firstOpperation.getOperationPrice().intValue());
		assertEquals(6, firstOpperation.getAmount().intValue());
		assertEquals(20, firstOpperation.getPrice().intValue());
	}

	@Test
	public void testaSeTodosOsTotaisEstaoOK() {
		for(Order o : clearReport.getExecutedOrders()) {
			BigDecimal amount = o.getAmount();
			BigDecimal price = o.getPrice();
			assertTrue(amount.multiply(price).equals(o.getOperationPrice()));
		}
	}
	
	@Test
	public void testaSeOTotalLiquidoEstaOK() {
		BigDecimal total = BigDecimal.ZERO;
		for(Order o : clearReport.getExecutedOrders()) {
			total = total.add(o.getOperationPrice());
		}
		
		assertEquals(clearReport.getNetPrice(), total);
	}
	
	@Test
	public void testaSeCarregaTaxas() {
		BigDecimal tradingFee = clearReport.getTradingFee();
		assertEquals(0.31, tradingFee.doubleValue(), 0);
		BigDecimal settlementFee = clearReport.getSettlementFee();
		assertEquals(1.57, settlementFee.doubleValue(), 0);
	}
	
	@Test
	public void testaSeTotalFinalEstaOK() {
		BigDecimal netPrice = clearReport.getNetPrice();
		BigDecimal tradingFee = clearReport.getTradingFee();
		BigDecimal settlementFee = clearReport.getSettlementFee();
		assertEquals(netPrice.add(tradingFee).add(settlementFee), clearReport.getReportTotal());
	}
}
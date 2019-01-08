package com.eaugusto.investorpdf.facade;

import java.io.File;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.model.Broker;
import com.eaugusto.investorpdf.utils.BrokerParser;
import com.eaugusto.investorpdf.utils.PDFUtils;

public class ClearFacade {

	public static BrokerReport getReport(File file) {
		return BrokerParser.getReport(PDFUtils.getTextFromPDF(file), Broker.CLEAR);
	}
	
}
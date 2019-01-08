package com.eaugusto.investorpdf.model;

import java.util.function.Supplier;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.model.clear.ClearReport;

public enum Broker {

	CLEAR(ClearReport::new);
	
	private Supplier<BrokerReport> brokerSupplier;
	
	private Broker(Supplier<BrokerReport> brokerSupplier) {
		this.brokerSupplier = brokerSupplier;
	}

	public Supplier<BrokerReport> getBrokerSupplier() {
		return brokerSupplier;
	}
	
}
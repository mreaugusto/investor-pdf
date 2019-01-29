package com.eaugusto.investorpdf.model.clear;

import java.math.BigDecimal;

import com.eaugusto.investorpdf.api.BrokerReport;
import com.eaugusto.investorpdf.api.InvestmentType;
import com.eaugusto.investorpdf.api.Order;
import com.eaugusto.investorpdf.api.Ticker;

import static com.eaugusto.investorpdf.utils.MoneyUtils.*;

public class ClearOrder implements Order {

	Boolean isBuyElseAsk;
	String ticker;
	BigDecimal amount;
	BigDecimal price;
	BigDecimal operationPrice;
	boolean isFII;
	InvestmentType investmentType;
	
	Ticker tickerUtils = new ClearTicker();
	private BrokerReport brokerReport;
	
	public ClearOrder(String line, BrokerReport brokerReport) {
		this.brokerReport = brokerReport;
		String isBuy = line.substring(10, 11);
		isBuyElseAsk = isBuy.equals("C") ? Boolean.TRUE : isBuy.equals("V") ? Boolean.FALSE : null;
		
		String reverseString = new StringBuilder(line).reverse().toString();
		
		int currentIndex = -1;
		for(int i = 0; i < 4; i++) {
			currentIndex = reverseString.indexOf(" ", ++currentIndex);
		}
		
		int tickerIndexStart = line.indexOf("FRACIONARIO") >= 0 ? 24 : line.indexOf("VISTA") >= 0 ? 18 : -1;
		if(tickerIndexStart >= 0) {
			String tickerLine = line.substring(tickerIndexStart, line.length() - currentIndex);
			ticker = tickerUtils.getTicker(tickerLine);
			if(ticker == null) {
				System.err.println("Ticker não encontrado: " + tickerLine);
			}
			
			isFII = tickerLine.startsWith("FII ");
			if(isFII) {
				investmentType = InvestmentType.INVESTMENT_FUND;
			} else {
				investmentType = InvestmentType.STOCK;
			}
		}
		
		String[] columns = line.substring(line.length() - currentIndex).split(" ");
		amount = newBigDecimal(columns[0]);
		operationPrice = newBigDecimal(columns[2]);
		if(!isBuyElseAsk) {
			amount = amount.negate();
			operationPrice = operationPrice.negate();
		}
		price = newBigDecimal(columns[1]);
	}
	
	@Override
	public String getTicker() {
		return ticker;
	}

	@Override
	public BigDecimal getAmount() {
		return amount;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	@Override
	public BigDecimal getOperationPrice() {
		return operationPrice;
	}

	@Override
	public Order same() {
		return this;
	}

	@Override
	public String toString() {
		return "ClearOrder [isBuyElseAsk=" + isBuyElseAsk + ", ticker=" + ticker + ", amount=" + amount + ", price="
				+ price + ", operationPrice=" + operationPrice + ", investmentType="
				+ investmentType + "]";
	}

	@Override
	public BrokerReport getBrokerReport() {
		return brokerReport;
	}

	@Override
	public InvestmentType getInvestmentType() {
		return investmentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((brokerReport == null) ? 0 : brokerReport.hashCode());
		result = prime * result + ((investmentType == null) ? 0 : investmentType.hashCode());
		result = prime * result + ((isBuyElseAsk == null) ? 0 : isBuyElseAsk.hashCode());
		result = prime * result + (isFII ? 1231 : 1237);
		result = prime * result + ((operationPrice == null) ? 0 : operationPrice.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((ticker == null) ? 0 : ticker.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClearOrder other = (ClearOrder) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (brokerReport == null) {
			if (other.brokerReport != null)
				return false;
		} else if (!brokerReport.equals(other.brokerReport))
			return false;
		if (investmentType != other.investmentType)
			return false;
		if (isBuyElseAsk == null) {
			if (other.isBuyElseAsk != null)
				return false;
		} else if (!isBuyElseAsk.equals(other.isBuyElseAsk))
			return false;
		if (isFII != other.isFII)
			return false;
		if (operationPrice == null) {
			if (other.operationPrice != null)
				return false;
		} else if (!operationPrice.equals(other.operationPrice))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (ticker == null) {
			if (other.ticker != null)
				return false;
		} else if (!ticker.equals(other.ticker))
			return false;
		return true;
	}
	
	
}
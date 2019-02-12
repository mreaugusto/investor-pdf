package com.eaugusto.investorpdf.model.clear;

import java.util.HashMap;
import java.util.Map;

import com.eaugusto.investorpdf.api.Ticker;

public class ClearTicker implements Ticker {

	static Map<String, String> tickers = new HashMap<>();

	static {
		put("BANRISUL", "BRSR6");
		put("BBSEGURIDADE", "BBSE3");
		put("CIA HERING", "HGTX3");
		put("ENERGIAS BR", "ENBR3");
		put("HYPERA", "HYPE3");
		put("ITAUUNIBANCO", "ITUB4");
		put("QUALICORP", "QUAL3");
		put("SANEPAR", "SAPR11");
		put("TELEF BRASIL", "VIVT4");
		put("AMBEV S/A", "ABEV3");
		put("WIZ S.A.", "WIZS3");
		put("EZTEC", "EZTC3");
		put("PETROBRAS", "PETR4");
		put("BANCO INTER", "BIDI4");
		put("FII ABC IMOB", "ABCP11");
		put("FII BC FUND", "BRCR11");
		put("FII CSHG LOG", "HGLG11");
		put("FII CSHGJHSF", "HGJH11");
		put("FII HG REAL", "HGRE11");
		put("FII HOTEL MX", "HTMX11");
		put("FII JS REAL", "JSRE11");
		put("FII KINEA RI", "KNCR11");
		put("FII KINEA", "KNRI11");
		put("FII MALLS BP", "MALL11");
		put("FII RBRHGRAD", "RBRR11");
		put("FII VINCI SC", "VISC11");
		put("FII XP MALLS", "XPML11");
	}
	
	private static void put(String company, String ticker) {
		tickers.put(company, ticker);
	}
	
	@Override
	public String getTicker(String brokerLabel) {
		int separatorIndex = brokerLabel.indexOf("          ");
		String company = brokerLabel.substring(0, separatorIndex).trim();
		String ticker = tickers.get(company);
		if(ticker == null) {
			throw new IllegalStateException("O ticker " + company + " não foi encontrado!");
		}
		return ticker;
	}
}
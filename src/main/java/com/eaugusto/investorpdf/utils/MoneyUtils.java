package com.eaugusto.investorpdf.utils;

import java.math.BigDecimal;

public class MoneyUtils {
	public static final BigDecimal newBigDecimal(String value) {
		return new BigDecimal(value.replace(".", "").replace(",", "."));
	}
}
package com.eaugusto.investorpdf.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CashFlow {

	private LocalDate date;
	private String description;
	private Integer invoice;
	private String asset;
	private BigDecimal debit;
	private BigDecimal credit;
	
	public CashFlow(LocalDate date, String description, Integer invoice, String asset, BigDecimal debit, BigDecimal credit) {
		this.date = date;
		this.description = description;
		this.invoice = invoice;
		this.asset = asset;
		this.debit = debit;
		this.credit = credit;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getInvoice() {
		return invoice;
	}
	public void setInvoice(Integer invoice) {
		this.invoice = invoice;
	}
	public String getAsset() {
		return asset;
	}
	public void setAsset(String asset) {
		this.asset = asset;
	}
	public BigDecimal getDebit() {
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	
	@Override
	public String toString() {
		return "CashFlow [date=" + date + ", description=" + description + ", invoice=" + invoice + ", asset=" + asset
				+ ", debit=" + debit + ", credit=" + credit + "]";
	}
}
package com.javaee.juliana.investments.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockOfferRest { // Consumer
	
	public StockOfferRest(StockOffer stockOffer) {
		this.setId(stockOffer.getId());
		this.setQuantity(stockOffer.getQuantity());
		this.setQuantitySold(stockOffer.getQuantitySold());
		this.setPrice(stockOffer.getPrice());
		this.setTimestamp(stockOffer.getTimestamp());
		if (stockOffer.isCompanyOffer()) {
			this.setCompanyOffer(true);
		} else {
			this.setCompanyOffer(false);
			this.setInvestor(new InvestorRest(stockOffer.getInvestor()));
		}
		this.setTransaction(new TransactionRest(stockOffer.getTransaction()));
	}
	public StockOfferRest() {
	}
	
	private String id;
	private boolean companyOffer;
	private int quantity;
	private int quantitySold;
	private float price;
	private String timestamp;

	private InvestorRest investor;
	private TransactionRest transaction;
	private Set<Market> markets = new HashSet<>();
}

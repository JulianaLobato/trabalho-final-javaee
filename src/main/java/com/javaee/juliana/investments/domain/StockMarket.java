package com.javaee.juliana.investments.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockMarket {
	
	private boolean companyOffer = false;
	private String investorId;
	private String stockId;
	private int quantity;
	private float price;
	
}

package com.javaee.juliana.investments.domain;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="stock-offers")
public class StockOffer { // Vendors
	
	@Id
	private String id = UUID.randomUUID().toString();
	private boolean companyOffer = false;
	private int quantity;
	private int quantitySold;
	private float price;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();
	
	@DBRef(lazy = true)
	private Investor investor;
	@DBRef(lazy = true)
	private Transaction transaction;
	@DBRef(lazy = true)
	private Set<Market> markets = new HashSet<>();;

}

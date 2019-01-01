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
@Document(collection="stock-demands")
public class StockDemand { // Consumer
	
	@Id
	private String id = UUID.randomUUID().toString();
	private int quantity;
	private int quantityBought;
	private float price;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

	@DBRef(lazy = true)
	private Investor investor;
	@DBRef(lazy = true)
	private Transaction transaction;
	@DBRef(lazy = true)
	private Set<Market> markets = new HashSet<>();;

}

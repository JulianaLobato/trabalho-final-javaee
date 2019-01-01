package com.javaee.juliana.investments.domain;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="markets")
public class Market {

	@Id
	private String id = UUID.randomUUID().toString();
	private int quantity;
	private float price;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();
		
	@DBRef(lazy = true)
	private StockDemand demand;
	@DBRef(lazy = true)
	private StockOffer offer;

}

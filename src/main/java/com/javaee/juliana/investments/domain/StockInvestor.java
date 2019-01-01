package com.javaee.juliana.investments.domain;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="stock-investors")
public class StockInvestor {
	
	@Id
	private String id = UUID.randomUUID().toString();
	
	private int quantity;
	private String StockId;
	private String InvestorId;
	
}

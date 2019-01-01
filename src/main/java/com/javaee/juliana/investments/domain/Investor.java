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
@Document(collection="investors")
public class Investor {

	@Id
	private String id = UUID.randomUUID().toString();
	private String name;
	private String email;
	private String timestamp = new Timestamp(System.currentTimeMillis()).toString();

	@DBRef(lazy = true)
	private Set<StockInvestor> stocks = new HashSet<>();

	@DBRef(lazy = true)
	private Set<StockDemand> demands = new HashSet<>();

	@DBRef(lazy = true)
	private Set<StockOffer> offers = new HashSet<>();

	@DBRef(lazy = true)
	private Set<StockMarket> market = new HashSet<>();
}

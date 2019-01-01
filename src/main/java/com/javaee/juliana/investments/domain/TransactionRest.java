package com.javaee.juliana.investments.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRest {
	public TransactionRest(Transaction transaction) {
		this.setId(transaction.getId());
		this.setName(transaction.getName());
		this.setQuantity(transaction.getQuantity());
		this.setQuantityCompany(transaction.getQuantityCompany());
		this.setInitialPrice(transaction.getInitialPrice());
		this.setTimestamp(transaction.getTimestamp());
	}

	private String id;
	private String name;
	private int quantity;
	private int quantityCompany;
	private float initialPrice;
	private String timestamp;

}

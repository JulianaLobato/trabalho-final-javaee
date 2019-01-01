package com.javaee.juliana.investments.domain;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection="messages")
public class Message implements Serializable{
	public Message(String source, StockMarket stockMarket) {
		this.setSource(source);
		this.setData(stockMarket);
	}
	public Message() {		
	}
	
	private static final long serialVersionUID = 1L;
	
	private String id = UUID.randomUUID().toString();
	private String source; 
	private StockMarket data;
}
package com.javaee.juliana.investments.services;

import java.util.Set;

import com.javaee.juliana.investments.domain.MessageId;
import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;
import ccom.javaee.juliana.investments.domain.StockMarket;
import com.javaee.juliana.investments.domain.StockOffer;

public interface TransactionService {
	Set<Transaction> getAll();

	Transaction getById(String id);

	Transaction createNew(Transaction transaction);

	Transaction emitNew(String companyId, Transaction transaction);

	Transaction save(String id, Transaction transaction);
		
	void deleteById(String id);
	
	Set<StockDemand> getAllDemands();

	Set<StockOffer> getAllOffers();

	MessageId sendMessage(String source, StockMarket stockMarket);
}

package com.javaee.juliana.investments.services;

import java.util.Set;

import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;
import com.javaee.juliana.investments.domain.StockMarket;

public interface DemandService {

	StockDemand createNew(StockMarket stockMarket);
	
	Set<StockDemand> getAll();

	Set<StockDemand> getAllByStock(Transaction transaction);

	StockDemand save(StockDemand demand);

}

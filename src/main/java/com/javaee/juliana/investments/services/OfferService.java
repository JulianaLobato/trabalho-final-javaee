package com.javaee.juliana.investments.services;

import com.javaee.juliana.investments.domain.StockMarket;
import com.javaee.juliana.investments.domain.StockOffer;

public interface OfferService {

	StockOffer createNew(StockMarket stockMarket);
	
	StockOffer save(StockOffer offer);
	
}

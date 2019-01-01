package com.javaee.juliana.investments.services;

import java.util.Comparator;

import com.javaee.juliana.investments.domain.StockDemand;

public class StockDemandComparatorByStockAndTimestamp implements Comparator<StockDemand> {

	@Override
	public int compare(StockDemand d1, StockDemand d2) {
		return d1.getStock().getId().compareTo(d2.getStock().getId()) & d1.getTimestamp().compareTo(d2.getTimestamp());
	}

}

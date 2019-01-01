package com.javaee.juliana.investments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.juliana.investments.domain.Market;
import com.javaee.juliana.investments.repositories.MarketRepository;

@Service
public class MarketServiceImpl implements MarketService {
	
	@Autowired
	MarketRepository marketRepository;

	@Override
	public Market save(Market market) {
		market.setId(market.getId());
		return marketRepository.save(market);
	}

}

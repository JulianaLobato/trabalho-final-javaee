package com.javaee.juliana.investments.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.Market;

@Repository
public interface MarketRepository extends MongoRepository<Market, String>{
}

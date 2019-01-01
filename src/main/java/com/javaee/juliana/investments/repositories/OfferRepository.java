package com.javaee.juliana.investments.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.StockOffer;

@Repository
public interface OfferRepository extends MongoRepository<StockOffer, String>{
}

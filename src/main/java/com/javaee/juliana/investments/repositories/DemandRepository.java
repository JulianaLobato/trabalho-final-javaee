package com.javaee.juliana.investments.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;

@Repository
public interface DemandRepository extends MongoRepository<StockDemand, String>{
	List<DemandRepository> findByStock(Transaction transaction);
}

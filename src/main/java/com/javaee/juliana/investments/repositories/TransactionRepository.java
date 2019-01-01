package com.javaee.juliana.investments.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>{
	List<Transaction> findByName(String name);
}

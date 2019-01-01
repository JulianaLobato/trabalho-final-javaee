package com.javaee.juliana.investments.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.Investor;

@Repository
public interface InvestorRepository extends  MongoRepository<Investor, String>{
	List<Investor> findByName(String name);
	List<Investor> findByEmail(String email);
}

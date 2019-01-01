package com.javaee.juliana.investments.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String>{
	List<Company> findByName(String name);
	List<Company> findByEmail(String email);
}

package com.javaee.juliana.investments.services;

import java.util.Set;

import com.javaee.juliana.investments.domain.Company;
import com.javaee.juliana.investments.domain.Transaction;

public interface CompanyService {
	Set<Company> getAll();

	Company getById(String id);

	Company createNew(Company company);

	Company save(String id, Company company);

	void deleteById(String id);

	Company addStock(String id, Transaction transaction);

	Set<Transaction> getAllStocks(String companyId);

	Transaction getStockById(String companyId, String stockId);
}

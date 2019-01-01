package com.javaee.juliana.investments.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.juliana.investments.domain.Company;
import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private TransactionService transactionService;
	//@Autowired
	//private TransactionRepository stransactionRepository;

	public Set<Company> getAll() {
		Set<Company> companies = new HashSet<>();
		companyRepository.findAll().iterator().forEachRemaining(companies::add);
		return companies;
	}

	public Company getById(String id) {
		return getCompanyById(id);
	}

	private Company getCompanyById(String id) {
		Optional<Company> companyOptional = companyRepository.findById(id);

		if (!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Company not found for ID value: " + id.toString());
		}

		return companyOptional.get();
	}

	public Company createNew(Company company) {
		Company companyInd0;
		try {
			companyInd0 = companyRepository.findByEmail(company.getEmail()).get(0);
		} catch (Exception e) {
			return companyRepository.save(company);			
		}
		throw new IllegalArgumentException("Company already exists with ID: " + companyInd0.getId());
	}

	public Company save(String id, Company company) {
		company.setId(id);
		return companyRepository.save(company);
	}

	public void deleteById(String id) {
		companyRepository.deleteById(id);
	}

	public Company addStock(String companyId, Transaction transaction) {
		Company company = getCompanyById(companyId);
		Set<Transaction> transactions = company.getTransaction();
		transactions.remove(null);
		transactions.add(transactionService.createNew(transaction));
		company.setTransaction(transactions);
		return save(companyId, company);
	}

	public Set<Transaction> getAllStocks(String companyId) {
		return getCompanyById(companyId).getTransaction();
	}

	public Transaction getStockById(String companyId, String stockId) {
		for (Transaction transaction : getCompanyById(companyId).getTransaction()) {
			if (transaction.getId().equals(stockId)) {
				return transaction;
			}
		}
		throw new IllegalArgumentException("Stock not found for ID value: " + stockId.toString() + " for Company " + companyId.toString());
	}

}

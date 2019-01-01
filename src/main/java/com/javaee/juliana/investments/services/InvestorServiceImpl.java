package com.javaee.juliana.investments.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ccom.javaee.juliana.investments.domain.Investor;
import com.javaee.juliana.investments.repositories.InvestorRepository;

@Service
public class InvestorServiceImpl implements InvestorService {

	@Autowired
	private InvestorRepository investorRepository;

	@Override
	public Set<Investor> getAll() {
		Set<Investor> investors = new HashSet<>();
		investorRepository.findAll().iterator().forEachRemaining(investors::add);
		return investors;
	}

	@Override
	public Investor getById(String id) {
		return getInvestorById(id);
	}

	private Investor getInvestorById(String id) {
		Optional<Investor> investorOptional = investorRepository.findById(id);

		if (!investorOptional.isPresent()) {
			throw new IllegalArgumentException("Investor not found for ID value: " + id.toString());
		}

		return investorOptional.get();
	}
	
	@Override
	public Investor createNew(Investor investor) {
		Investor investorInd0;
		try {
			investorInd0 = investorRepository.findByName(investor.getName()).get(0);
		} catch (Exception e) {
			return investorRepository.save(investor);
		}
		throw new IllegalArgumentException("Investor already exists with ID: " + investorInd0.getId());
	}

	@Override
	public Investor save(String id, Investor investor) {
		investor.setId(id);
		return investorRepository.save(investor);
	}

	@Override
	public void deleteById(String id) {
		investorRepository.deleteById(id);
	}

}

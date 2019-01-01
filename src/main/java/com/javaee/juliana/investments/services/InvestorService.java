package com.javaee.juliana.investments.services;

import java.util.Set;

import com.javaee.juliana.investments.domain.Investor;

public interface InvestorService {
	Set<Investor> getAll();

	Investor getById(String id);

	Investor createNew(Investor buyer);

	Investor save(String id, Investor buyer);

	void deleteById(String id);
}

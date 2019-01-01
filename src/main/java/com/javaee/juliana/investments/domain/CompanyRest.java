package com.javaee.juliana.investments.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRest {
	public CompanyRest(Company company) {
		this.setId(company.getId());
		this.setName(company.getName());
		this.setEmail(company.getEmail());
		this.setTimestamp(company.getTimestamp());
	}

	private String id;
	private String name;
	private String email;
	private String timestamp;

}

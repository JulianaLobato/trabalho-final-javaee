package com.javaee.juliana.investments.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestorRest {
	public InvestorRest(Investor investor) {
		this.setId(investor.getId());
		this.setName(investor.getName());
		this.setEmail(investor.getEmail());
		this.setTimestamp(investor.getTimestamp());
	}
	
	private String id;
	private String name;
	private String email;
	private String timestamp;

}

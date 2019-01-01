package com.javaee.juliana.investments.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageId {
	public MessageId(String id) {
		this.setId(id);
	}

	private String id;

}

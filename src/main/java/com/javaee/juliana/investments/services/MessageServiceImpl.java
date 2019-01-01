package com.javaee.juliana.investments.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.juliana.investments.domain.Message;
import com.javaee.juliana.investments.repositories.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	MessageRepository messageRepository;

	@Override
	public Message createNew(Message message) {
		return messageRepository.save(message);
	}

}

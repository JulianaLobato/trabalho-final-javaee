package com.javaee.juliana.investments.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.juliana.investments.domain.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{
}

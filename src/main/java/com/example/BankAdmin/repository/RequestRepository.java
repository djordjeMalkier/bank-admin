package com.example.BankAdmin.repository;

import com.example.BankAdmin.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, String> {

}

package com.petstore.counter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.petstore.counter.domain.Counter;

public interface CounterRepository extends MongoRepository<Counter, String> {

}

package com.petstore.pet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.petstore.pet.domain.Pet;

@Repository
public interface PetRepository extends MongoRepository<Pet, Integer> {
	
	Iterable<Pet> findBypetName(String petName);

}

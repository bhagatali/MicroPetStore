package com.petstore.tag.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.petstore.tag.domain.Tag;

public interface TagRepository extends MongoRepository<Tag, Integer> {
	
	Iterable<Tag> findBytagName(String tagName);

}

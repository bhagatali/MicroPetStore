package com.petstore.category.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.petstore.category.domain.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, Integer> {
	
	Iterable<Category> findBycategoryName(String categoryName);

}

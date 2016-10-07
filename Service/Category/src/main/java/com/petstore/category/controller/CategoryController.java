package com.petstore.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.petstore.category.domain.Category;
import com.petstore.category.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

	private CategoryRepository categoryRepository;
	private RestTemplate restTemplate;
	
	@Value("${counter.service.uri}")
	public String counterUri;

	@Autowired
	public CategoryController(CategoryRepository categoryRepository,
            RestTemplate restTemplate) {
		this.categoryRepository = categoryRepository;
		this.restTemplate = restTemplate;
	}
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public Iterable<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
//	@HystrixCommand
	public Category createCategory(@RequestBody Category category){
		String categoryCounterUri = counterUri + "category";
		category.setId(restTemplate.getForObject(categoryCounterUri, Integer.class));
		return categoryRepository.save(category);
	}
	
	@RequestMapping(value="/", method = RequestMethod.DELETE)
	public void deleteCategory(){
		categoryRepository.deleteAll();
	}
	
	@RequestMapping(value="/{categoryName}", method = RequestMethod.GET)
	public Iterable<Category> getCategoryByName(@PathVariable String categoryName){
		return categoryRepository.findBycategoryName(categoryName);
	}
}

package com.petstore.pet.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.petstore.pet.domain.Category;
import com.petstore.pet.domain.Pet;
import com.petstore.pet.domain.Tag;
import com.petstore.pet.repository.PetRepository;

@RestController
@RequestMapping("/pet")
public class PetController{
	
	private PetRepository petRepository;
	private RestTemplate restTemplate;

	@Value("${category.service.uri}")
	public String categoryUri;
	@Value("${tag.service.uri}")
	public String tagUri;	
	@Value("${counter.service.uri}")
	public String counterUri;
	
	@Autowired
	public PetController(PetRepository petRepository,
			             RestTemplate restTemplate) {
		this.petRepository = petRepository;
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public Map<String, Object> getAllPet(){
		List<Pet> pets = petRepository.findAll();
	    Map<String, Object> response = new LinkedHashMap<String, Object>();
	    response.put("totalPets", pets.size());
	    response.put("pets", pets);
	    return response;		
	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	@HystrixCommand
	public Map<String, Object> createPet(@RequestBody Pet pet){
		Iterable<Category> selectedCategory = pet.getCategories();
		List<Category> petSaveCategory = new ArrayList<Category>();
		
		if(selectedCategory!=null){
			for (Category petCategory:selectedCategory){
				Category category = new Category();
				
				category.setCategoryName(petCategory.getCategoryName());

				Category categoryResponse = restTemplate.postForObject(categoryUri, category, Category.class);
				petSaveCategory.add(categoryResponse);
			}
		}
		
		Iterable<Tag> selectedTag = pet.getTags();
		List<Tag> petSaveTag = new ArrayList<Tag>();
		
		if(selectedTag!=null){
			for (Tag petTag:selectedTag){
				Tag tag = new Tag();
				
				tag.setTagName(petTag.getTagName());

				Tag tagResponse = restTemplate.postForObject(tagUri, tag, Tag.class);
				petSaveTag.add(tagResponse);
			}
		}
		
		String petCounterUri = counterUri + "pet";
		pet.setId(restTemplate.getForObject(petCounterUri, Integer.class));
		pet.setCategories(petSaveCategory);
		pet.setTags(petSaveTag);		
		
		Pet result = petRepository.save(pet);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("message", "Pet created successfully");
	    response.put("pet", result);
		return response;
	}
	
	@RequestMapping(value="/", method = RequestMethod.DELETE)
	public void deleteAllPet(){
		petRepository.deleteAll();
	}
	
	@RequestMapping(value="/{petId}", method = RequestMethod.GET)
	public Pet getPet(@PathVariable Integer petId){
		Pet result = petRepository.findOne(petId);
		return result;
	}
	
	@RequestMapping(value="/{petId}", method = RequestMethod.DELETE)
	public void deletePet(@PathVariable Integer petId){
		petRepository.delete(petId);
	}
}

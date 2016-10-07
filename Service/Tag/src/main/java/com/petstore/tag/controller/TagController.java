package com.petstore.tag.controller;

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
import com.petstore.tag.domain.Tag;
import com.petstore.tag.repository.TagRepository;

@RestController
@RequestMapping("/tag")
//@CrossOrigin(origins = "http://localhost:9000")
@CrossOrigin
public class TagController {

	private TagRepository tagRepository;
	private RestTemplate restTemplate;

	@Value("${counter.service.uri}")
	String counterUri;
	
	@Autowired
	public TagController(TagRepository tagRepository, RestTemplate restTemplate) {
		this.tagRepository = tagRepository;
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value="/",method=RequestMethod.GET)
	public Iterable<Tag> getAllTags(){
		return tagRepository.findAll();
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
//	@HystrixCommand
	public Tag createTag(@RequestBody Tag tag){
		String tagCounterUri = counterUri + "tag";
		System.out.println("Counter URL: " + tagCounterUri);
		tag.setId(restTemplate.getForObject(tagCounterUri, Integer.class));
		System.out.println("After the Tag");
		return tagRepository.save(tag);
	}
	
	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public void deleteAllTags(){
		tagRepository.deleteAll();
	}
	
	@RequestMapping(value="/{tagName}", method=RequestMethod.GET)
	public Iterable<Tag> getTag(@PathVariable String tagName){
		return tagRepository.findBytagName(tagName);
	}	
}

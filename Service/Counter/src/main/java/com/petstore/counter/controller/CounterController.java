package com.petstore.counter.controller;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.petstore.counter.domain.Counter;
import com.petstore.counter.repository.CounterRepository;

@RestController
@RequestMapping("/counter")
public class CounterController {

	  private MongoOperations mongo;
	  private CounterRepository counterRepository;
	 
	  @Autowired
	  public CounterController(MongoOperations mongo, CounterRepository counterRepository) {
		this.mongo = mongo;
		this.counterRepository = counterRepository;
	}

	  @RequestMapping(value="/", method=RequestMethod.GET)
	  public Iterable<Counter> getAllCounters(){
		return counterRepository.findAll();  
	  }
	  
	  @RequestMapping(value="/{collectionName}", method=RequestMethod.GET)
	  public int getNextSequence(@PathVariable String collectionName) {
		    Counter counter = mongo.findAndModify
		    		(
		              query(where("_id").is(collectionName)
		            ), 
		            new Update().inc("seq", 1),
		            options().returnNew(true),
		            Counter.class);
		    return counter.getSeq();
		  }

	  @RequestMapping(value="/", method=RequestMethod.POST)	
	  public Counter insertSequence(@RequestBody Counter counter){
		  return counterRepository.save(counter);
	  }
}

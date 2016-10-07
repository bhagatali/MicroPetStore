package com.petstore.pet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.petstore.pet.controller.PetController;
import com.petstore.pet.domain.Category;
import com.petstore.pet.domain.Pet;
import com.petstore.pet.domain.Tag;
import com.petstore.pet.repository.PetRepository;


//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = PetApplication.class)
//@WebAppConfiguration

@RunWith(SpringRunner.class)
@SpringBootTest({"server.port:0", "eureka.client.registerWithEureka:false", "eureka.client.fetchRegistry:false"})
public class PetApplicationTests {

	Integer petId1,petId2, expectedPetCounts;
	
    @Mock
    RestTemplate restTemplate;
    
    @Autowired
    @InjectMocks
    private PetController petController;
    
    @Autowired
    PetRepository petRepository;
    Category category;
    
    @Before
    public void setup(){
    	MockitoAnnotations.initMocks(this);
    	
    	Map<String, Object> apiResponse = petController.getAllPet();
    	expectedPetCounts = Integer.parseInt(apiResponse.get("totalPets").toString()); 
		
    	//Add some test data for the API
		List<Category> categoryList1 = new ArrayList<Category>();
		categoryList1.add(new Category(50,"Category50"));
		categoryList1.add(new Category(51,"Category51"));
		
		List<Tag> tagList1 = new ArrayList<Tag>();
		tagList1.add(new Tag(50,"Tag50"));
		tagList1.add(new Tag(51,"Tag51"));		
		
		List<String> photos1 = new ArrayList<String>();
		photos1.add("photo50");
		photos1.add("photo51");
		
		Pet pet1 = new Pet(50, categoryList1, "Buster", photos1, tagList1, "SOLD", 100);
		petRepository.save(pet1);

		List<Category> categoryList2 = new ArrayList<Category>();
		categoryList2.add(new Category(150,"Category50"));
		categoryList2.add(new Category(151,"Category51"));
		
		List<Tag> tagList2 = new ArrayList<Tag>();
		tagList2.add(new Tag(50,"Tag150"));
		tagList2.add(new Tag(51,"Tag151"));		
		
		List<String> photos2 = new ArrayList<String>();
		photos2.add("photo150");
		photos2.add("photo151");

		Pet pet2 = new Pet(150, categoryList2, "Palm", photos2, tagList2, "BUY", 500);
		petRepository.save(pet2);  
		
		petId1 = pet1.getId();
		petId2 = pet2.getId();
		
		expectedPetCounts += 2;
    }

    @After
    public void tear() {
		 //Delete the test data created
		 petController.deletePet(petId1);
		 petController.deletePet(petId2);
	}

    @Ignore
    @Test
    public void createAPetShouldReturnPetCreated(){
    	//Add some test data for the API
		List<Category> categoryList1 = new ArrayList<Category>();
		
		Category category1 = new Category(80,"Category80");
		Category category2 = new Category(81,"Category81");

		categoryList1.add(category1);
		categoryList1.add(category2);
		
		List<Tag> tagList1 = new ArrayList<Tag>();
		
		Tag tag1 = new Tag(80,"Tag80");
		Tag tag2 = new Tag(81,"Tag81");
		
		tagList1.add(tag1);
		tagList1.add(tag2);		
		
		List<String> photos1 = new ArrayList<String>();
		photos1.add("photo80");
		photos1.add("photo81");
		
		Pet pet1 = new Pet(80, categoryList1, "Spike", photos1, tagList1, "PENDING", 800);

		when(restTemplate.postForObject(eq("http://localhost:9081/category/"), eq(category1), eq(Category.class)))
		.thenReturn(category1);
		when(restTemplate.postForObject(eq("http://localhost:9081/category/"), eq(category1), eq(Category.class)))
		.thenReturn(category2);

		when(restTemplate.postForObject(eq("http://localhost:9082/tag/"), eq(tag1), eq(Tag.class)))
		.thenReturn(tag1);
		when(restTemplate.postForObject(eq("http://localhost:9082/tag/"), eq(tag2), eq(Tag.class)))
		.thenReturn(tag2);
		
		Map<String, Object> response = petController.createPet(pet1);
		
		assertNotNull(response);

		//Asserting the response of the API.
		
	    String message = response.get("message").toString();
	    assertEquals("Pet created successfully", message);
	    
	    @SuppressWarnings("unchecked")
		Integer petId = (Integer) ((Map<String, Object>)response.get("pet")).get("id");
	    assertNotNull(petId);		

	    
//    	//Fetching the Pet details directly from the DB to verify the API succeeded
//	    Pet petFromDB = petRepository.findOne(petId);
//	    assertEquals("Spike", petFromDB.getPetName());
//	    assertEquals("PENDING", petFromDB.getStatus());
//	    assertEquals("800", petFromDB.getPrice().toString());
//	    assertNotNull(petFromDB.getCategories());
//	    
//	    //Delete the data added for testing
//	    petRepository.delete(petId);
    };
    
    @Test
    public void getAllPetShouldReturnAllPets() {

		 //Invoke the API
		//Map<String, Object> apiResponse = restTemplate.getForObject("http://localhost:8080/pet/", Map.class);
		 Map<String, Object> allPets = petController.getAllPet();
			
		 //Assert the response from the API
		 int actualPetCounts = Integer.parseInt(allPets.get("totalPets").toString());
		 assertTrue(actualPetCounts == expectedPetCounts);
		 
		 @SuppressWarnings("unchecked")
    	 List<Map<String, Object>> petlistSize = (List<Map<String, Object>>)allPets.get("pets");
		 assertTrue(petlistSize.size() == expectedPetCounts);
    }
	
	@Test
    public void getAPetThatExistsInDBShouldReturnPetname() {
		assertTrue(petController.getPet(50).getPetName().equals("Buster"));
		assertTrue(petController.getPet(150).getPetName().equals("Palm"));		
    }

	@Test
    public void getAPetThatDoesNotExistsInDBShouldNotError() {
		assertNull(petController.getPet(8));
    }

	@Test
    public void deleteAPetShouldRemoveRecordFromDB() {
		petController.deletePet(150);
		assertNull(petController.getPet(150));
    }

	@Ignore
	@Test
    public void deleteAllPetShouldRemoveAllRecordsFromDB() {
		petController.deleteAllPet();
		assertNull(petController.getPet(50));
    }	
}

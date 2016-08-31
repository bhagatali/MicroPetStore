package com.petstore.pet.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="pet")
public class Pet {
	
	@Id
	private Integer id;
	@DBRef
	private List<Category> categories;
	private String petName;
	private List<String> photoUrl;
	@DBRef
	private List<Tag> tags;
	private String status;
	private Integer price;
	
	public Pet() {
		super();
	}

	public Pet(Integer id, List<Category> categories, String petName, List<String> photoUrl, List<Tag> tags,
			String status, Integer price) {
		super();
		this.id = id;
		this.categories = categories;
		this.petName = petName;
		this.photoUrl = photoUrl;
		this.tags = tags;
		this.status = status;
		this.price = price;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getPetName() {
		return petName;
	}
	public void setPetName(String petName) {
		this.petName = petName;
	}
	public List<String> getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotUrl(List<String> photoUrl) {
		this.photoUrl = photoUrl;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Pet [id=" + id + ", petName=" + petName + ", status=" + status + ", price=" + price + "]";
	}
	
}

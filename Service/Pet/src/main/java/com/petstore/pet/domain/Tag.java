package com.petstore.pet.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tag")
public class Tag {

	@Id
	private Integer id;
	private String tagName;
	
	
	public Tag() {
		super();
	}

	public Tag(Integer id, String tagName) {
		super();
		this.id = id;
		this.tagName = tagName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Override
	public String toString() {
		return "Tags [id=" + id + ", tagName=" + tagName + "]";
	}
	
}

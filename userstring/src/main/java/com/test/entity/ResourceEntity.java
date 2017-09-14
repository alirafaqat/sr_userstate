package com.test.entity;

import org.apache.commons.lang3.StringUtils;

//@Entity
public class ResourceEntity {
	String data;

	public ResourceEntity() {
		data = new String();
	}
	
	public ResourceEntity(String data) {
		super();
		this.data = data;
	}


	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public void appendData(Character character, int count) {
		this.data += StringUtils.repeat(character, count);
	}
	
}

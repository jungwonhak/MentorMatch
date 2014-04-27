package com.codepath.mentormatch.models;

public enum Status {
	OPEN ("Java"), 
	CLOSED ("Ruby");
	
	private String description;
	
	Status(String descr) {
		description = descr;
	}
	
	public String toString() {
		return description;
	}
}

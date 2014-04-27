package com.codepath.mentormatch.models;

public enum Status {
	OPEN ("Open"), 
	CLOSED ("Closed");
	
	private String description;
	
	Status(String descr) {
		description = descr;
	}
	
	public String toString() {
		return description;
	}
}

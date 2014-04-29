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
	
	public static Status fromString(String value) {
		if(Status.OPEN.toString().equals(value)) {
			return Status.OPEN;
		} else if(Status.CLOSED.toString().equals(value)) {
			return Status.CLOSED;
		}
		return Status.OPEN;
	}
}

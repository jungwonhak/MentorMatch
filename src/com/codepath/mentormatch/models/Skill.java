package com.codepath.mentormatch.models;

import java.io.Serializable;

public enum Skill implements Serializable{
	JAVA ("Java"), 
	RUBY ("Ruby"), 
	PYTHON ("Python"),
	IOS ("ios"),
	PHP ("php"),
	ANDROID ("Android");
	
	private String description;

	Skill(String value) {
		this.description = value;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	public String getLogo() {
		return toString() + "_logo.png";
	}
	public static Skill fromValue(String value) throws IllegalArgumentException {
        try{
        	for(Skill skill : Skill.values()) {
        		if(skill.toString().equalsIgnoreCase(value)) {
        			return skill;
        		}
        	}
        }catch( ArrayIndexOutOfBoundsException e ) {
             throw new IllegalArgumentException("Unknown enum value :"+ value);
             
		}
        return null;
	}
}

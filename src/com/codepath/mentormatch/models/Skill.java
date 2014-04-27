package com.codepath.mentormatch.models;

import java.io.Serializable;

import com.codepath.mentormatch.R;

public enum Skill implements Serializable {
	JAVA ("Java", R.drawable.ic_launcher), 
	RUBY ("Ruby", R.drawable.ruby_logo), 
	PYTHON ("Python", R.drawable.python_logo),
	IOS ("ios", R.drawable.ios_logo),
	PHP ("php", R.drawable.ic_launcher),
	ANDROID ("Android", R.drawable.android_logo);
	
	private String description;
	private int resourceId;

	Skill(String value, int resource) {
		this.description = value;
		this.resourceId = resource;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	public int getResourceId() {
		return resourceId;
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

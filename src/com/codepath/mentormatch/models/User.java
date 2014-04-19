package com.codepath.mentormatch.models;

import java.util.List;

import org.json.JSONObject;

import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("_User") 
public class User extends ParseUser{

	public static final String NAME_KEY = "name";
	public static final String LOCATION_KEY = "location";
	public static final String DESCRIPTION_KEY = "description";
	public static final String JOB_TITLE_KEY = "jobTitle";
	public static final String COMPANY_KEY = "company";
	public static final String YEAR_EXP_KEY = "yearsOfExperience";
	public static final String LINKED_IN_URL_KEY = "linkedInUrl";
	public static final String GITHUB_URL_KEY = "githubUrl";
	public static final String PROFILE_IMG_URL_KEY = "profileImageUrl";
	public static final String ACCESS_TOKEN_KEY = "accessToken";
	public static final String SKILLS_LIST_KEY = "skills";

	public User() {
		super();
	}
	
	public User(JSONObject userJson) {
		super();
	}
	
	public String getLocation() {
		return getString(LOCATION_KEY);
	}

	public void setLocation(String loc) {
		put(LOCATION_KEY, loc);
	}
	
	public String getDescription() {
		return getString(DESCRIPTION_KEY);
	}

	public void setDescription(String value) {
		put(DESCRIPTION_KEY, value);
	}
	
	public String getJobTitle() {
		return getString(JOB_TITLE_KEY);
	}

	public void setJobTitle(String value) {
		put(JOB_TITLE_KEY, value);
	}
	
	public String getCompany() {
		return getString(COMPANY_KEY);
	}

	public void setCompany(String value) {
		put(COMPANY_KEY, value);
	}
	
	public double getYearsExperience() {
		return getDouble(YEAR_EXP_KEY);
	}

	public void setYearsExperience(double value) {
		put(YEAR_EXP_KEY, value);
	}

	public String getLinkedInUrl() {
		return getString(LINKED_IN_URL_KEY);
	}

	public void setLinkedInUrl(String value) {
		put(LINKED_IN_URL_KEY, value);
	}
	
	public String getGithubUrl() {
		return getString(GITHUB_URL_KEY);
	}

	public void setGithubUrl(String value) {
		put(GITHUB_URL_KEY, value);
	}
	
	public String getProfileImage() {
		return getString(PROFILE_IMG_URL_KEY);
	}

	public void setProfileImage(String value) {
		put(PROFILE_IMG_URL_KEY, value);
	}
	
	public String getAccessToken() {
		return getString(ACCESS_TOKEN_KEY);
	}

	public void setAccessToken(String value) {
		put(ACCESS_TOKEN_KEY, value);
	}
	
	public List<String> getSkills() {
		return getList(SKILLS_LIST_KEY);
	}

	public void setSkills(List<String> value) {
		put(SKILLS_LIST_KEY, value);
	}
	
}

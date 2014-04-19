package com.codepath.mentormatch.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.util.Log;

import com.parse.LogInCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("_User") 
public class User extends ParseUser{

	public static final String NAME_KEY = "name";
	public static final String LOCATION_KEY = "location";
	public static final String DESCRIPTION_KEY = "description";
	public static final String JOB_TITLE_KEY = "jobTitle";
	public static final String COMPANY_KEY = "company";
	public static final String LINKED_IN_URL_KEY = "linkedInUrl";
	public static final String GITHUB_URL_KEY = "githubUrl";
	public static final String PROFILE_IMG_URL_KEY = "profileImageUrl";
	public static final String ACCESS_TOKEN_KEY = "accessToken";
	public static final String SKILLS_LIST_KEY = "skills";
	public static final String FULL_NAME_KEY = "fullName";
	
	/** LinkedIn User:
	 * 		- Username = LinkedIn ProfileId
	 * 		- Password = Access Token
	 * Regular User:
	 * 		- Username = email
	 * 		- Password =  password
	*/
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

	public String getFullName() {
		return getString(FULL_NAME_KEY);
	}

	public void setFullName(String value) {
		put(FULL_NAME_KEY, value);
	}

	public List<String> getSkills() {
		return getList(SKILLS_LIST_KEY);
	}

	public void setSkills(List<String> value) {
		put(ACCESS_TOKEN_KEY, value);
	}
		
	// Email is the username
	public static User fromRegularUser(String email, String password) {
		User u = new User();
		u.setUsername(email);
		u.setEmail(email);
		u.setPassword(password);
		return u;
	}
	
	// LinkedIn ProfileId is the username
	public static User fromLinkedInUser(LinkedInUser linkedInUser) {
		User u = new User();
		
		try {			
			u.setUsername(linkedInUser.getProfileId());
			u.setPassword(linkedInUser.getAccessToken());

			u.setLocation(linkedInUser.getLocation());
			u.setEmail(linkedInUser.getEmailAddress());
			u.setJobTitle(linkedInUser.getTitle());
			u.setFullName(linkedInUser.getFullName());
			u.setProfileImage(linkedInUser.getPictureUrl());
			u.setCompany(linkedInUser.getCompany());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public static User getUserIfExists(String userName) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		List<ParseUser> results = new ArrayList<ParseUser>();
		query.whereEqualTo("username", userName);
		try {
			results = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (results.size() > 0) {
			return (User) results.get(0);
		}
		return null;
	}
	
    public void loginToParse(String password) {
    	ParseUser.logInInBackground(this.getUsername(), password, new LogInCallback() {
    		  public void done(ParseUser user, ParseException e) {
    		    if (user != null) {
    		      // Hooray! The user is logged in.
    		    	Log.d("DEBUG", "User is now logged in");
    		    } else {
    		    	Log.d("DEBUG", "Signin failed");
    		      // Signup failed. Look at the ParseException to see what happened.
    		    }
    		  }
    		});	
    }
}

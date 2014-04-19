package com.codepath.mentormatch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.scribe.model.Token;


public class LinkedInUser {
	public String getProfileId() {
		return profileId;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFullName() {
		return firstName + " " + lastName;
	}

	public String getLocation() {
		return location;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public String getCompany() {
		if (currentPositions.size() > 0) {
			return currentPositions.get(0).getCompany();
		}
		return null;
	}

	public String getTitle() {
		if (currentPositions.size() > 0) {
			return currentPositions.get(0).getTitle();
		}
		return null;
	}
	
	public void setAccessToken(Token token) {
		accessToken = token.getToken();
	}
	
	public String getAccessToken() {
		return accessToken;
	}
	
	private String accessToken;
	private String profileId;
	private String emailAddress;
	private String firstName;
	private String lastName;
	// https://developer.linkedin.com/forum/dictionary-locations
	private String location;
	private String pictureUrl;
	private ArrayList<LinkedInSkill> skills;
	private ArrayList<LinkedInPosition> currentPositions;

	@Override
	public String toString() {
		String output = String.format("User id: %s, name: %s %s, emailAddress: %s, location: %s, picture: %s%n", 
				profileId, firstName, lastName, emailAddress, location, pictureUrl);

		for (LinkedInSkill s : skills) {
			output += s.toString() + "\n";
		}
		
		for (LinkedInPosition p : currentPositions) {
			output += p.toString() + "\n";
		}
		
		return output;	
	}
	
	public static LinkedInUser fromJson(JSONObject jsonObject) {
		LinkedInUser u = new LinkedInUser();
		
		try {			
			u.profileId = jsonObject.getString("id");
			u.emailAddress = jsonObject.getString("emailAddress");
			u.firstName = jsonObject.getString("firstName");
			u.lastName = jsonObject.getString("lastName");
			u.pictureUrl = jsonObject.getString("pictureUrl");
			u.location = jsonObject.getJSONObject("location").getString("name");
			u.skills = LinkedInSkill.fromJson(jsonObject.getJSONObject("skills").getJSONArray("values"));
			u.currentPositions = LinkedInPosition.fromJson(jsonObject.getJSONObject("threeCurrentPositions").getJSONArray("values"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}
	
	public static ArrayList<LinkedInUser> fromJson(JSONArray jsonArray) {
		ArrayList<LinkedInUser> results = new ArrayList<LinkedInUser>(jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject userJson = null;
			try {
				userJson = jsonArray.getJSONObject(i);
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			LinkedInUser result = LinkedInUser.fromJson(userJson);
			if (result != null) {
				results.add(result);
			}
		}
		return results;
	}
}

package com.codepath.mentormatch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class LinkedInPosition {

	private String positionId;
	private boolean isCurrent;
	private String title;
	private String company;
		
	public String toString() {
		return String.format("Position id: %s, title: %s, isCurrent: %b, company: %s", positionId, title, isCurrent, company);
	}

	public static LinkedInPosition fromJson(JSONObject jsonObject) {
		LinkedInPosition p = new LinkedInPosition();
		
		try {			
			p.positionId = jsonObject.getString("id");
			p.isCurrent = jsonObject.getBoolean("isCurrent");
			p.title = jsonObject.getString("title");
			p.company = jsonObject.getJSONObject("company").getString("name"); 
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return p;
	}
	
	public static ArrayList<LinkedInPosition> fromJson(JSONArray jsonArray) {
		ArrayList<LinkedInPosition> results = new ArrayList<LinkedInPosition>(jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject userJson = null;
			try {
				userJson = jsonArray.getJSONObject(i);
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			LinkedInPosition result = LinkedInPosition.fromJson(userJson);
			if (result != null) {
				results.add(result);
			}
		}
		return results;
	}
}

package com.codepath.mentormatch.models.linkedin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class LinkedInPosition {

	public String getTitle() {
		return title;
	}

	public String getCompany() {
		return company;
	}

	private String positionId;
	private String title;
	private String company;
		
	public String toString() {
		return String.format("Position id: %s, title: %s, company: %s", positionId, title, company);
	}

	public static LinkedInPosition fromJson(JSONObject jsonObject) {
		LinkedInPosition p = new LinkedInPosition();
		
		try {			
			p.positionId = jsonObject.getString("id");
			p.title = jsonObject.optString("title", "");
			p.company = "";
			JSONObject company = jsonObject.getJSONObject("company");
			if (company != null) {
				p.company = company.optString("name", "");
			}
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

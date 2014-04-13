package com.codepath.mentormatch.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class LinkedInSkill {

	private Long skillId;
	private String skillName;
	
	@Override
	public String toString() {
		return String.format("Skill id: %s, name: %s", skillId, skillName);
	}
		
	public static LinkedInSkill fromJson(JSONObject jsonObject) {
		LinkedInSkill s = new LinkedInSkill();
		
		try {			
			s.skillId = jsonObject.getLong("id");
			s.skillName = jsonObject.getJSONObject("skill").getString("name");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return s;
	}
	
	public static ArrayList<LinkedInSkill> fromJson(JSONArray jsonArray) {
		ArrayList<LinkedInSkill> results = new ArrayList<LinkedInSkill>(jsonArray.length());
		
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject userJson = null;
			try {
				userJson = jsonArray.getJSONObject(i);
			} catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			LinkedInSkill result = LinkedInSkill.fromJson(userJson);
			if (result != null) {
				results.add(result);
			}
		}
		return results;
	}
}

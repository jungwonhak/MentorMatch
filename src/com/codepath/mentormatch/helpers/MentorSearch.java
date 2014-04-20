package com.codepath.mentormatch.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import com.codepath.mentormatch.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MentorSearch {

	
	List<ParseUser> mentorList = new ArrayList<ParseUser>();
	public void findMentorsBasedOnSkill(String skill) {
		String[] skillsList = { skill };

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		// query.whereEqualTo(User.USER_NAME_KEY, "TestUser");
		query.whereContainedIn(User.SKILLS_LIST_KEY, Arrays.asList(skillsList));
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					// The query was successful.
					Log.d("TEST", "userList size: " + objects.size());
					if (objects.size() > 0) {
						User aUser = (User) objects.get(0);
						Log.d("TEST", "skills: " + aUser.getSkills().size());
					}
					mentorList = objects;
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});
	}

	public List<ParseUser> getUsersList() {
		return mentorList;
	}
	
	public void getRatingAsMentee(User user) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo("playerName", "Dan Stemkoski");
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> scoreList, ParseException e) {
		        if (e == null) {
		            Log.d("score", "Retrieved " + scoreList.size() + " scores");
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});
		

	}
}

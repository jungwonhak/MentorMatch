package com.codepath.mentormatch.Fragments;

import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MentorMatchResultsFragment extends MatchResultsListFragment {

	//private static final String SKILL_ARG = "skill";
	//private static final String REQUEST_ID_ARG = "requestId";

	public static MentorMatchResultsFragment newInstance(Skill skill, String requestId) {
		MentorMatchResultsFragment fragment = new MentorMatchResultsFragment();
		//Bundle args = new Bundle();
		//args.putSerializable(SKILL_ARG, skill);
		//args.putString(REQUEST_ID_ARG, requestId);
		//fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//requestId = getArguments().getString(REQUEST_ID_ARG);
	}

	public MentorMatchResultsFragment() {
		super();
	}

	private void getProfilesBySkill(String skill) {
		String[] skillsList = { skill.toString() };

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereContainedIn(User.SKILLS_LIST_KEY, Arrays.asList(skillsList));
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					Log.d("TEST", "userList size: " + objects.size());
					if (objects.size() > 0) {
						User aUser = (User) objects.get(0);
						Log.d("TEST", "skills: " + aUser.getSkills().size());
					}
					profileAdapter.addAll(objects);
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});		
	}

	// Get the most recently created mentor request to find skill and then
	// find all mentors that have that skill.  Look at consolidating this
	// into a single parse query later
	@Override
	public void fetchProfiles() {
		ParseUser user = ParseUser.getCurrentUser();
		
		ParseQuery<ParseObject> q = ParseQuery.getQuery("MentorRequest");
		q.whereEqualTo(MentorRequest.MENTEE_USER_ID_KEY, user);
		q.addDescendingOrder("createdAt");
		q.getFirstInBackground(new GetCallback<ParseObject>() {
			@Override  
			public void done(ParseObject object, ParseException e) {
			    if (object == null) {
			    	Log.d("DEBUG", "Could not find mentor request in backend");
			    } else {
			    	MentorRequest mr = (MentorRequest)object;
			    	Log.d("DEBUG", "Mentor Request - Created at:" + mr.getCreatedAt() + " Object Id: " + mr.getObjectId() + " Skill: " + mr.getSkill());
			    	getProfilesBySkill(mr.getSkill());	
			    }
			  }
		});				
		//Skill skill = (Skill)getArguments().getSerializable(SKILL_ARG);
	}
}

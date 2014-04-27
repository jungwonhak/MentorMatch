package com.codepath.mentormatch.fragments;

import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MenteeMatchResultsFragment extends MatchResultsListFragment {
	// Results that a mentee should see - This retrieves the potential mentors
	
	//private MentorRequest request;
	
	public MenteeMatchResultsFragment() {
		super();
	}

	public static MenteeMatchResultsFragment newInstance(Skill skill, String requestId) {
		MenteeMatchResultsFragment fragment = new MenteeMatchResultsFragment();
		Bundle args = new Bundle();
//		args.putSerializable(SKILL_ARG, skill);
//		args.putString(REQUEST_ID_ARG, requestId);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestId = getArguments().getString(REQUEST_ID_ARG);
	}

	private void getProfilesBySkill(String skill) {
		ParseQueries.findMentorsWithSkill(skill, new FindMentorsWithSkillCallback());
	}

	// Get the most recently created mentor request to find skill and then
	// find all mentors that have that skill.  Look at consolidating this
	// into a single parse query later
	@Override
	public void fetchProfiles() {
		ParseUser user = ParseUser.getCurrentUser();
		
		ParseQuery<MentorRequest> q = ParseQuery.getQuery("MentorRequest");
		q.whereEqualTo(MentorRequest.MENTEE_USER_ID_KEY, user);
		q.addDescendingOrder("createdAt");
		q.getFirstInBackground(new GetCallback<MentorRequest>() {
			@Override  
			public void done(MentorRequest object, ParseException e) {
			    if (object == null) {
			    	Log.d("DEBUG", "Could not find mentor request in backend");
			    } else {
			    	request = (MentorRequest)object;
			    	Log.d("DEBUG", "Mentor Request - Created at:" + request.getCreatedAt() + " Object Id: " + request.getObjectId() + " Skill: " + request.getSkill());
			    	getProfilesBySkill(request.getSkill());	
			    }
			  }
		});				
	}

	// Callback for Query Results
	private class FindMentorsWithSkillCallback extends FindCallback<ParseUser> {
		@Override
		public void done(List<ParseUser> mentorsList, ParseException e) {
	        if (e == null) {
	            Log.d("DEBUG", "FindMentorsWithSkillCallback: " + mentorsList.size());
				profileAdapter.addAll(mentorsList);
	        } else {
	            Log.d("DEBUG", "Error: " + e.getMessage());
	        }
		}
	}

}

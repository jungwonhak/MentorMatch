package com.codepath.mentormatch.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ProfileDetailActivity;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class MenteeMatchResultsFragment extends MatchResultsListFragment {
	// Results that a mentee should see - This retrieves the potential mentors
	
	private MentorRequest request;
	//protected MentorRequest request;
	private User rejectedUser;
	
	public MenteeMatchResultsFragment() {
		super();
	}

	public static MenteeMatchResultsFragment newInstance(Skill skill, String requestId) {
		MenteeMatchResultsFragment fragment = new MenteeMatchResultsFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void getProfilesBySkill(String skill) {
		ParseQueries.findMentorsWithSkill(skill, request, new FindMentorsWithSkillCallback());
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
	

	protected void handleItemClick(int pos) {
		Log.d("TEST - Item Click Listener", "on item click: " + pos);
		Intent i = new Intent(getActivity(), ProfileDetailActivity.class);
		i.putExtra(USER_EXTRA, ((User) profileAdapter.getItem(pos)).getObjectId());
		i.putExtra(REQUEST_ID_EXTRA, request.getObjectId());
		startActivityForResult(i, ProfileDetailActivity.PROFILE_DETAIL_REQUEST_CODE);		
	}
	/*
	public void setListViewListeners() {
		lvProfileSummaries.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Log.d("TEST - Item Click Listener", "on item click: " + pos);
				Intent i = new Intent(getActivity(), ProfileDetailActivity.class);
				i.putExtra(USER_EXTRA, ((User) profileAdapter.getItem(pos)).getObjectId());
				i.putExtra(REQUEST_ID_EXTRA, request.getObjectId());
				startActivityForResult(i, ProfileDetailActivity.PROFILE_DETAIL_REQUEST_CODE);
			}
		});
	}
	*/
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ProfileDetailActivity.PROFILE_DETAIL_REQUEST_CODE) {
			profileAdapter.clear();
			getProfilesBySkill(request.getSkill());	
		}
	}

	// Callback for Query Results
	private class FindMentorsWithSkillCallback extends FindCallback<ParseUser> {
		@Override
		public void done(List<ParseUser> mentorsList, ParseException e) {

	        if (e == null) {
	            Log.d("DEBUG", "FindMentorsWithSkillCallback: " + mentorsList.size());				
				profileAdapter.addAll(mentorsList);
				pbLoading.setVisibility(ProgressBar.INVISIBLE);
				if(profileAdapter.isEmpty()) {
					tvEmptyList.setText(getActivity().getResources().getString(R.string.match_results_empty_list));
					tvEmptyList.setVisibility(View.VISIBLE);
				}
	        } else {
	            Log.d("DEBUG", "Error: " + e.getMessage());
	        }
		}
	}

	@Override
	protected void handleDeleteItem(User user) {
		rejectedUser = user;
		User currentUser = (User)ParseUser.getCurrentUser();
		ParseQueries.getMostRecentMentorRequestFromUser(currentUser, new GetCallbackClass());
		
	}

	private class GetCallbackClass extends GetCallback<MentorRequest> {
		@Override
		public void done(MentorRequest mr, ParseException e) {
	        if (e != null || mr == null) {
	        	e.printStackTrace();
	        } else {
		    	Log.d("DEBUG", "Mentor Request - Created at:" + mr.getCreatedAt() + " Object Id: " + mr.getObjectId() + " Skill: " + mr.getSkill());
		    	mr.addRejectedMentorToList(rejectedUser.getObjectId());
		    	mr.saveInBackground();
	        }
		}
		
	}

}

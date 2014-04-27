package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.mentormatch.activities.ProfileDetailActivity;
import com.codepath.mentormatch.adapters.MatchResultsAdapter;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MentorMatchResultsFragment extends MatchResultsListFragment {
	// Results that a mentor should see - This retrieves the mentees that have requested this person as a mentor
	
	//private static final String SKILL_ARG = "skill";
	//private static final String REQUEST_ID_ARG = "requestId";

	public static MentorMatchResultsFragment newInstance(Skill skill, String requestId) {
		MentorMatchResultsFragment fragment = new MentorMatchResultsFragment();

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		usersList = new ArrayList();
//		profileAdapter = new MatchResultsAdapter(getActivity(), usersList);
		super.onCreate(savedInstanceState);
		//requestId = getArguments().getString(REQUEST_ID_ARG);
	}

	public MentorMatchResultsFragment() {
		super();
	}

	// Get the most recently created mentor request to find skill and then
	// find all mentors that have that skill.  Look at consolidating this
	// into a single parse query later
	@Override
	public void fetchProfiles() {
		ParseUser user = ParseUser.getCurrentUser();		
		ParseQueries.getRequestsForMentor(user.getObjectId(), new FindRequestsForMentorCallback());
	}
	
	private class FindRequestsForMentorCallback extends FindCallback<MentorRequest> {
		@Override
		public void done(List<MentorRequest> requestList, ParseException e) {
			if(e == null) {
				profileAdapter.addAll(requestList);
				/*
				for(MentorRequest aReq : requestList) {
					profileAdapter.add(aReq);
				}*/
			} else {
				Log.d("DEBUG", "Error - trying to find requests for mentor");
				e.printStackTrace();
			}
		}
	}

	@Override
	public void setListViewListeners() {
		lvProfileSummaries.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Log.d("TEST - Item Click Listener", "on item click: " + pos);
				MentorRequest request = (MentorRequest) profileAdapter.getItem(pos);
				Intent i = new Intent(getActivity(), ProfileDetailActivity.class);
				i.putExtra(USER_EXTRA, request.getMentee().getObjectId());
				i.putExtra(REQUEST_ID_EXTRA, request.getObjectId());
				startActivity(i);
			}
		});		
	}
}

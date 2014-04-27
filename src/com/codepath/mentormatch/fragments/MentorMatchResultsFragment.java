package com.codepath.mentormatch.fragments;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.mentormatch.activities.MatchResultsActivity;
import com.codepath.mentormatch.activities.ProfileDetailActivity;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MentorMatchResultsFragment extends MatchResultsListFragment {
	// Results that a mentor should see - This retrieves the mentees that have requested this person as a mentor
	
	private boolean firstUse;

	public static MentorMatchResultsFragment newInstance(Skill skill, String requestId, boolean isFirstUse) {
		MentorMatchResultsFragment fragment = new MentorMatchResultsFragment();
		Bundle args = new Bundle();
		args.putBoolean(MatchResultsActivity.FIRST_USE_EXTRA, isFirstUse);
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
//		usersList = new ArrayList();
//		profileAdapter = new MatchResultsAdapter(getActivity(), usersList);
		//requestId = getArguments().getString(REQUEST_ID_ARG);
		firstUse = getArguments().getBoolean(MatchResultsActivity.FIRST_USE_EXTRA);
		super.onCreate(savedInstanceState);
		if (firstUse) {
			showFirstUseDialog();
		}
		

	}

	public MentorMatchResultsFragment() {
		super();
	}

	private void showFirstUseDialog() {
		User user = (User)ParseUser.getCurrentUser();		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		
		String[] tokens = user.getFullName().split(" ", 2);
		String firstName = "Hi";
		if (tokens.length > 0) {
			firstName = tokens[0];
		}
		Log.d("DEBUG", "First name is: " + firstName);
		MentorSignupSuccessDialog signupSuccessDialog = MentorSignupSuccessDialog.newInstance(firstName);
		signupSuccessDialog.show(fm, "fragment_dialog_mentor_signup_success");
	}
	// Get the most recently created mentor request to find skill and then
	// find all mentors that have that skill.  Look at consolidating this
	// into a single parse query later
	@Override
	public void fetchProfiles() {
		Log.d("DEBUG", "Trying to fetch profiles");
		User user = (User)ParseUser.getCurrentUser();		
		
		if (firstUse) {
			Log.d("DEBUG", "First use");
			ParseQueries.getMenteesLookingForMySkills(user.getSkills(), new FindRequestsForMentorCallback());
		} else {
			Log.d("DEBUG", "Not first use");

			ParseQueries.getRequestsForMentor(user.getObjectId(), new FindRequestsForMentorCallback());
		}
	}
	
	private class FindRequestsForMentorCallback extends FindCallback<MentorRequest> {
		@Override
		public void done(List<MentorRequest> requestList, ParseException e) {
			if(e == null) {
				profileAdapter.addAll(requestList);

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

package com.codepath.mentormatch.activities;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.fragments.ProfileSummaryListFragment;
import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ViewProfileActivity extends FragmentActivity {

	private String userObjId;
	private String requestId;
	private User user;
	private TextView tvName;
	private TextView tvJobInfo;
	private TextView tvLocation;
	private Button btnContact;
	private RatingBar rbRating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);
		userObjId = getIntent().getStringExtra(ProfileSummaryListFragment.USER_EXTRA);
		requestId = getIntent().getStringExtra(ProfileSummaryListFragment.REQUEST_ID_EXTRA);
		btnContact = (Button) findViewById(R.id.btnRequestMentor);
		if (requestId == null) {
			// hide connection button
			btnContact.setVisibility(View.GONE);
		}
		getUser();
	}

	private void getUser() {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(userObjId, new GetCallback<ParseUser>() {
			public void done(ParseUser object, ParseException e) {
				if (e == null) {
					Log.d("DEBUG", "found user");
					user = (User) object;
					setViews();
				} else {
					// something went wrong
					Log.d("DEBUG",
							"ERROR - PROFILE ACTIVITY - COULD NOT FIND USER");
					e.printStackTrace();
				}
			}
		});
	}

	private void setViews() {
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(user.getFullName());

		tvJobInfo = (TextView) findViewById(R.id.tvJobInfo);
		tvJobInfo.setText(user.getJobTitle() + " at " + user.getCompany());

		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvLocation.setText(user.getLocation());
		
		rbRating = (RatingBar) findViewById(R.id.rbRating);
		retrieveReviews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_profile, menu);
		return true;
	}

	public void createMentorRequest(View view) {
		Log.d("DEBUG", "Requesting Mentor");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MentorRequest");
		query.getInBackground(requestId, new GetCallback<ParseObject>() {
			public void done(ParseObject object, ParseException e) {
				if (e == null) {
					((MentorRequest) object).addMentorToList(userObjId);
					object.saveInBackground();
					Log.d("DEBUG", "ADDING MENTOR TO REQUEST: " + requestId);
				} else {
					Log.d("DEBUG", "ERROR IN ADDING MENTOR TO REQUEST: "
							+ requestId);
					e.printStackTrace();
				}
			}
		});

	}

	private void retrieveReviews() {
		ParseQuery<MatchRelationship> mentorRating = ParseQuery
				.getQuery("MatchRelationship");
		mentorRating.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY,
				ParseUser.getCurrentUser());

		ParseQuery<MatchRelationship> menteeRating = ParseQuery
				.getQuery("MatchRelationship");
		menteeRating.whereEqualTo(MatchRelationship.MENTEE_USER_ID_KEY,
				ParseUser.getCurrentUser());
		List<ParseQuery<MatchRelationship>> queries = new ArrayList<ParseQuery<MatchRelationship>>();
		queries.add(mentorRating);
		queries.add(menteeRating);

		ParseQuery<MatchRelationship> mainQuery = ParseQuery.or(queries);
		mainQuery.include(MatchRelationship.MENTOR_USER_ID_KEY);
		mainQuery.include(MatchRelationship.MENTEE_USER_ID_KEY);
		mainQuery.findInBackground(new FindCallback<MatchRelationship>() {
			public void done(List<MatchRelationship> results, ParseException e) {
				double totalRating = 0.0;
				// int count;
				for (MatchRelationship relation : results) {
					if (relation.getMentee().equals(ParseUser.getCurrentUser())) {
						totalRating += relation.getMenteeRating();
					} else {
						totalRating += relation.getMentorRating();
					}
				}
				double avgRating = totalRating / results.size();
				rbRating.setRating((float)avgRating);
			}
		});
	}

}

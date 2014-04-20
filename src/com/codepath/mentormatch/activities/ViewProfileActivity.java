package com.codepath.mentormatch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.fragments.ProfileSummaryListFragment;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.User;
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
	private Button btnContact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);
		userObjId = getIntent().getStringExtra(
				ProfileSummaryListFragment.USER_EXTRA);
		requestId = getIntent().getStringExtra(
				ProfileSummaryListFragment.REQUEST_ID_EXTRA);
		btnContact = (Button) findViewById(R.id.btnRequestMentor);
		if(requestId == null) {
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
		tvName.setText(user.getUsername());

		tvJobInfo = (TextView) findViewById(R.id.tvJobInfo);
		tvJobInfo.setText(user.getJobTitle() + " at " + user.getCompany());
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

}

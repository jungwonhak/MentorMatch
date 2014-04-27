package com.codepath.mentormatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.core.MentorMatchApplication;
import com.codepath.mentormatch.fragments.DetailsFragment;
import com.codepath.mentormatch.fragments.MatchResultsListFragment;
import com.codepath.mentormatch.fragments.MenteeMatchResultsFragment;
import com.codepath.mentormatch.fragments.MentorMatchResultsFragment;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.User;
import com.parse.ParseUser;

public class MatchResultsActivity extends FragmentActivity {

	public static final String FIRST_USE_EXTRA = "first_use";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_results);
		Intent intent = getIntent();
		Skill skill = (Skill) intent.getSerializableExtra(DetailsFragment.SKILL_EXTRA);
		String requestId = intent.getStringExtra(DetailsFragment.REQUEST_EXTRA);
		boolean isFirstUse = intent.getBooleanExtra(FIRST_USE_EXTRA, false);
		Log.d("DEBUG", "In MatchResultsActivity, first use is: " + isFirstUse);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if(((User) ParseUser.getCurrentUser()).isMentor()) {
			Log.d("DEBUG", "Replacing with mentor fragment");
			ft.replace(R.id.flMentorSearchResults, MentorMatchResultsFragment.newInstance(skill, requestId, isFirstUse));
		} else {
			ft.replace(R.id.flMentorSearchResults, MenteeMatchResultsFragment.newInstance(skill, requestId));
		}
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void handleLogout(MenuItem item){
		MentorMatchApplication.logoutUser();
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
	}

	public void viewConnections(MenuItem item) {
		Intent i = new Intent(this, ConnectionsActivity.class);
		startActivity(i);
	}
	
	public void viewRequests(MenuItem item) {
		//Intent i = new Intent(this, PendingRequestActivity.class);
		Intent i = new Intent(this, ProfileBuilderActivity.class);
		i.putExtra("foo", "details");
		//i.putExtra(ProfileSummaryListFragment.USER_EXTRA, ParseUser.getCurrentUser().getObjectId());
		startActivity(i);
	}
	
	public void viewMyProfile(MenuItem item) {
		Intent i = new Intent(this, ProfileDetailActivity.class);
		i.putExtra(MatchResultsListFragment.USER_EXTRA, ParseUser.getCurrentUser().getObjectId());
		startActivity(i);
	}
}

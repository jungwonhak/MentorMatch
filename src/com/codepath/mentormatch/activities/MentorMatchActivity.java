package com.codepath.mentormatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.core.ParseApplication;
import com.codepath.mentormatch.fragments.AboutMeFragment;
import com.codepath.mentormatch.fragments.MentorSearchResultsFragment;
import com.codepath.mentormatch.fragments.ProfileSummaryListFragment;
import com.codepath.mentormatch.models.Skill;
import com.parse.ParseUser;

public class MentorMatchActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mentor_match);
		Intent intent = getIntent();
		Skill skill = (Skill) intent.getSerializableExtra(AboutMeFragment.SKILL_EXTRA);
		String requestId = intent.getStringExtra(AboutMeFragment.REQUEST_EXTRA);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flMentorSearchResults, MentorSearchResultsFragment.newInstance(skill, requestId));
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mentor_match, menu);
		return true;
	}

	public void handleLogout(MenuItem item){
		ParseApplication.logoutUser();
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
		Intent i = new Intent(this, ViewProfileActivity.class);
		i.putExtra(ProfileSummaryListFragment.USER_EXTRA, ParseUser.getCurrentUser().getObjectId());
		startActivity(i);
	}
}

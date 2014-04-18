package com.codepath.mentormatch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.fragments.AboutMeFragment;
import com.codepath.mentormatch.fragments.LanguageSelectionFragment;
import com.codepath.mentormatch.fragments.MentorStatusFragment;
import com.codepath.mentormatch.fragments.ProfileDetailsFragment;

public class ProfileBuilderActivity extends FragmentActivity {
	private String name;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.builder_profile);
		
		name = getIntent().getStringExtra("foo").toString();
		
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (name.equals("login")) {
			fts.replace(R.id.profileItemContainer, new MentorStatusFragment());
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();
		} 
		
		else if(name.equals("mentor")) {
			fts.replace(R.id.profileItemContainer, new ProfileDetailsFragment());
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();	
		}
		else if (name.equals("details")) {
			fts.replace(R.id.profileItemContainer, new LanguageSelectionFragment());
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();
		} 
		else if (name.equals("language")) {
			fts.replace(R.id.profileItemContainer, new AboutMeFragment());
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();		
		} 
		
		fts.commit();
	}
	
}

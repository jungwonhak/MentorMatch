package com.example.mentormatch;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.mentormatch.Fragments.AboutMeFragment;
import com.example.mentormatch.Fragments.LanguageSelectionFragment;
import com.example.mentormatch.Fragments.MentorStatusFragment;
import com.example.mentormatch.Fragments.ProfileDetailsFragment;

public class Profile_Builder extends FragmentActivity {
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

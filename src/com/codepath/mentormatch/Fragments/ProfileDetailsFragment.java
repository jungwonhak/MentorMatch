package com.codepath.mentormatch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.R.drawable;
import com.codepath.mentormatch.activities.ProfileBuilderActivity;
import com.codepath.mentormatch.models.parse.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseUser;

public class ProfileDetailsFragment extends Fragment{
	private Button profileNext;
	private TextView tvProfileDetails;
	private EditText etProfileName;
	private EditText etProfileJob;
	private EditText etProfileCompany;
	private EditText etLocation; 
	private ImageView ivProfileImage;
	private int drawableID;
	
	public static final String PROFILE_PAGE_EXTRA = "foo";
	public static final String NAME_EXTRA = "profile_name";
	public static final String JOB_EXTRA = "job";
	public static final String LOCATION_EXTRA = "location";
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		View v = inf.inflate(R.layout.fragment_profile_details, parent,  false);
		tvProfileDetails = (TextView) v.findViewById(R.id.tvProfileDetails);
		ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		etProfileName = (EditText) v.findViewById(R.id.etProfileName);
		etProfileJob = (EditText) v.findViewById(R.id.etProfileJob);
		etProfileCompany = (EditText) v.findViewById(R.id.etProfileCompany);
		etLocation = (EditText) v.findViewById(R.id.etLocation);
		profileNext = (Button) v.findViewById(R.id.btnProfileNext);
		drawableID = (int) R.drawable.ic_profile_placeholder;
		
		User currentUser = (User) ParseUser.getCurrentUser();
		if (currentUser != null) {
			ImageLoader.getInstance().displayImage(currentUser.getProfileImage(), ivProfileImage);
			
			if (currentUser.getProfileImage() == null) {
				ivProfileImage.setImageResource(drawableID);
			}
			etLocation.setText(currentUser.getLocation());
			etProfileName.setText(currentUser.getFullName());
			etProfileJob.setText(currentUser.getJobTitle());
			etProfileCompany.setText(currentUser.getCompany());
			if (currentUser.isMentor()) {
				tvProfileDetails.setText(R.string.mentor_profile_details_title);
			}
		} else {
			Log.d("DEBUG", "No current parse user");
		}
		addListenerOnButton();


		return v;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void addListenerOnButton() {
	    profileNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				updateBackend();
				Intent i = new Intent(getActivity(), ProfileBuilderActivity.class);
				i.putExtra(PROFILE_PAGE_EXTRA, "details");
				startActivity(i);
				
			}
		}); 
	  }  
	public void updateBackend() {
		User u = (User)ParseUser.getCurrentUser();
		u.setLocation(etLocation.getText().toString());
		u.setFullName(etProfileName.getText().toString());
		u.setJobTitle(etProfileJob.getText().toString());
		u.setCompany(etProfileCompany.getText().toString());		
		u.saveInBackground();
	}
}


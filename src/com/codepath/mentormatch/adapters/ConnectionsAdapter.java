package com.codepath.mentormatch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.User;
import com.parse.ParseUser;

public class ConnectionsAdapter extends ArrayAdapter<MatchRelationship>{

	private MatchRelationship matchRelationship;
	private User mentee;
	TextView tvName;
	TextView tvJobTitle;
	TextView tvLocation;
	TextView tvMessage;
	TextView tvBlurb;
	ImageView ivProfileImage;
	RatingBar rbRating;
	ImageView ivSkill;
	
	public ConnectionsAdapter(Context context, List<MatchRelationship> mentorRequests) {
		super(context, 0, mentorRequests);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		matchRelationship = (MatchRelationship) getItem(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.connection_item, null);	
		}

		tvName = (TextView) convertView.findViewById(R.id.tvName);
		tvJobTitle = (TextView) convertView.findViewById(R.id.tvJobTitle);
		tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
		ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		ivSkill = (ImageView) convertView.findViewById(R.id.ivSkill);
		tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
		tvBlurb = (TextView) convertView.findViewById(R.id.tvBlurb);
		User currentUser = (User) ParseUser.getCurrentUser();
		String name = "", jobTitle = "", location = "", description = "", blurb = "";
		if(currentUser.isMentor()) {
			mentee = (User) matchRelationship.getMentee();
			name = mentee.getFullName();
			jobTitle = mentee.getJobTitle() + " @ " + mentee.getCompany();
			location = mentee.getLocation();
			description = matchRelationship.getMentorRequestId().getDescription();
			blurb = "needs your help with ";
		} else {
			User mentor = (User) matchRelationship.getMentor();
			name = mentor.getFullName();
			jobTitle = mentor.getJobTitle() + " @ " + mentor.getCompany();
			location = mentor.getLocation();
			description = mentor.getDescription();
			blurb = " is helping you with ";
		}

		
		
		tvName.setText(name);
		tvJobTitle.setText(jobTitle);
		tvLocation.setText(location);
		tvMessage.setText(description);
		tvBlurb.setText(blurb);
//		ivSkill.setImageResource(Skill.fromValue(req.getSkill()).getResourceId());
		return convertView;
	}
	
}

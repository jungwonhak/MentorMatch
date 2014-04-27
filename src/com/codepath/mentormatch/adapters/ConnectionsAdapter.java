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
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;

public class ConnectionsAdapter extends ArrayAdapter<MatchRelationship>{

	private MatchRelationship matchRelationship;
	private User mentee;
	TextView tvName;
	TextView tvJobTitle;
	TextView tvLocation;
	TextView tvMessage;
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
		
		mentee = (User) matchRelationship.getMentee();

		
		tvName.setText(mentee.getFullName());
		tvJobTitle.setText(mentee.getJobTitle() + " @ " + mentee.getCompany());
		tvLocation.setText(mentee.getLocation());
		MentorRequest req = matchRelationship.getMentorRequestId();
		tvMessage.setText(req.getDescription());
		ivSkill.setImageResource(Skill.fromValue(req.getSkill()).getResourceId());
		return convertView;
	}
	
}

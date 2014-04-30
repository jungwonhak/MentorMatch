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
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseUser;

public class ConnectionsAdapter extends ArrayAdapter<MatchRelationship>{

	private MatchRelationship matchRelationship;
	private int drawableID;
	private TextView tvName;
	private TextView tvJobTitle;
	private TextView tvLocation;
	private TextView tvMessage;
	private TextView tvBlurb;
	private ImageView ivProfileImage;
	private RatingBar rbRating;
	private ImageView ivSkill;

	
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
		drawableID = (int) R.drawable.ic_profile_placeholder;
		
		User currentUser = (User) ParseUser.getCurrentUser();
		String name = "", jobTitle = "", location = "", description = "", blurb = "", image= "";
		MentorRequest mentorRequest = matchRelationship.getMentorRequestId();
		if(currentUser.isMentor()) {
			User mentee = (User) matchRelationship.getMentee();
			name = mentee.getFullName();
			jobTitle = mentee.getJobTitle() + " @ " + mentee.getCompany();
			location = mentee.getLocation();
			image = mentee.getProfileImage();
			description = mentorRequest.getDescription();
			blurb = getContext().getResources().getString(R.string.connections_mentee_text);
		} else {
			User mentor = (User) matchRelationship.getMentor();
			name = mentor.getFullName();
			jobTitle = mentor.getJobTitle() + " @ " + mentor.getCompany();
			location = mentor.getLocation();
			image = mentor.getProfileImage();
			description = mentor.getDescription();
			blurb = getContext().getResources().getString(R.string.connections_mentor_text);
		}
		Skill skill = Skill.fromValue(mentorRequest.getSkill());
		tvName.setText(name);
		tvJobTitle.setText(jobTitle);
		tvLocation.setText(location);
		tvMessage.setText(description);
		tvBlurb.setText(skill.toString() + blurb);
		if (image != null && !image.isEmpty()) {
			ImageLoader.getInstance().displayImage(image,
					ivProfileImage);

		} else {
			ivProfileImage.setImageResource(drawableID);
		}
		
		ivSkill.setImageResource(skill.getResourceId());
		return convertView;
	}
	
}

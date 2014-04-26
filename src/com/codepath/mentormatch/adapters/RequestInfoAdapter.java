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
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class RequestInfoAdapter extends ArrayAdapter<MentorRequest>{

	private MentorRequest mentorRequest;
	private User mentee;
	TextView tvName;
	TextView tvJobTitle;
	TextView tvLocation;
	TextView tvMessage;
	ImageView ivProfileImage;
	RatingBar rbRating;
	ImageView ivSkill;
	
	public RequestInfoAdapter(Context context, List<MentorRequest> mentorRequests) {
		super(context, 0, mentorRequests);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mentorRequest = (MentorRequest) getItem(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.mentor_request_item, null);	
		}

		tvName = (TextView) convertView.findViewById(R.id.tvName);
		tvJobTitle = (TextView) convertView.findViewById(R.id.tvJobTitle);
		tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
		ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		ivSkill = (ImageView) convertView.findViewById(R.id.ivSkill);
		tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
		
		mentee = (User) mentorRequest.getMentee();

		
		tvName.setText(mentee.getFullName());
		tvJobTitle.setText(mentee.getJobTitle() + " @ " + mentee.getCompany());
		tvLocation.setText(mentee.getLocation());
		tvMessage.setText(mentorRequest.getDescription());
		ivSkill.setImageResource(Skill.fromValue(mentorRequest.getSkill()).getResourceId());
		return convertView;
	}
	
}

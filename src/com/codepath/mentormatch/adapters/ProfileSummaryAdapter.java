package com.codepath.mentormatch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.User;
import com.parse.ParseUser;

public class ProfileSummaryAdapter extends ArrayAdapter<ParseUser> {

	private User user;
	TextView tvName;
	TextView tvJobTitle;
	
	public ProfileSummaryAdapter(Context context, List<ParseUser> objects) {
		super(context, 0, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if(view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.profile_summary_item, null);
		}
		
		tvName = (TextView) view.findViewById(R.id.tvName);
		tvJobTitle = (TextView) view.findViewById(R.id.tvJobTitle);
		user = (User) getItem(position);
		tvName.setText(user.getUsername());
		tvJobTitle.setText(user.getJobTitle() + " at " + user.getCompany());
		
		return view;
	}
	
	private void getRatings() {
		
	}

}

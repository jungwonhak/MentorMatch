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
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.MatchResultsActivity;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class DetailsFragment extends Fragment{
	private Button btnDetailsNext;
	private EditText etDescription;
	private TextView tvAboutMe;
	public static final String ABOUT_ME_PAGE_EXTRA = "foo";
	public static final String TEXT_EXTRA = "about";
	public static final String SKILL_EXTRA = "skill";
	public static final String REQUEST_EXTRA = "requestObjectId";
	public static final String SKILL_ARG = "skill";
	private User user;
	
	/*
	public static DetailsFragment newInstance(Skill aSkill) {
		DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(SKILL_ARG, aSkill);
        fragment.setArguments(args);
        return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		skill = (Skill) args.getSerializable(SKILL_ARG);
	}
*/
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		
		View v = inf.inflate(R.layout.fragment_details, parent,  false);
	    btnDetailsNext = (Button) v.findViewById(R.id.btnDetailsNext);
	    tvAboutMe = (TextView) v.findViewById(R.id.tvAboutMe);
	    etDescription = (EditText) v.findViewById(R.id.etAboutMe);
		addListenerOnButton();

		user = (User)ParseUser.getCurrentUser();
		if (user.isMentor()) {
			tvAboutMe.setText(R.string.details_mentor_header);
			btnDetailsNext.setText(R.string.details_mentor_button);
		}
		
		return v;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void addListenerOnButton() {
	    btnDetailsNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateBackend();
			}
		}); 
	}
	private void goToNextPage() {
		Intent i = new Intent(getActivity(), MatchResultsActivity.class);
		i.putExtra(MatchResultsActivity.FIRST_USE_EXTRA, true);
		startActivity(i);		
	}
	
	public void updateBackend() {
		final String description = etDescription.getText().toString();
		if (user.isMentor()) {
			Log.d("DEBUG", "Detected user is a mentor");
			// Update description on server
			user.setDescription(description);
			user.saveInBackground(new SaveCallback() {
				@Override
				public void done(ParseException e) {
					if (e == null) {
						goToNextPage();
					} else {
						e.printStackTrace();
					}
				}
			});	
		} else {
			Log.d("DEBUG", "Detected user is a mentee");
			// Get latest mentee request from server
			ParseQueries.getMostRecentMentorRequestFromUser(user, new GetCallbackClass());			
		}
	}
	
	private class GetCallbackClass extends GetCallback<MentorRequest> {
		@Override
		public void done(MentorRequest mr, ParseException e) {
	        if (e != null || mr == null) {
	        	e.printStackTrace();
	        } else {
		    	Log.d("DEBUG", "Mentor Request - Created at:" + mr.getCreatedAt() + " Object Id: " + mr.getObjectId() + " Skill: " + mr.getSkill());
		    	mr.setDescription(etDescription.getText().toString());
		    	mr.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
				    	if (e == null) {
				    		goToNextPage();	
				    	} else {
				    		Log.d("DEBUG", "Unable to save mentor request with description");
				    	}
				    	
					}
				});
	        	
	        }
		}
		
	}

}

package com.codepath.mentormatch.fragments;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ConnectionDetailActivity;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class RatingsFragment extends Fragment {
	
	private MatchRelationship relationship;
	private User mentee;
	private User mentor;
	
	private Button btnSave;
	private EditText etReview;
	private RatingBar rbRating;
	private TextView tvName;

	
	public static RatingsFragment newInstance() {
		RatingsFragment fragment = new RatingsFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ratings, parent, false);
		etReview = (EditText) view.findViewById(R.id.etReview);
		btnSave = (Button) view.findViewById(R.id.btnSave);
		rbRating = (RatingBar) view.findViewById(R.id.rbRating);
		tvName = (TextView) view.findViewById(R.id.tvName);
		btnSave.setEnabled(false);
		setOnClickListener();

		return view;
	}

	private void setOnClickListener() {
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(((User)ParseUser.getCurrentUser()).isMentor()) {
					relationship.setMenteeRating(rbRating.getRating());
					relationship.setCommentForMentee(etReview.getText().toString());

				} else {
					relationship.setMentorRating(rbRating.getRating());
					relationship.setCommentForMentor(etReview.getText().toString());

				}
				relationship.saveInBackground();	
				Toast.makeText(getActivity(), "Review Saved", Toast.LENGTH_SHORT).show();
				Activity activity = getActivity();
				ActionBar actionbar = activity.getActionBar();
				actionbar.selectTab(actionbar.getTabAt(0));//.selectTab();
				/*
				Intent i = new Intent(getActivity(), ConnectionDetailActivity.class);//RatingsActivity.class);
				i.putExtra(ConnectionDetailActivity.RELATIONSHIP_ID_EXTRA, relationship.getObjectId());
				startActivity(i);
				*/
			}
		});
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private void populateExistingData() {
		if(((User) ParseUser.getCurrentUser()).isMentor()) {
			tvName.setText(mentee.getFullName() + "?");
			rbRating.setRating((float) relationship.getMenteeRating());
			etReview.setText(relationship.getCommentForMentee());
			
		} else {			
			tvName.setText(mentor.getFullName() + "?");
			rbRating.setRating((float) relationship.getMentorRating());
			etReview.setText(relationship.getCommentForMentor());
		}
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String objId = getActivity().getIntent().getStringExtra(ConnectionDetailActivity.RELATIONSHIP_ID_EXTRA);
		ParseQueries.getRelationshipById(objId, new RetrieveRelationshipCallback());
	}
	
	private class RetrieveRelationshipCallback extends GetCallback<MatchRelationship> {
		@Override
		public void done(MatchRelationship obj, ParseException e) {
			if(e == null) {
				relationship = obj;
				mentee = (User) relationship.getMentee();
				mentor = (User) relationship.getMentor();
				populateExistingData();
				btnSave.setEnabled(true);				
			} else {
				Log.d("DEBUG", "Error getting relationship");
				e.printStackTrace();
			}
		}		
	}

}

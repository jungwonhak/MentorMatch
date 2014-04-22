package com.codepath.mentormatch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class RatingActivity extends Activity {
	public static final String RELATIONSHIP_ID_EXTRA = "relationshipId";

	private MatchRelationship relationship;
	private String requestId;
	
	private Button btnSave;
	private EditText etReview;
	private RatingBar rbRating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
		etReview = (EditText) findViewById(R.id.etReview);
		btnSave = (Button) findViewById(R.id.btnSave);
		rbRating = (RatingBar) findViewById(R.id.rbRating);
		requestId = getIntent().getStringExtra(RELATIONSHIP_ID_EXTRA);
		btnSave.setEnabled(false);
		retrieveRelationship();
	}
	
	private void retrieveRelationship(){
		ParseObject mentorReq = ParseObject.create("MentorRequest");
		mentorReq.setObjectId(requestId);
//	    return pointer;
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_REQUEST_KEY, mentorReq);
		query.include(MatchRelationship.MENTEE_USER_ID_KEY);
		query.include(MatchRelationship.MENTOR_USER_ID_KEY);
		query.getFirstInBackground(new GetCallback<MatchRelationship>() {
			
			@Override
			public void done(MatchRelationship arg0, ParseException e) {
				if(e == null) {
					relationship = arg0;
					btnSave.setEnabled(true);
				} else {
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rating, menu);
		return true;
	}

	public void saveRating(View view) {
		User mentee = (User) relationship.getMentee();
//		User mentor = (User) relationship.getMentor();
		if(ParseUser.getCurrentUser().equals(mentee)) {
			relationship.setMentorRating(rbRating.getRating());
			relationship.setCommentForMentor(etReview.getText().toString());
		} else {
			relationship.setMenteeRating(rbRating.getRating());
			relationship.setCommentForMentee(etReview.getText().toString());
		}
		relationship.saveInBackground();
		setResult(RESULT_OK); // set result code and bundle data for response
		finish();
	}
}

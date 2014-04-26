package com.codepath.mentormatch.activities;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.Task;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class RatingsActivity extends Activity {
	public static final String RELATIONSHIP_ID_EXTRA = "relationshipId";

	private MatchRelationship relationship;
	private User mentee;
	private User mentor;
	private String requestId;
	
	private Button btnSave;
	private EditText etReview;
	private RatingBar rbRating;
	private TextView tvName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ratings);
		etReview = (EditText) findViewById(R.id.etReview);
		btnSave = (Button) findViewById(R.id.btnSave);
		rbRating = (RatingBar) findViewById(R.id.rbRating);
		tvName = (TextView) findViewById(R.id.tvName);
		requestId = getIntent().getStringExtra(RELATIONSHIP_ID_EXTRA);
		btnSave.setEnabled(false);
		retrieveRelationship();
		setupTask();
	}
	
	private void retrieveRelationship(){
		ParseObject mentorReq = ParseObject.create("MentorRequest");
		mentorReq.setObjectId(requestId);
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_REQUEST_KEY, mentorReq);
		query.include(MatchRelationship.MENTEE_USER_ID_KEY);
		query.include(MatchRelationship.MENTOR_USER_ID_KEY);
		query.getFirstInBackground(new GetCallback<MatchRelationship>() {
			
			@Override
			public void done(MatchRelationship arg0, ParseException e) {
				if(e == null) {
					relationship = arg0;
					mentee = (User) relationship.getMentee();
					mentor = (User) relationship.getMentor();
					populateExistingData();
					btnSave.setEnabled(true);
					
					retrieveTasks();
				} else {
					e.printStackTrace();
				}
				
			}
		});
	}

	private void populateExistingData() {
		if(ParseUser.getCurrentUser().equals(mentee)) {
			tvName.setText(mentor.getFullName());
			rbRating.setRating((float) relationship.getMentorRating());
			etReview.setText(relationship.getCommentForMentor());
		} else {
			tvName.setText(mentee.getFullName());
			rbRating.setRating((float) relationship.getMenteeRating());
			etReview.setText(relationship.getCommentForMentee());
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void saveRating(View view) {
//		User mentee = (User) relationship.getMentee();
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
	
	
	// Task Related Objects
	private EditText etTaskDescription;
	
	private void setupTask() {
		etTaskDescription = (EditText) findViewById(R.id.etTaskDescription);
	}
	public void onAddTask(View view) {
		Task task = new Task();
		task.setDescription(etTaskDescription.getText().toString());
		task.setDueDate(new Date());
		task.setMatchRelationshipId(relationship);
		task.saveInBackground();
	}
	
	private void retrieveTasks(){
		ParseQuery<Task> query = ParseQuery.getQuery("Task");
		query.whereEqualTo(Task.RELATIONSHIP_KEY, relationship);
		query.findInBackground(new FindCallback<Task>() {

			@Override
			public void done(List<Task> list, ParseException e) {
				// TODO Auto-generated method stub
				if(e == null) {
					Toast.makeText(getBaseContext(), "Tasks found: " + list.size(), Toast.LENGTH_LONG).show();
				} else {
					e.printStackTrace();
				}
				
			}
		}); 
			
	}

}

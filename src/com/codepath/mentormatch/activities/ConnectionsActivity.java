package com.codepath.mentormatch.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.adapters.RequestInfoAdapter;
import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ConnectionsActivity extends Activity {

	ListView lvConnections;
	ArrayList<MentorRequest> requestList;
	RequestInfoAdapter requestAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connections);
		lvConnections = (ListView) findViewById(R.id.lvConnections);
		requestList = new ArrayList<MentorRequest>();
		requestAdapter = new RequestInfoAdapter(this, requestList);
		lvConnections.setAdapter(requestAdapter);
		setupListeners();
		retrieveConnections();
	}
	
	private void setupListeners() {
		lvConnections.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
    			MentorRequest requestId = requestAdapter.getItem(pos);
				Intent i = new Intent(getBaseContext(), RatingActivity.class);
				i.putExtra(RatingActivity.RELATIONSHIP_ID_EXTRA, requestId);
				startActivity(i);
    		}
    	});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connections, menu);
		return true;
	}
	
	private void retrieveConnections() {
		/*
		String [] mentorList = {ParseUser.getCurrentUser().getObjectId()};
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MatchRelationship");
		query.whereContainedIn(MentorRequest.REQUESTED_MENTORS_LIST_KEY, Arrays.asList(mentorList));
		query.include(MentorRequest.MENTEE_USER_ID_KEY);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> requestList, ParseException e) {
		        if (e == null) {
		            Log.d("DEBUG", "Retrieved " + requestList.size() + " REQUESTS");
		            for(ParseObject obj : requestList) {
		            	requestAdapter.add((MentorRequest)obj);
		            }
		            requestAdapter.notifyDataSetChanged();
		        } else {
		            Log.d("DEBUG", "Error: " + e.getMessage());
		        }
		    }
		});
*/		
		// Assume ParseObject myPost was previously created.
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY, ParseUser.getCurrentUser());
		query.include(MatchRelationship.MENTOR_REQUEST_KEY);
		query.include(MatchRelationship.MENTEE_USER_ID_KEY);
		query.findInBackground(new FindCallback<MatchRelationship>() {
		  public void done(List<MatchRelationship> relationsList, ParseException e) {
		        if (e == null) {
		            Log.d("DEBUG", "Retrieved " + relationsList.size() + " REQUESTS");
		            for(MatchRelationship obj : relationsList) {
		            	requestAdapter.add(obj.getMentorRequestId());
		            }
		            requestAdapter.notifyDataSetChanged();
		        } else {
		            Log.d("DEBUG", "Error: " + e.getMessage());
		        }
		  }
		});
	}

}

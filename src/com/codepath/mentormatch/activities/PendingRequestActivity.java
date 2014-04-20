package com.codepath.mentormatch.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PendingRequestActivity extends Activity {

	ListView lvPendingRequests;
	ArrayList<String> requestList;
	ArrayAdapter<String> requestAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pending_request);
		lvPendingRequests = (ListView) findViewById(R.id.lvPendingRequests);
		requestList = new ArrayList<String>();
		requestAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, requestList);
		lvPendingRequests.setAdapter(requestAdapter);
		setupListeners();
		retrieveOpenRequests();
	}

	private void setupListeners() {
		lvPendingRequests.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
    			String requestId = requestAdapter.getItem(pos);
    			ParseQuery<MentorRequest> query = ParseQuery.getQuery("MentorRequest");
    			query.getInBackground(requestId, new GetCallback<MentorRequest>() {
    				public void done(MentorRequest object, ParseException e) {
    					if (e == null) {
    						MatchRelationship relationship = new MatchRelationship();
    						relationship.setMentee(object.getMentee());
    						relationship.setMentor(ParseUser.getCurrentUser());
    						relationship.setMentorRequestId(object);
    						relationship.saveInBackground();
//    						((MentorRequest) object).addMentorToList(userObjId);
//    						object.saveInBackground();
    						Log.d("DEBUG", "ADDING MENTOR TO relationship: " + object.getObjectId());
    					} else {
    						Log.d("DEBUG", "ERROR IN ADDING MENTOR TO REQUEST: "
    								+ object);
    						e.printStackTrace();
    					}
    				}
    			});
    		}
    	});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pending_request, menu);
		return true;
	}

	private void retrieveOpenRequests() {
		String [] mentorList = {ParseUser.getCurrentUser().getObjectId()};
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MentorRequest");
		query.whereContainedIn(MentorRequest.REQUESTED_MENTORS_LIST_KEY, Arrays.asList(mentorList));
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> requestList, ParseException e) {
		        if (e == null) {
		            Log.d("DEBUG", "Retrieved " + requestList.size() + " REQUESTS");
		            for(ParseObject obj : requestList) {
		            	requestAdapter.add(obj.getObjectId());
		            }
		            requestAdapter.notifyDataSetChanged();
		        } else {
		            Log.d("DEBUG", "Error: " + e.getMessage());
		        }
		    }
		});
	}
	
}

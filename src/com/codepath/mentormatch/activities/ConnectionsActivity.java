package com.codepath.mentormatch.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.adapters.ConnectionsAdapter;
import com.codepath.mentormatch.core.MentorMatchApplication;
import com.codepath.mentormatch.fragments.DetailsFragment;
import com.codepath.mentormatch.fragments.MatchResultsListFragment;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class ConnectionsActivity extends Activity {
	public static final int RATING_REQUEST_CODE = 100;
	
	ListView lvConnections;
	ArrayList<MatchRelationship> requestList;
	ConnectionsAdapter requestAdapter;
	ProgressBar pbLoading;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connections);
		pbLoading = (ProgressBar) findViewById(R.id.pbLoading);
		pbLoading.setVisibility(ProgressBar.VISIBLE);
		lvConnections = (ListView) findViewById(R.id.lvConnections);
		requestList = new ArrayList<MatchRelationship>();
		requestAdapter = new ConnectionsAdapter(this, requestList);
		lvConnections.setAdapter(requestAdapter);
		setupListeners();
		retrieveConnections();
	}
	
	private void setupListeners() {
		lvConnections.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
    			MatchRelationship requestId = requestAdapter.getItem(pos);
				Intent i = new Intent(getBaseContext(), ConnectionDetailActivity.class);//RatingsActivity.class);
				i.putExtra(RatingsActivity.RELATIONSHIP_ID_EXTRA, requestId.getObjectId());
				startActivityForResult(i, RATING_REQUEST_CODE);
    		}
    	});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == RATING_REQUEST_CODE) {
		  Toast.makeText(this, "Rating has been saved.", Toast.LENGTH_SHORT).show();
	  }
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		if (((User) ParseUser.getCurrentUser()).isMentor()) {
			getMenuInflater().inflate(R.menu.viewconnectionsmentor, menu);
		} else {
			getMenuInflater().inflate(R.menu.viewconnections, menu);
		}
		return true;
	}
	
	public void viewRequests(MenuItem item) {
		//Intent i = new Intent(this, PendingRequestActivity.class);
		Intent i = new Intent(this, ProfileBuilderActivity.class);
		i.putExtra("foo", "details");
		//i.putExtra(ProfileSummaryListFragment.USER_EXTRA, ParseUser.getCurrentUser().getObjectId());
		startActivity(i);
	}
	
	public void viewMyProfile(MenuItem item) {
		Intent i = new Intent(this, ProfileDetailActivity.class);
		i.putExtra(MatchResultsListFragment.USER_EXTRA, ParseUser.getCurrentUser().getObjectId());
		startActivity(i);
	}
	
	public void goHome(MenuItem item) {
		Intent i = new Intent(this, MatchResultsActivity.class);
		startActivity(i);
	}
	
	public void handleLogout(MenuItem item){
		MentorMatchApplication.logoutUser();
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
	}
	
	private void retrieveConnections() {
//		ParseQueries.findRelationshipsForUser(new FindCallbackClass());
		ParseQueries.findConnectionsForCurrentUser(new FindCallbackClass());
	}
	
	private class FindCallbackClass extends FindCallback<MatchRelationship> {

		@Override
		public void done(List<MatchRelationship> relationsList, ParseException e) {
			// TODO Auto-generated method stub
	        if (e == null) {
	        	pbLoading.setVisibility(ProgressBar.INVISIBLE);
	            Log.d("DEBUG", "Retrieved " + relationsList.size() + " relationships");
	            for(MatchRelationship obj : relationsList) {
	            	requestAdapter.add(obj);
	            }
	            requestAdapter.notifyDataSetChanged();
	            if(relationsList.size() == 0) {
	            	TextView tvEmptyList = (TextView) findViewById(R.id.tvEmptyList);
	            	tvEmptyList.setText(getBaseContext().getResources().getString(R.string.connections_empty_list));
	            	tvEmptyList.setVisibility(View.VISIBLE);
	            }
	        } else {
	            Log.d("DEBUG", "Error: " + e.getMessage());
	        }
			
		}
		
	}

}

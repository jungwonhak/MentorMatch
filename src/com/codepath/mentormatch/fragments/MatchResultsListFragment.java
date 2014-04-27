package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ProfileDetailActivity;
import com.codepath.mentormatch.adapters.MatchResultsAdapter;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;

public abstract class MatchResultsListFragment extends Fragment{
//	protected MatchResultsAdapter profileAdapter;
	protected MatchResultsAdapter profileAdapter;
//	protected List<ParseUser> usersList;
	protected List usersList;
	protected ListView lvProfileSummaries;

	
	public static final String USER_EXTRA = "user";
	public static final String REQUEST_ID_EXTRA = "requestId";
	
	public abstract void fetchProfiles();
	public abstract void setListViewListeners();
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		usersList = new ArrayList();
		profileAdapter = new MatchResultsAdapter(getActivity(), usersList);
		fetchProfiles();		
	}

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		View view = inf.inflate(R.layout.fragment_match_results_list, parent,  false);
		lvProfileSummaries = (ListView) view.findViewById(R.id.lvProfileSummaries);
		lvProfileSummaries.setAdapter(profileAdapter);
		setListViewListeners();
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	


}

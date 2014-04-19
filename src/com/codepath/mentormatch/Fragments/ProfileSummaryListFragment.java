package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.adapters.ProfileSummaryAdapter;
import com.parse.ParseUser;

public abstract class ProfileSummaryListFragment extends Fragment{
	protected ProfileSummaryAdapter profileAdapter;
	protected List<ParseUser> usersList;
	protected ListView lvProfileSummaries;
	
	public abstract void fetchProfiles();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		usersList = new ArrayList<ParseUser>();
		profileAdapter = new ProfileSummaryAdapter(getActivity(), usersList);
		fetchProfiles();		
	}

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		View view = inf.inflate(R.layout.fragment_profile_summary_list, parent,  false);
		lvProfileSummaries = (ListView) view.findViewById(R.id.lvProfileSummaries);
		lvProfileSummaries.setAdapter(profileAdapter);

		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}

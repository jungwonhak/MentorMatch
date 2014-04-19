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
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ViewProfileActivity;
import com.codepath.mentormatch.adapters.ProfileSummaryAdapter;
import com.codepath.mentormatch.models.User;
import com.parse.ParseUser;

public abstract class ProfileSummaryListFragment extends Fragment{
	protected ProfileSummaryAdapter profileAdapter;
	protected List<ParseUser> usersList;
	protected ListView lvProfileSummaries;
	
	public static final String USER_EXTRA = "user";
	
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
		setListViewListeners();
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	private void setListViewListeners() {
		lvProfileSummaries.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				Log.d("TEST - Item Click Listener", "on item click: " + pos);
				Intent i = new Intent(getActivity().getBaseContext(), ViewProfileActivity.class);
				i.putExtra(USER_EXTRA, (User) profileAdapter.getItem(pos));
				startActivity(i);
			}
		});

	}

}

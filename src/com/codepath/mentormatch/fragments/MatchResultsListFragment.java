package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.adapters.MatchResultsAdapter;
import com.fortysevendeg.swipelistview.BaseSwipeListViewListener;
import com.fortysevendeg.swipelistview.SwipeListView;

public abstract class MatchResultsListFragment extends Fragment{
//	protected MatchResultsAdapter profileAdapter;
	protected MatchResultsAdapter profileAdapter;
//	protected List<ParseUser> usersList;
	protected List usersList;
	protected SwipeListView lvProfileSummaries;
	protected ProgressBar pbLoading;
	protected TextView tvEmptyList; 
	
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
		lvProfileSummaries = (SwipeListView) view.findViewById(R.id.lvProfileSummaries);
		pbLoading = (ProgressBar) view.findViewById(R.id.pbLoading);
		pbLoading.setVisibility(ProgressBar.VISIBLE);
		tvEmptyList = (TextView) view.findViewById(R.id.tvEmptyList);
		lvProfileSummaries.setAdapter(profileAdapter);
		lvProfileSummaries.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            	Log.d("DEBUG", "Opened");
            	profileAdapter.remove(profileAdapter.getItem(position));
            	lvProfileSummaries.closeOpenedItems();
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            	Log.d("DEBUG", "Closed");
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }
        });
        
		setListViewListeners();
		return view;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	
	


}

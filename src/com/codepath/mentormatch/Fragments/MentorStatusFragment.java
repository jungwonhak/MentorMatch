package com.codepath.mentormatch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ProfileBuilderActivity;

public class MentorStatusFragment extends Fragment {
	private Button mentorNext;
	
	public static final String MENTOR_STATUS_EXTRA = "foo";
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_mentor_status, parent,  false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		mentorNext = (Button) getActivity().findViewById(R.id.btnMentorNext);

	    mentorNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ProfileBuilderActivity.class);
				i.putExtra(MENTOR_STATUS_EXTRA, "mentor");
				startActivity(i);
				
			}
		}); 
	  } 

}

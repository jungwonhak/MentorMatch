package com.codepath.mentormatch.fragments;

import android.app.Activity;
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
	private Button btnFindMentor;
	private Button btnBeMentor;
	private static String BE_MENTOR = "BE_MENTOR";
	private static String FIND_MENTOR = "FIND_MENTOR";
	
	public static final String MENTOR_STATUS_EXTRA = "foo";
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		
		View v = inf.inflate(R.layout.fragment_mentor_status, parent,  false);
		btnFindMentor = (Button) v.findViewById(R.id.btnFindMentor);
		btnBeMentor = (Button) v.findViewById(R.id.btnBeMentor);
		btnFindMentor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ProfileBuilderActivity.class);
				i.putExtra(MENTOR_STATUS_EXTRA, FIND_MENTOR);
				startActivity(i);
			}
		});
		return v;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}	
}

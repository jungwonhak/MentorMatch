package com.codepath.mentormatch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.HomeActivity;
import com.codepath.mentormatch.activities.MentorMatchActivity;

public class AboutMeFragment extends Fragment{
	private Button mentorNext;
	private EditText mentorText;
	public static final String ABOUT_ME_PAGE_EXTRA = "foo";
	public static final String TEXT_EXTRA = "about";
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_about_me, parent,  false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {

	    mentorNext = (Button) getActivity().findViewById(R.id.btnAboutMeNext);
	    mentorText = (EditText) getActivity().findViewById(R.id.etAboutMe);

	    mentorNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), mentorText.getText(), Toast.LENGTH_LONG).show();
				Intent i = new Intent(getActivity(), MentorMatchActivity.class);
				i.putExtra(ABOUT_ME_PAGE_EXTRA, "about_me");
				i.putExtra(TEXT_EXTRA, mentorText.getText());
				startActivity(i);
				
			}
		}); 

	}
}

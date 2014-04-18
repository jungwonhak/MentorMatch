package com.example.mentormatch.Fragments;

import com.example.mentormatch.Profile_Builder;
import com.example.mentormatch.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfileDetailsFragment extends Fragment{
	private Button profileNext;
	private EditText nameText;
	private EditText jobText;
	private Spinner locationText; 
	
	
	public static final String PROFILE_PAGE_EXTRA = "foo";
	public static final String NAME_EXTRA = "profile_name";
	public static final String JOB_EXTRA = "job";
	public static final String LOCATION_EXTRA = "location";
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_profile_details, parent,  false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {

		nameText = (EditText) getActivity().findViewById(R.id.etProfileName);
		jobText = (EditText) getActivity().findViewById(R.id.etProfileJob);
		locationText = (Spinner) getActivity().findViewById(R.id.spinLocation);
		profileNext = (Button) getActivity().findViewById(R.id.btnProfileNext);

	    profileNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getActivity(), String.valueOf(locationText.getSelectedItem()), Toast.LENGTH_LONG).show();
				Intent i = new Intent(getActivity(), Profile_Builder.class);
				i.putExtra(PROFILE_PAGE_EXTRA, "details");
				i.putExtra(NAME_EXTRA, nameText.getText());
				i.putExtra(JOB_EXTRA, jobText.getText());
				i.putExtra(LOCATION_EXTRA, String.valueOf(locationText.getSelectedItem()));
				startActivity(i);
				
			}
		}); 
	  }  
}


package com.example.mentormatch.Fragments;

import com.example.mentormatch.MainActivity;
import com.example.mentormatch.Profile_Builder;
import com.example.mentormatch.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LanguageSelectionFragment extends Fragment {
	private RadioGroup radioLanguageGroup;
	private Button languageNext;
	private RadioButton radioLanguageButton;
	public static final String LANGUAGE_PAGE_EXTRA = "foo";
	public static final String LANGUAGE_EXTRA = "language";
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		return inf.inflate(R.layout.fragment_language_selection, parent,  false);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {

	    radioLanguageGroup = (RadioGroup) getActivity().findViewById(R.id.languageOptions);
	    languageNext = (Button) getActivity().findViewById(R.id.languageNext);

	    languageNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int selectedId = radioLanguageGroup.getCheckedRadioButtonId();
				radioLanguageButton = (RadioButton) getActivity().findViewById(selectedId);
				
				Intent i = new Intent(getActivity(), Profile_Builder.class);
				i.putExtra(LANGUAGE_PAGE_EXTRA, "language");
				i.putExtra(LANGUAGE_EXTRA, radioLanguageButton.getText());
				startActivity(i);
				
			}
		}); 
	  }  
}

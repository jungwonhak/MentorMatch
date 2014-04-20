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

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.MentorMatchActivity;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.Skill;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class AboutMeFragment extends Fragment{
	private MentorRequest request;
	private Skill skill;
	private Button btnFindMentor;
	private EditText etDescription;
	public static final String ABOUT_ME_PAGE_EXTRA = "foo";
	public static final String TEXT_EXTRA = "about";
	public static final String SKILL_EXTRA = "skill";
	public static final String REQUEST_EXTRA = "requestObjectId";
	public static final String SKILL_ARG = "skill";
	
	
	public static AboutMeFragment newInstance(Skill aSkill) {
		AboutMeFragment fragment = new AboutMeFragment();
        Bundle args = new Bundle();
        args.putSerializable(SKILL_ARG, aSkill);
        fragment.setArguments(args);
        return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		skill = (Skill) args.getSerializable(SKILL_ARG);
	}

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
		
		View v = inf.inflate(R.layout.fragment_about_me, parent,  false);
	    btnFindMentor = (Button) v.findViewById(R.id.btnAboutMeNext);
	    etDescription = (EditText) v.findViewById(R.id.etAboutMe);
		addListenerOnButton();

		return v;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void addListenerOnButton() {
	    btnFindMentor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateBackend();


				
			}
		}); 
	}
	
	public void updateBackend() {
//		User u = (User)ParseUser.getCurrentUser();
//		u.setDescription(etDescription.getText().toString());
//		u.saveInBackground();
		request = new MentorRequest();
		request.setMentee(ParseUser.getCurrentUser());
		request.setSkill(skill);
		request.setDescription(etDescription.getText().toString());
		request.saveInBackground(new SaveCallback() {

			@Override
			public void done(ParseException e) {
				if(e == null) {
				// TODO Auto-generated method stub
					//Toast.makeText(getActivity(), etDescription.getText(), Toast.LENGTH_LONG).show();
					Intent i = new Intent(getActivity(), MentorMatchActivity.class);
					//i.putExtra(ABOUT_ME_PAGE_EXTRA, "about_me");
					//i.putExtra(TEXT_EXTRA, etDescription.getText());
					//i.putExtra(SKILL_EXTRA, skill);
					//i.putExtra(REQUEST_EXTRA, request.getObjectId());
					//Log.d("DEBUG", "Created Mentor Request - request id: " + request.getObjectId());
					startActivity(i);				
				} else {
					e.printStackTrace();
				}
			}
			
		});
		
//		request.s
	}
}

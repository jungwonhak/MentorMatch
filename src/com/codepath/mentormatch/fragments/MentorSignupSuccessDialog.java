package com.codepath.mentormatch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.codepath.mentormatch.R;

public class MentorSignupSuccessDialog extends DialogFragment {
	private Button btnSignupOk;
	private TextView tvSignupBody;
	private String firstName;

	public MentorSignupSuccessDialog() {
	}

	public static MentorSignupSuccessDialog newInstance(String firstName) {
		MentorSignupSuccessDialog fragment = new MentorSignupSuccessDialog();
		
		fragment.firstName = firstName;
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().setTitle(R.string.sign_up_success_title);
		View view = inflater
				.inflate(R.layout.fragment_dialog_mentor_signup_success, container);
		btnSignupOk = (Button) view.findViewById(R.id.btnSignupOk);
		tvSignupBody = (TextView) view.findViewById(R.id.tvSignupBody);
		tvSignupBody.setText(firstName + tvSignupBody.getText().toString());
		btnSignupOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		return view;
	}
}

package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.Hashtable;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.MatchResultsActivity;
import com.codepath.mentormatch.activities.ProfileBuilderActivity;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class SkillsFragment extends Fragment implements OnClickListener {
	public static final String LANGUAGE_PAGE_EXTRA = "foo";
	public static final String LANGUAGE_EXTRA = "language";

	private LinearLayout llPython;
	private LinearLayout llIos;
	private LinearLayout llRuby;
	private LinearLayout llAndroid;

	private ImageView ivPython;
	private ImageView ivRuby;
	private ImageView ivIos;
	private ImageView ivAndroid;

	private ImageView ivFirstSkill;
	private Button btnSkillNext;
	private TextView tvProfileDetails;
	private ArrayList<Skill> skills;
	private Hashtable<Skill, SkillDrawable> potentialSkills;
	private User user;
	private boolean canSelectMultipleLanguages = false;

	private class SkillDrawable {
		public SkillDrawable(Drawable o, LayerDrawable dwc, ImageView iv) {
			original = o;
			drawableWithCheckmark = dwc;
			imageView = iv;
		}
		public Drawable original;
		public LayerDrawable drawableWithCheckmark;
		public ImageView imageView;
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inf.inflate(R.layout.fragment_skills, parent, false);
		llPython = (LinearLayout) v.findViewById(R.id.llPython);
		llPython.setOnClickListener(this);
		llIos = (LinearLayout) v.findViewById(R.id.llIos);
		llIos.setOnClickListener(this);
		llRuby = (LinearLayout) v.findViewById(R.id.llRuby);
		llRuby.setOnClickListener(this);
		llAndroid = (LinearLayout) v.findViewById(R.id.llAndroid);
		llAndroid.setOnClickListener(this);
		btnSkillNext = (Button) v.findViewById(R.id.btnSkillNext);
		btnSkillNext.setOnClickListener(this);
		tvProfileDetails = (TextView) v.findViewById(R.id.tvProfileDetails);

		skills = new ArrayList<Skill>();
		potentialSkills = new Hashtable<Skill, SkillDrawable>();
		ivPython = (ImageView) v.findViewById(R.id.ivPython);
		potentialSkills.put(Skill.PYTHON, new SkillDrawable(ivPython.getDrawable(), createBmp(ivPython), ivPython));
		ivRuby = (ImageView) v.findViewById(R.id.ivRuby);
		potentialSkills.put(Skill.RUBY, new SkillDrawable(ivRuby.getDrawable(), createBmp(ivRuby), ivRuby));
		ivIos = (ImageView) v.findViewById(R.id.ivIos);
		potentialSkills.put(Skill.IOS, new SkillDrawable(ivIos.getDrawable(), createBmp(ivIos), ivIos));
		ivAndroid = (ImageView) v.findViewById(R.id.ivAndroid);
		potentialSkills.put(Skill.ANDROID, new SkillDrawable(ivAndroid.getDrawable(), createBmp(ivAndroid), ivAndroid));

		// Set up the checkmark on the first skill
		ivPython.setImageDrawable(potentialSkills.get(Skill.PYTHON).drawableWithCheckmark);
		skills.add(Skill.PYTHON);
		user = (User) ParseUser.getCurrentUser();
		if (user.isMentor()) {
			tvProfileDetails.setText(R.string.teachingLanguageString);
			canSelectMultipleLanguages = true;
		}
		return v;
	}

	private LayerDrawable createBmp(ImageView iv) {
		Drawable d1 = iv.getDrawable();
		BitmapDrawable d2 = (BitmapDrawable) getResources().getDrawable(
				R.drawable.checkmark);

		Drawable drawableArray[] = new Drawable[] { d1, d2 };
		LayerDrawable layerDraw = new LayerDrawable(drawableArray);
		// Need to find a way to overlay the checkmark on the image properly.
		// layerDraw.setLayerInset(1, 150, -100, 0, 0);
		// d2.setBounds(100, 100, 50, 50);

		return layerDraw;
	}

	private void updateBackend() {
		if (skills.size() == 0) {
			Log.d("DEBUG", "Trying to send empty list of skills");
			return;
		}
		
		ArrayList<String> skillsToSend = new ArrayList<String>();
		for (Skill s : skills) {
			skillsToSend.add(s.toString());
		}
		if (user.isMentor()) {
			user.setSkills(skillsToSend);
			goToNextPage();
		}
		else {
			MentorRequest request = new MentorRequest();
			request.setMentee(ParseUser.getCurrentUser());
			request.setSkill(skills.get(0));
			request.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					if(e == null) {
					// TODO Auto-generated method stub
						//Toast.makeText(getActivity(), etDescription.getText(), Toast.LENGTH_LONG).show();
						goToNextPage();
					} else {
						e.printStackTrace();
					}
				}
				
			});

		}
	}

	private void toggleSkill(Skill s) {
		SkillDrawable sd = potentialSkills.get(s);
		if (skills.contains(s)) {
			skills.remove(s);
			sd.imageView.setImageDrawable(sd.original);
		} else {
			if (canSelectMultipleLanguages) {
				skills.add(s);
				sd.imageView.setImageDrawable(sd.drawableWithCheckmark);
			}
			else { // Only one language can be selected
				while (skills.size() > 0) { // remove old one
					Skill skillToRemove = skills.remove(0);
					SkillDrawable sdToRemove = potentialSkills.get(skillToRemove);
					sdToRemove.imageView.setImageDrawable(sdToRemove.original);
				}
				skills.add(s);
				sd.imageView.setImageDrawable(sd.drawableWithCheckmark);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.llPython:
			Log.d("DEBUG", "Python");
			toggleSkill(Skill.PYTHON);
			break;
		case R.id.llIos:
			Log.d("DEBUG", "iOS");
			toggleSkill(Skill.IOS);
			break;
		case R.id.llRuby:
			Log.d("DEBUG", "Ruby");
			toggleSkill(Skill.RUBY);
			break;
		case R.id.llAndroid:
			Log.d("DEBUG", "Android");
			toggleSkill(Skill.ANDROID);
			break;
		case R.id.btnSkillNext:
			updateBackend();
			break;
		default:
			Log.d("DEBUG", "Unrecognized click");
			return;
		}
	}

	private void goToNextPage() {
		Intent i = new Intent(getActivity(), ProfileBuilderActivity.class);
		i.putExtra(LANGUAGE_PAGE_EXTRA, "language");
		startActivity(i);		
	}
}

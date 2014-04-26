package com.codepath.mentormatch.fragments;

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

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.activities.ProfileBuilderActivity;
import com.codepath.mentormatch.models.Skill;

public class LanguageSelectionFragment extends Fragment implements
		OnClickListener {
	public static final String LANGUAGE_PAGE_EXTRA = "foo";
	public static final String LANGUAGE_EXTRA = "language";

	private LinearLayout llPython;
	private LinearLayout llIos;
	private LinearLayout llRuby;
	private LinearLayout llAndroid;
	private ImageView ivFirstSkill;
	private Button btnSkillNext;
	private Skill skill;

	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup parent,
			Bundle savedInstanceState) {

		View v = inf.inflate(R.layout.fragment_language_selection, parent,
				false);
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

		// Set up the checkmark on the first skill
		ivFirstSkill = (ImageView) v.findViewById(R.id.ivPython);
		ivFirstSkill.setImageDrawable(createBmp(ivFirstSkill));
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

	@Override
	public void onClick(View v) {
		ImageView iv = null;
		switch (v.getId()) {
		case R.id.llPython:
			Log.d("DEBUG", "Python");
			skill = Skill.PYTHON;
			iv = (ImageView) v.findViewById(R.id.ivPython);
			iv.setImageDrawable(createBmp(iv));
			break;
		case R.id.llIos:
			Log.d("DEBUG", "iOS");
			skill = Skill.IOS;
			iv = (ImageView) v.findViewById(R.id.ivIos);
			iv.setImageDrawable(createBmp(iv));
			break;
		case R.id.llRuby:
			Log.d("DEBUG", "Ruby");
			skill = Skill.RUBY;
			iv = (ImageView) v.findViewById(R.id.ivRuby);
			iv.setImageDrawable(createBmp(iv));
			break;
		case R.id.llAndroid:
			Log.d("DEBUG", "Android");
			skill = Skill.ANDROID;
			iv = (ImageView) v.findViewById(R.id.ivAndroid);
			iv.setImageDrawable(createBmp(iv));
			break;
		case R.id.btnSkillNext:
			Intent i = new Intent(getActivity(), ProfileBuilderActivity.class);
			i.putExtra(LANGUAGE_PAGE_EXTRA, "language");
			i.putExtra(LANGUAGE_EXTRA, skill);
			startActivity(i);
		default:
			Log.d("DEBUG", "Unrecognized click");
			return;
		}
	}

}

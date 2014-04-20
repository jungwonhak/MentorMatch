package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.User;
// Currently not used
public class SkillImagesFragment extends Fragment {
	
	private ArrayList<String> logos;
	private String parseObjectId;
	public static final String PARSE_OBJECT_ID_ARG = "parseObjectId";
	private LinearLayout linearLayout;
	
    public static SkillImagesFragment newInstance(String parseObjectId) {
        SkillImagesFragment fragment = new SkillImagesFragment();
        Bundle args = new Bundle();
        args.putString(PARSE_OBJECT_ID_ARG, parseObjectId);
        fragment.setArguments(args);
        return fragment;
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		parseObjectId = args.getString(PARSE_OBJECT_ID_ARG);
		logos = new ArrayList<String>();
		for (String s : getSkills()) {
			logos.add((Skill.fromValue(s)).getLogo());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		linearLayout = (LinearLayout)v.findViewById(R.id.llLogoContainer);
		for (String logo : logos) {
            ImageView iv = new ImageView(v.getContext());
            
            Drawable drawable = getResources().getDrawable(getResources().getIdentifier(logo, "drawable", "com.codepath.mentormatch"));
            iv.setImageDrawable(drawable);
            linearLayout.addView(iv);
		}
		return v;
	}

	private List<String> getSkills() {
		User u = User.getUserById(parseObjectId);
		return u.getSkills();
	}
}

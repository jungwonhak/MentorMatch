package com.codepath.mentormatch.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Bundle;
import android.util.Log;

import com.codepath.mentormatch.helpers.MentorSearch;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.User;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MentorSearchResultsFragment extends ProfileSummaryListFragment {

	private static final String SKILL_ARG = "skill";

	public static MentorSearchResultsFragment newInstance(Skill skill) {
		MentorSearchResultsFragment fragment = new MentorSearchResultsFragment();
		Bundle args = new Bundle();
		args.putSerializable(SKILL_ARG, skill);
		fragment.setArguments(args);
		return fragment;
	}

	public MentorSearchResultsFragment() {
		super();
	}

	@Override
	public void fetchProfiles() {
		// search.findMentorsBasedOnSkill(Skill.ANDROID.toString());

		// this.usersList = (ArrayList) search.getUsersList();
		Skill skill = (Skill)getArguments().getSerializable(SKILL_ARG);
		String[] skillsList = { skill.toString() };

		ParseQuery<ParseUser> query = ParseUser.getQuery();
		// query.whereEqualTo(User.USER_NAME_KEY, "TestUser");
		query.whereContainedIn(User.SKILLS_LIST_KEY, Arrays.asList(skillsList));
		query.findInBackground(new FindCallback<ParseUser>() {
			public void done(List<ParseUser> objects, ParseException e) {
				if (e == null) {
					// The query was successful.
					Log.d("TEST", "userList size: " + objects.size());
					if (objects.size() > 0) {
						User aUser = (User) objects.get(0);
						Log.d("TEST", "skills: " + aUser.getSkills().size());
					}
					profileAdapter.addAll(objects);
					// profileAdapter.notifyDataSetChanged();
				} else {
					Log.d("score", "Error: " + e.getMessage());
				}
			}
		});

	}

}

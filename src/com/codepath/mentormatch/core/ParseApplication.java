package com.codepath.mentormatch.core;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.codepath.mentormatch.helpers.LinkedInClient;
import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SignUpCallback;

public class ParseApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(User.class);
		ParseObject.registerSubclass(MentorRequest.class);
		ParseObject.registerSubclass(MatchRelationship.class);

		ParseApplication.context = this;

		Parse.initialize(this, "SPqzrixlrCnLDAmmchtr8Uz2vqVJEQG58ivlbgmN",
				"kB3cp9xHengwfcLT7tE4xv5jO0fFerCdCb1nynSQ");

		// Create global configuration and initialize ImageLoader with this
		// configuration
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
				defaultOptions).build();
		ImageLoader.getInstance().init(config);
		
		// For Testing purpose only
//		createUser();
	}

	private void createUser() {
		User user = new User();
		user.setUsername("TestUser3");
		user.setPassword("abc");
		user.setEmail("email3@example.com");
		user.setLocation("san francisco");
		List<String> skills = new ArrayList<String>();
		skills.add(Skill.JAVA.toString());
		skills.add(Skill.ANDROID.toString());
		user.setSkills(skills);

		// other fields can be set just like with ParseObject
		user.put("phone", "650-253-0000");

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					Log.d("DEBUG", "SUCCESSFUL SIGN IN");
					
				} else {
					Log.d("DEBUG", "EXCEPTION!");
					e.printStackTrace();
				}
			}
		});

	}
	public static LinkedInClient getRestClient() {
		return (LinkedInClient) LinkedInClient.getInstance(
				LinkedInClient.class, ParseApplication.context);
	}

}
package com.codepath.mentormatch.core;

import android.content.Context;

import com.codepath.mentormatch.helpers.LinkedInClient;
import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.ParseObject;

public class ParseApplication extends com.activeandroid.app.Application {
	private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(MentorRequest.class);
        ParseObject.registerSubclass(MatchRelationship.class);

        ParseApplication.context = this;
        
        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
        		cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .defaultDisplayImageOptions(defaultOptions)
            .build();
        ImageLoader.getInstance().init(config);
  }     
    
    public static LinkedInClient getRestClient() {
    	return (LinkedInClient) LinkedInClient.getInstance(LinkedInClient.class, ParseApplication.context);
    }
}
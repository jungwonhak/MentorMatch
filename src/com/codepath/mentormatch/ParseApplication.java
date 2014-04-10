package com.codepath.mentormatch;

import android.app.Application;

import com.codepath.mentormatch.models.MatchRelationship;
import com.codepath.mentormatch.models.MentorRequest;
import com.codepath.mentormatch.models.User;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(MentorRequest.class);
        ParseObject.registerSubclass(MatchRelationship.class);

    // Add your initialization code here

  }     
}
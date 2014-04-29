package com.codepath.mentormatch.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.codepath.mentormatch.helpers.LinkedInClient;
import com.codepath.mentormatch.helpers.ParseQueries;
import com.codepath.mentormatch.helpers.ReviewsUtil;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.Task;
import com.codepath.mentormatch.models.parse.User;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MentorMatchApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		ParseObject.registerSubclass(User.class);
		ParseObject.registerSubclass(MentorRequest.class);
		ParseObject.registerSubclass(MatchRelationship.class);
		ParseObject.registerSubclass(Task.class);

		MentorMatchApplication.context = this;

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
//		getAllUserReviews();
	}

	public static LinkedInClient getRestClient() {
		return (LinkedInClient) LinkedInClient.getInstance(
				LinkedInClient.class, MentorMatchApplication.context);
	}

    
    
    public static void logoutUser() {
    	getRestClient().clearAccessToken();
		User currentUser = (User) ParseUser.getCurrentUser();
		if (currentUser != null) {
			ParseUser.logOut();
		}
    }    
    
    public static Map<String, HashMap<String, Object>> userReviews;
    private static RetrieveReviewsTask retrieveReviewsTask;
    private static FindReviewsCallback callback;
    
    public static void retrieveUserReviews() {
    	if(userReviews == null) {
    		userReviews = new HashMap<String, HashMap<String, Object>>();
    		retrieveReviewsTask = new RetrieveReviewsTask();
    		retrieveReviewsTask.execute();
    	}
    }

	public static Map<String, HashMap<String, Object>> getUserReviews() {
		retrieveUserReviews();
		return userReviews;
	}

    
	private static class RetrieveReviewsTask extends AsyncTask<String, Void, Boolean> {

		// Cannot use network capabilities on main thread, so need to retrieve image in background
		protected Boolean doInBackground(String... arg0) {
			try {
				ParseQuery <ParseUser> userQuery = ParseUser.getQuery();
				userQuery.findInBackground(new FindCallback<ParseUser>() {
					
					@Override
					public void done(List<ParseUser> userList, ParseException e) {
						if(e == null) {
							Log.d("DEBUG -TEST", "Retrieving all users:");
							for(ParseUser aUser : userList) {
								Log.d("DEBUG", "FETCHED USER: " + aUser.getObjectId());
								getFetchReviewsFromParse((User)aUser);
								userReviews.put(aUser.getObjectId(), new HashMap<String, Object>());
							}
						} else {
							Log.d("DEBUG", "Error retrieving all users");
							e.printStackTrace();
						}
					}
				});
				return Boolean.TRUE;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(Boolean bool) {
		}
	}
	
	public static void getFetchReviewsFromParse(User user) {

		callback = new FindReviewsCallback(user);
		ParseQueries.getReviewsForUser(user, callback);		
		
	}
	
	private static class FindReviewsCallback extends FindCallback<MatchRelationship> {
		private User user;
		
		public FindReviewsCallback(User aUser) {
			user = aUser;
		}
		@Override
		public void done(List<MatchRelationship> relationshipList, ParseException e) {
			if(e == null) {
				Log.d("DEBUG", "relationship list: " + relationshipList.size());
				ReviewsUtil reviewHelper = new ReviewsUtil(relationshipList);
				reviewHelper.getReviews(user.isMentor());
				double avgRating = reviewHelper.getAverageRating();
				Map<String, Object> map = userReviews.get(user.getObjectId());
				map.put("average", avgRating);
				map.put("reviews", reviewHelper.getTotalReviews());
			} else {
				Log.d("DEBUG", "Error retrieving views for user: " + user.getObjectId());
				e.printStackTrace();
			}
		}
	}
	
}
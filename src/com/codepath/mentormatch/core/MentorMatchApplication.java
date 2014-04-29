package com.codepath.mentormatch.core;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
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
    
    public static void getAllUserReviews() {
    	if(userReviews == null) {
    	userReviews = new HashMap<String, HashMap<String, Object>>();
    	retrieveReviewsTask = new RetrieveReviewsTask();
    	retrieveReviewsTask.execute();
    	}
    }

	public static Map<String, HashMap<String, Object>> getUserReviews() {
		return userReviews;
	}

	public void setUserReviews(Map<String, HashMap<String, Object>> userReviews) {
		this.userReviews = userReviews;
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
//			BitmapDrawable backgroundDrawable = new BitmapDrawable(getBaseContext().getResources(), bitmap);
//			ivBackgroundImage.setBackgroundDrawable(backgroundDrawable);
		}
	}
	
	public static void getFetchReviewsFromParse(User user) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getObjectId());

		String parseCloudFunction = "averageStarsForMentee";
		if(user.isMentor()) {
			parseCloudFunction = "averageStarsForMentor";
		}

		callback = new FindReviewsCallback(user);
		ParseQueries.getReviewsForUser(user, callback);
		/*
		ParseCloud.callFunctionInBackground(parseCloudFunction, params, new FunctionCallback<Object>() {
			public void done(Object ratings, ParseException e) {
				if (e == null) {
					if(ratings instanceof HashMap) {
						Object avg = ((HashMap)ratings).get("average");
						Object totalReviews = ((HashMap) ratings).get("");
						if(avg == null || avg.toString().equals("null")) {
								
						}else {
								Log.d("Debug", "done calling cloud code: " + avg);
							}
							if(totalReviews != null) {
							}
						}

						if(ratings != null) {
							Log.d("Debug", "done calling cloud code: " + ratings);
						}
				    }
				}
			});			
*/			
		
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
	
	public void processRatingsFromParse() {
		
	}
	/*
     * new RetrieveBitMapTask().execute(user.getBackgroundImageUrl());
     * 		
		
		
     * 
	private class RetrieveReviewsTask extends AsyncTask<String, Void, Bitmap> {

		// Cannot use network capabilities on main thread, so need to retrieve image in background
		protected Bitmap doInBackground(String... arg0) {
			try {
				HttpGet httpRequest = new HttpGet(URI.create(user.getBackgroundImageUrl()));
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
				HttpEntity entity = response.getEntity();
				BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
				Bitmap bitmap = BitmapFactory.decodeStream(bufHttpEntity.getContent());
				return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(Bitmap bitmap) {
			BitmapDrawable backgroundDrawable = new BitmapDrawable(getBaseContext().getResources(), bitmap);
			ivBackgroundImage.setBackgroundDrawable(backgroundDrawable);
		}
	}    
	*/
}
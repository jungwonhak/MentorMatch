package com.codepath.mentormatch.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.adapters.SmartFragmentStatePagerAdapter;
import com.codepath.mentormatch.fragments.MatchResultsListFragment;
import com.codepath.mentormatch.fragments.ReviewDetailFragment;
import com.codepath.mentormatch.models.Review;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ProfileDetailActivity extends FragmentActivity {

	private String userObjId;
	private String requestId;
	private User user;
	private TextView tvName;
	private TextView tvJobInfo;
	private TextView tvLocation;
	private Button btnContact;
	private RatingBar rbRating;
	private ImageView ivProfileImage;
	private LinearLayout llSkillImages;
	private ViewPager vpPager;
	private MyPagerAdapter adapterViewPager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_detail);
		userObjId = getIntent().getStringExtra(MatchResultsListFragment.USER_EXTRA);
		requestId = getIntent().getStringExtra(MatchResultsListFragment.REQUEST_ID_EXTRA);
		btnContact = (Button) findViewById(R.id.btnContact);
		rbRating = (RatingBar) findViewById(R.id.rbRating);
		rbRating.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
		rbRating.setFocusable(false);

		//if (requestId == null) {
			// hide connection button
		//	btnContact.setVisibility(View.GONE);
		//}
		getUser();
	}

	private void getUser() {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(userObjId, new GetCallback<ParseUser>() {
			public void done(ParseUser object, ParseException e) {
				if (e == null) {
					Log.d("DEBUG", "found user");
					user = (User) object;
					setViews();
				} else {
					// something went wrong
					Log.d("DEBUG",
							"ERROR - PROFILE ACTIVITY - COULD NOT FIND USER");
					e.printStackTrace();
				}
			}
		});
	}

	private void setViews() {
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(user.getFullName());

		tvJobInfo = (TextView) findViewById(R.id.tvJobInfo);
		tvJobInfo.setText(user.getJobTitle() + " at " + user.getCompany());

		tvLocation = (TextView) findViewById(R.id.tvLocation);
		tvLocation.setText(user.getLocation());

		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(65, 65);

		llSkillImages = (LinearLayout) findViewById(R.id.llSkillImages);
		if(user.getSkills() != null) {
		for (String s : user.getSkills()) {
			Log.d("DEBUG", "SKill: " + s);
			ImageView iv = new ImageView(this);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(params);
			Skill skill = Skill.fromValue(s);
			iv.setMaxHeight(15);
			iv.setMaxWidth(15);
			iv.setImageResource(skill.getResourceId());
			llSkillImages.addView(iv);
		}
		}

		if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
			ImageLoader.getInstance().displayImage(user.getProfileImage(),
					ivProfileImage);
		}
		retrieveReviews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void createMentorRequest(View view) {
		Log.d("DEBUG", "Requesting Mentor");
		ParseQuery<MentorRequest> query = ParseQuery.getQuery("MentorRequest");
		query.getInBackground(requestId, new GetCallback<MentorRequest>() {
			public void done(MentorRequest object, ParseException e) {
				if (e == null) {
					User currentUser = (User) ParseUser.getCurrentUser();
					if(currentUser.isMentor()) {
						MatchRelationship relationship = new MatchRelationship(object);
						relationship.setMentor(currentUser);
						relationship.saveInBackground();
						
					} else {
						object.addMentorToList(userObjId);
						object.saveInBackground();
					}
					Log.d("DEBUG", "ADDING MENTOR TO REQUEST: " + requestId);
					Intent i = new Intent(getBaseContext(), ConnectionsActivity.class);
					startActivity(i);
				} else {
					Log.d("DEBUG", "ERROR IN ADDING MENTOR TO REQUEST: "
							+ requestId);
					e.printStackTrace();
				}
			}
		});

	}

	private void retrieveReviews() {
		ParseQuery<MatchRelationship> mentorRating = ParseQuery
				.getQuery("MatchRelationship");
		mentorRating.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY,
				ParseUser.getCurrentUser());

		ParseQuery<MatchRelationship> menteeRating = ParseQuery
				.getQuery("MatchRelationship");
		menteeRating.whereEqualTo(MatchRelationship.MENTEE_USER_ID_KEY,
				ParseUser.getCurrentUser());
		List<ParseQuery<MatchRelationship>> queries = new ArrayList<ParseQuery<MatchRelationship>>();
		queries.add(mentorRating);
		queries.add(menteeRating);

		ParseQuery<MatchRelationship> mainQuery = ParseQuery.or(queries);
		mainQuery.include(MatchRelationship.MENTOR_USER_ID_KEY);
		mainQuery.include(MatchRelationship.MENTEE_USER_ID_KEY);
		mainQuery.findInBackground(new FindCallback<MatchRelationship>() {
			public void done(List<MatchRelationship> results, ParseException e) {
				double totalRating = 0.0;
				List<Review> reviews = new ArrayList<Review>();
				// int count;
				for (MatchRelationship relation : results) {
					Review newReview;
					if (relation.getMentee().equals(ParseUser.getCurrentUser())) {
						totalRating += relation.getMenteeRating();
						User mentor = (User) relation.getMentor();
						newReview = new Review(mentor.getFullName(), relation.getCreatedAt(), relation.getCommentForMentee(), relation.getMenteeRating());
						reviews.add(newReview);
					} else {
						totalRating += relation.getMentorRating();
						User mentee = (User) relation.getMentor();
						newReview = new Review(mentee.getFullName(), relation.getCreatedAt(), relation.getCommentForMentor(), relation.getMentorRating());
					}
					reviews.add(newReview);
				}
				double avgRating = totalRating / results.size();
				rbRating.setRating((float) avgRating);
				setupReviewView(reviews);
			}
		});
	}

	
	private void setupReviewView(List<Review> reviews) {
		vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), reviews);
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOnPageChangeListener(new OnPageChangeListener() {

        	
            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
            	Toast.makeText(getBaseContext(), "page selected: " + position, Toast.LENGTH_SHORT).show();
//                getSupportActionBar().setSelectedNavigationItem(position);
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            	Log.d("TEST", "onPageScrolled");
            }

            // Called when the scroll state changes: 
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            	Log.d("TEST", "state changed");
            }
        });		
	}
	
	public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
		private List<Review> reviewList;
		public MyPagerAdapter(FragmentManager fragmentManager, List<Review> list) {

			super(fragmentManager);
			reviewList = list;
		}

		// Returns total number of pages
		@Override
		public int getCount() {
			return reviewList.size();
		}

		// Returns the fragment to display for that page
		@Override
		public Fragment getItem(int position) {
			Log.d("DEBUG", "view profile: review adapter: " + position);
			return ReviewDetailFragment.newInstance(reviewList.get(position));
		}

		// Returns the page title for the top indicator
/*
		@Override
		public CharSequence getPageTitle(int position) {
			return "Page " + position;
		}
*/
	}

}

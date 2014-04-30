package com.codepath.mentormatch.adapters;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.core.MentorMatchApplication;
import com.codepath.mentormatch.helpers.ReviewsUtil;
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.FindCallback;
import com.parse.ParseException;

public class MatchResultsAdapter extends ArrayAdapter {

	//private static final int SWIPE_MIN_DISTANCE = 60;
    //private static final int SWIPE_THRESHOLD_VELOCITY = 100;
	private User user;
	private ViewHolder viewHolder;
	
    // View lookup cache
    private static class ViewHolder {
    	TextView tvName;
    	TextView tvJobTitle;
    	TextView tvLocation;
    	ImageView ivProfileImage;
    	RatingBar rbRating;
    	LinearLayout llSkillImages;
    	TextView tvNumReviews;    	
    }
    
	public MatchResultsAdapter(Context context, List objects) {
		super(context, 0, objects);
	}

	private void initializeUser(int position) {

		if(getItem(position) instanceof MentorRequest) {
			user = (User) ((MentorRequest)getItem(position)).getMentee();
		} else {
			user = (User) getItem(position);		
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		initializeUser(position);
//		ViewHolder viewHolder; // view lookup cache stored in tag
		Log.d("DEBUG", "Match results adapter: get view position: " + position + " user - " + user);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.match_result_item, null);	
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvJobTitle = (TextView) convertView.findViewById(R.id.tvJobTitle);
			viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
			viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			viewHolder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
			viewHolder.tvNumReviews = (TextView) convertView.findViewById(R.id.tvNumReviews);
			viewHolder.llSkillImages = (LinearLayout) convertView.findViewById(R.id.llSkillImages);
	        convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.llSkillImages.removeAllViews();
	    }
		Log.d("DEBUG", "Current user is: " + user.getObjectId() + " with profile image: " + user.getProfileImage());
		if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
			ImageLoader.getInstance().displayImage(user.getProfileImage(),
					viewHolder.ivProfileImage);
		} else {
			viewHolder.ivProfileImage.setImageResource(R.drawable.ic_profile_placeholder);
		}
		viewHolder.tvName.setText(user.getFullName());
		viewHolder.tvJobTitle.setText(user.getJobTitle() + " @ " + user.getCompany());
		viewHolder.tvLocation.setText(user.getLocation());
		Log.d("DEBUG", "Name: " + user.getFullName());
		retrieveReviews();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(65, 65);
		
		if(user.getSkills() != null) {
			for (String s : user.getSkills()) {
				Log.d("DEBUG", "SKill: " + s);
				ImageView iv = new ImageView(convertView.getContext());
				iv.setScaleType(ScaleType.FIT_XY);
				iv.setLayoutParams(params);
				Skill skill = Skill.fromValue(s);
				iv.setMaxHeight(15);
				iv.setMaxWidth(15);
				iv.setImageResource(skill.getResourceId());
				viewHolder.llSkillImages.addView(iv);				
			}
		}
		return convertView;
	}
	
	private void retrieveReviews() {
		HashMap<String, Object> reviewInfo = MentorMatchApplication.getUserReviews().get(user.getObjectId());
		double avg = 0.0;
		int numReviews = 0;
		if(reviewInfo != null) {
			avg = (Double) reviewInfo.get("average");
			numReviews = (Integer) reviewInfo.get("reviews");

		}
		viewHolder.rbRating.setRating((float)avg);
		
		viewHolder.tvNumReviews.setText(String.valueOf(numReviews));
	}
	
	
	/*		convertView.setOnClickListener(new OnClickListener() {		
	@Override
	public void onClick(View v) {
		Intent i = new Intent(v.getContext(), ViewProfileActivity.class);
		i.putExtra(ProfileSummaryListFragment.USER_EXTRA, user.getObjectId());
		v.getContext().startActivity(i);
	}
});
*/	
/*        final GestureDetector gdt = new GestureDetector(convertView.getContext(), new GestureListener());
convertView.setOnTouchListener(new OnTouchListener() {
    @Override
    public boolean onTouch(final View view, final MotionEvent event) {
        gdt.onTouchEvent(event);
        Log.d("DEBUG", "Touch");
        return true;
    }
});*/

	private class FindReviewsCallback extends FindCallback<MatchRelationship> {
		@Override
		public void done(List<MatchRelationship> relationshipList, ParseException e) {
			if(e == null) {
				Log.d("DEBUG", "relationship list: " + relationshipList.size());
				ReviewsUtil reviewHelper = new ReviewsUtil(relationshipList);
				reviewHelper.getReviews(user.isMentor());
				double avgRating = reviewHelper.getAverageRating();
				viewHolder.rbRating.setRating((float) avgRating);
				viewHolder.tvNumReviews.setText(reviewHelper.getTotalReviews() + " Reviews");
			} else {
				Log.d("DEBUG", "Error retrieving views for user: " + user.getObjectId());
				e.printStackTrace();
			}
		}
	}
}

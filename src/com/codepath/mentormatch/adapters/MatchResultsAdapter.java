package com.codepath.mentormatch.adapters;

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
import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.parse.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseUser;

public class MatchResultsAdapter extends ArrayAdapter<ParseUser> {

	//private static final int SWIPE_MIN_DISTANCE = 60;
    //private static final int SWIPE_THRESHOLD_VELOCITY = 100;
	private User user;
	
    // View lookup cache
    private static class ViewHolder {
    	TextView tvName;
    	TextView tvJobTitle;
    	TextView tvLocation;
    	ImageView ivProfileImage;
    	RatingBar rbRating;
    	LinearLayout llSkillImages;
    }
    
	public MatchResultsAdapter(Context context, List<ParseUser> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		user = (User) getItem(position);
		ViewHolder viewHolder; // view lookup cache stored in tag
		
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.match_result_item, null);	
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			viewHolder.tvJobTitle = (TextView) convertView.findViewById(R.id.tvJobTitle);
			viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
			viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
			viewHolder.rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
			viewHolder.llSkillImages = (LinearLayout) convertView.findViewById(R.id.llSkillImages);
	        convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.llSkillImages.removeAllViews();
	    }
		
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
		viewHolder.rbRating.setRating(getAverageRating());
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
		return convertView;
	}

	private float getAverageRating() {
		return (float)Math.random() * 5; 
	}
/*
    private class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Right to left
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d("DEBUG", "SWIPE RIGHT!!!");
                
                return false; // Left to right
            }

            if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Bottom to top
            }  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                return false; // Top to bottom
            }
            return false;
        }
    }
   */
}

package com.codepath.mentormatch.adapters;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.parse.ParseUser;

public class ProfileSummaryAdapter extends ArrayAdapter<ParseUser> {

	private User user;
	TextView tvName;
	TextView tvJobTitle;
	TextView tvLocation;
	ImageView ivProfileImage;

	public ProfileSummaryAdapter(Context context, List<ParseUser> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.profile_summary_item, null);
		}

		tvName = (TextView) view.findViewById(R.id.tvName);
		tvJobTitle = (TextView) view.findViewById(R.id.tvJobTitle);
		tvLocation = (TextView) view.findViewById(R.id.tvLocation);
		ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);

		user = (User) getItem(position);

		if (user.getProfileImage() != null && !user.getProfileImage().isEmpty()) {
			ImageLoader.getInstance().displayImage(user.getProfileImage(),
					ivProfileImage);
		} else {
			ivProfileImage.setImageResource(R.drawable.ic_profile_placeholder);
		}
		tvName.setText(user.getFullName());
		tvJobTitle.setText(user.getJobTitle() + " @ " + user.getCompany());
		tvLocation.setText(user.getLocation());
		
        final GestureDetector gdt = new GestureDetector(view.getContext(), new GestureListener());
        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                gdt.onTouchEvent(event);
                Log.d("DEBUG", "Touch");
                return true;
            }
        });
		return view;
	}

	private void getRatings() {

	}
    private static final int SWIPE_MIN_DISTANCE = 60;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;

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
}

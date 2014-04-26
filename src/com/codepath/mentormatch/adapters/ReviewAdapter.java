package com.codepath.mentormatch.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.Review;

public class ReviewAdapter extends ArrayAdapter<Review>{
	private Review review;
	private RatingBar rbRating;
	private TextView tvReviewContent;
	private TextView tvReviewer;
	private TextView tvReviewDate;
	
	public ReviewAdapter(Context context, List<Review> reviews) {
		super(context, 0, reviews);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		review = getItem(position);
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.review_item, null);	
			rbRating = (RatingBar) convertView.findViewById(R.id.rbRating);
			tvReviewContent = (TextView) convertView.findViewById(R.id.tvReviewContent);
			tvReviewer = (TextView) convertView.findViewById(R.id.tvReviewer);
			tvReviewDate = (TextView) convertView.findViewById(R.id.tvReviewDate);
		}
		
		rbRating.setRating((float)review.getRating());
		tvReviewContent.setText(review.getContent());
		tvReviewer.setText(review.getReviewer());
		tvReviewDate.setText(review.getReviewDate().toString());
		return convertView;
	}

}

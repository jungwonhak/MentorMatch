package com.codepath.mentormatch.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.Review;

public class ReviewDetailFragment extends Fragment{
	public static String REVIEW_ARG = "review";
	
	private Review review;
	private RatingBar rbRating;
	private TextView tvReviewContent;
	private TextView tvReviewer;
	private TextView tvReviewDate;
	
	public static ReviewDetailFragment newInstance(Review review) {
		ReviewDetailFragment fragment = new ReviewDetailFragment();
		Bundle args = new Bundle();
		args.putSerializable(REVIEW_ARG, review);		
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		return super.onCreateView(inflater, parent, savedInstanceState);
		View view = inflater.inflate(R.layout.review_item, null);	
		rbRating = (RatingBar) view.findViewById(R.id.rbRating);
		tvReviewContent = (TextView) view.findViewById(R.id.tvReviewContent);
		tvReviewer = (TextView) view.findViewById(R.id.tvReviewer);
		tvReviewDate = (TextView) view.findViewById(R.id.tvReviewDate);
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle args = getArguments();
		review = (Review)args.getSerializable(REVIEW_ARG);
		Log.d("DEBUG", review.getReviewer());
		rbRating.setRating((float)review.getRating());
		tvReviewContent.setText(review.getContent());
		tvReviewer.setText(review.getReviewer());
	}


}

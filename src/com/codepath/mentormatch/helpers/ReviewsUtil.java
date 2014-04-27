package com.codepath.mentormatch.helpers;

import java.util.ArrayList;
import java.util.List;

import com.codepath.mentormatch.models.Review;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.User;

public class ReviewsUtil {
	private int totalReviews = 0;
	private double ratingValueTotal = 0.0;
	private List<MatchRelationship> relationships;
	private List<Review> reviews;

	// double totalRating = 0.0;

	public ReviewsUtil() {
		relationships = new ArrayList<MatchRelationship>();
		reviews = new ArrayList<Review>();
	}

	public ReviewsUtil(List<MatchRelationship> valueList) {
		relationships = valueList;
		reviews = new ArrayList<Review>();
	}

	public void initializeReviews(boolean isForMentor) {
		for (MatchRelationship relation : relationships) {
			Review newReview;
			if (isForMentor) {

				User mentor = (User) relation.getMentor();
				double rating = relation.getMentorRating();
				String comment = relation.getCommentForMentor();
				if (rating > 0 || (comment != null && comment.length() > 0)) {
					newReview = new Review(mentor.getFullName(),
							relation.getCreatedAt(), comment, rating);
					reviews.add(newReview);
					ratingValueTotal += rating;
					totalReviews++;
				}
			} else {
				User mentee = (User) relation.getMentee();
				double rating = relation.getMenteeRating();
				String comment = relation.getCommentForMentee();
				if (rating > 0 || (comment != null && comment.length() > 0)) {
					newReview = new Review(mentee.getFullName(),
							relation.getCreatedAt(), comment, rating);
					reviews.add(newReview);
					ratingValueTotal += rating;
					totalReviews++;
				}
			}
		}
	}

	public double getAverageRating() {
		return ratingValueTotal/totalReviews;
	}

	public List<Review> getReviewsForMentor() {
		initializeReviews(true);
		return reviews;
	}

	public List<Review> getReviewsForMentee() {
		initializeReviews(false);
		return reviews;
	}
	
	public List<Review> getReviews(boolean isMentor) {
		reviews = new ArrayList<Review>();
		initializeReviews(isMentor);
		return reviews;
	}

	/*
	 * // int count; for (MatchRelationship relation : results) { Review
	 * newReview; if (relation.getMentee().equals(ParseUser.getCurrentUser())) {
	 * totalRating += relation.getMenteeRating(); User mentor = (User)
	 * relation.getMentor(); newReview = new Review(mentor.getFullName(),
	 * relation.getCreatedAt(), relation.getCommentForMentee(),
	 * relation.getMenteeRating()); reviews.add(newReview); } else { totalRating
	 * += relation.getMentorRating(); User mentee = (User) relation.getMentor();
	 * newReview = new Review(mentee.getFullName(), relation.getCreatedAt(),
	 * relation.getCommentForMentor(), relation.getMentorRating()); }
	 * reviews.add(newReview); }
	 */
}

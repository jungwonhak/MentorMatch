package com.codepath.mentormatch.models;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {

	private static final long serialVersionUID = 5929092685376328941L;
	private String reviewer;
	private Date reviewDate;
	private String reviewContent;
	private double rating;
	
	public Review() {
		
	}
	
	public Review(String name, Date date, String content, double reviewRating) {
		reviewer = name;
		reviewDate = date;
		reviewContent = content;
		rating = reviewRating;
	}
	
	public String getReviewer() {
		return reviewer;
	}
	
	public Date getReviewDate() {
		return reviewDate;
	}
	
	public String getContent() {
		return reviewContent;
	}
	
	public double getRating() {
		return rating;
	}
}

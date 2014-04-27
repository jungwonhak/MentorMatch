package com.codepath.mentormatch.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ParseQueries {
	
	public static void getUserById(String userObjId, GetCallback<ParseUser> callBack) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(userObjId, callBack); 
	}
	
	public static void findMentorsWithSkill(String skill, FindCallback<ParseUser> callBack) {
		String[] skillsList = { skill };
		ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("MentorRequest");
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo(User.IS_MENTOR_KEY, true);
		query.whereContainedIn(User.SKILLS_LIST_KEY, Arrays.asList(skillsList));

		query.findInBackground(callBack);		
	}
	
	public static void getRequestsForMentor(String mentorId, FindCallback<MentorRequest> callBack) {
		String [] mentorList = {mentorId};
		ParseQuery<MentorRequest> query = ParseQuery.getQuery("MentorRequest");
		query.whereContainedIn(MentorRequest.REQUESTED_MENTORS_RELATION_KEY, Arrays.asList(mentorList));
		query.findInBackground(callBack);
	}
	
	public static void findRelationshipsForUser(String userId, FindCallback<MatchRelationship> callBack) {
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY, ParseUser.getCurrentUser());
		query.include(MatchRelationship.MENTOR_REQUEST_KEY);
		query.include(MatchRelationship.MENTEE_USER_ID_KEY);
		query.findInBackground(callBack);
	}
	
	public static void getReviewsForUser(String userId, FindCallback<MatchRelationship> callBack) {
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
		mainQuery.findInBackground(callBack);
	}
	
	// Retrieve Relationships where current user is the Mentor
	// From ConnectionsActivity
	public static void retrieveConnections(FindCallback<MatchRelationship> callBack) {
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY, ParseUser.getCurrentUser());
		query.include(MatchRelationship.MENTOR_REQUEST_KEY);
		query.include(MatchRelationship.MENTEE_USER_ID_KEY);
		query.orderByDescending(MatchRelationship.CREATED_AT_KEY);
		query.findInBackground(callBack);
	}
		
	/*
	// Retrieve Request
	// Pending Request Activity - removed	 
	
	private void retrieveOpenRequests() {
		String [] mentorList = {ParseUser.getCurrentUser().getObjectId()};
		ParseQuery<ParseObject> query = ParseQuery.getQuery("MentorRequest");
		query.whereContainedIn(MentorRequest.REQUESTED_MENTORS_LIST_KEY, Arrays.asList(mentorList));
		query.include(MentorRequest.MENTEE_USER_ID_KEY);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> requestList, ParseException e) {
		        if (e == null) {
		            Log.d("DEBUG", "Retrieved " + requestList.size() + " REQUESTS");
		            for(ParseObject obj : requestList) {
//		            	requestAdapter.add((MentorRequest)obj);
		            }
//		            requestAdapter.notifyDataSetChanged();
		        } else {
		            Log.d("DEBUG", "Error: " + e.getMessage());
		        }
		    }
		});
	}

	
	// Retrieve Relationship for given request id
	// From Rating Activity
	public void retrieveRelationship(String requestId){
		ParseObject mentorReq = ParseObject.create("MentorRequest");
		mentorReq.setObjectId(requestId);
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_REQUEST_KEY, mentorReq);
		query.include(MatchRelationship.MENTEE_USER_ID_KEY);
		query.include(MatchRelationship.MENTOR_USER_ID_KEY);
		query.getFirstInBackground(new GetCallback<MatchRelationship>() {
			
			@Override
			public void done(MatchRelationship arg0, ParseException e) {
				if(e == null) {
/*					
					relationship = arg0;
					mentee = (User) relationship.getMentee();
					mentor = (User) relationship.getMentor();
					populateExistingData();
					btnSave.setEnabled(true);
					
					retrieveTasks();
					
				} else {
					e.printStackTrace();
				}
				
			}
		});
	}

	// Retrieve User from Profile Detail Activity (View Profile Activity)
	private void getUser(String userObjId) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(userObjId, new GetCallback<ParseUser>() {
			public void done(ParseUser object, ParseException e) {
				if (e == null) {
					Log.d("DEBUG", "found user");
//					user = (User) object;
//					setViews();
				} else {
					// something went wrong
					Log.d("DEBUG",
							"ERROR - PROFILE ACTIVITY - COULD NOT FIND USER");
					e.printStackTrace();
				}
			}
		});
	}

	// Retrieve Reviews for Detail Profile - retrieves reviews for whether current user is a mentor or a mentee
	// In View Profile Activity
	// Need to make generic to retrieve for any user
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
//				rbRating.setRating((float) avgRating);
//				setupReviewView(reviews);
			}
		});
	}

	
*/
}

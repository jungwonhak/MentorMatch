package com.codepath.mentormatch.helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;

import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.Status;
import com.codepath.mentormatch.models.parse.MatchRelationship;
import com.codepath.mentormatch.models.parse.MentorRequest;
import com.codepath.mentormatch.models.parse.User;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseQueries {

	public static void getUserById(String userObjId,
			GetCallback<ParseUser> callBack) {
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.getInBackground(userObjId, callBack);
	}

	public static void findMentorsWithSkill(String skill, MentorRequest request, FindCallback<ParseUser> callBack) {
		String[] skillsList = { skill };
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo(User.IS_MENTOR_KEY, true);
		query.whereContainedIn(User.SKILLS_LIST_KEY, Arrays.asList(skillsList));
		List<String> mentorList = new ArrayList<String>();
		if(request.getMentorList() != null) {
			mentorList = request.getMentorList();
		}
		query.whereNotContainedIn("objectId", mentorList);
		query.findInBackground(callBack);

	}

	public static void getMenteesLookingForMySkills(List<String> mentorSkills,
			FindCallback<MentorRequest> callBack) {

		ParseQuery<MentorRequest> query = ParseQuery.getQuery("MentorRequest");
		query.whereContainedIn(MentorRequest.SKILL_KEY, mentorSkills);
		query.whereNotEqualTo(MentorRequest.STATUS_KEY, Status.CLOSED.toString());
		query.findInBackground(callBack);
	}
	
	public static void getRequestsForMentor(String mentorId,
			FindCallback<MentorRequest> callBack) {
		String[] mentorList = { mentorId };
		ParseQuery<MentorRequest> query = ParseQuery.getQuery("MentorRequest");
		query.whereContainedIn(MentorRequest.REQUESTED_MENTORS_RELATION_KEY,
				Arrays.asList(mentorList));
		query.whereNotEqualTo(MentorRequest.STATUS_KEY, Status.CLOSED.toString());
		query.findInBackground(callBack);
	}

	public static void findRelationshipsForUser(
			FindCallback<MatchRelationship> callBack) {
		ParseQuery<MatchRelationship> menteeRelationship = ParseQuery.getQuery("MatchRelationship");
		menteeRelationship.whereEqualTo(MatchRelationship.MENTEE_USER_ID_KEY, ParseUser.getCurrentUser());
		
		ParseQuery<MatchRelationship> mentorRelationship = ParseQuery
				.getQuery("MatchRelationship");
		mentorRelationship.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY,
				ParseUser.getCurrentUser());

		List<ParseQuery<MatchRelationship>> queries = new ArrayList<ParseQuery<MatchRelationship>>();
		queries.add(menteeRelationship);
		queries.add(mentorRelationship);

		ParseQuery<MatchRelationship> mainQuery = ParseQuery.or(queries);
		mainQuery.include(MatchRelationship.MENTOR_USER_ID_KEY);
		mainQuery.include(MatchRelationship.MENTEE_USER_ID_KEY);
		mainQuery.findInBackground(callBack);
		
	}
	
	public static void getRelationshipById(String objId, GetCallback<MatchRelationship> callBack) {
		ParseQuery<MatchRelationship> query = ParseQuery.getQuery("MatchRelationship");
		try {
			query.getInBackground(objId, callBack);
		} catch(Exception e) {
			Log.d("DEBUG", "Error retrieving match relationship by obj id: " + objId);
			e.printStackTrace();
		}
	}

	public static void getReviewsForUser(String userId,
			FindCallback<MatchRelationship> callBack) {
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
		mainQuery.findInBackground(callBack);
	}

	// Retrieve Relationships where current user is the Mentor or mentee
	// From ConnectionsActivity
	public static void findConnectionsForCurrentUser(
			FindCallback<MatchRelationship> callBack) {
		ParseQuery<MatchRelationship> query = ParseQuery
				.getQuery("MatchRelationship");
		query.whereEqualTo(MatchRelationship.MENTOR_USER_ID_KEY,
				ParseUser.getCurrentUser());

		ParseQuery<MatchRelationship> mentorRelationship = ParseQuery
				.getQuery("MatchRelationship");
		mentorRelationship.whereEqualTo(MatchRelationship.MENTEE_USER_ID_KEY,
				ParseUser.getCurrentUser());

		List<ParseQuery<MatchRelationship>> queries = new ArrayList<ParseQuery<MatchRelationship>>();
		queries.add(query);
		queries.add(mentorRelationship);

		ParseQuery<MatchRelationship> mainQuery = ParseQuery.or(queries);
		mainQuery.orderByDescending(MatchRelationship.CREATED_AT_KEY);
		mainQuery.findInBackground(callBack);
	}

}

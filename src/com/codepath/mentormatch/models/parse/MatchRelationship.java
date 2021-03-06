package com.codepath.mentormatch.models.parse;

import java.io.Serializable;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("MatchRelationship")
public class MatchRelationship extends ParseObject implements Serializable {

	private static final long serialVersionUID = -6017240453324687968L;

	public static final String MENTEE_USER_ID_KEY = "menteeId";
	public static final String MENTOR_USER_ID_KEY = "mentorId";
	public static final String MENTOR_RATING_KEY = "mentorRating";
	public static final String MENTEE_RATING_KEY = "menteeRating";
	public static final String MENTOR_COMMENT_KEY = "commentForMentor";
	public static final String MENTEE_COMMENT_KEY = "commentForMentee";
	public static final String MENTOR_REQUEST_KEY = "mentorRequestId";
	public static final String CREATED_AT_KEY = "createdAt";

	public MatchRelationship() {
		super();
	}

	public MatchRelationship(MentorRequest request) {
		super();
		setMentee(request.getMentee());
		setMentorRequestId(request);
	}

	public ParseUser getMentee() {
		try {
			return getParseUser(MENTEE_USER_ID_KEY).fetchIfNeeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getParseUser(MENTEE_USER_ID_KEY);
	}

	public void setMentee(ParseUser user) {
		put(MENTEE_USER_ID_KEY, user);
	}

	public ParseUser getMentor() {
		try {
			return getParseUser(MENTOR_USER_ID_KEY).fetchIfNeeded();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getParseUser(MENTOR_USER_ID_KEY);
	}

	public void setMentor(ParseUser user) {
		put(MENTOR_USER_ID_KEY, user);
	}

	public double getMentorRating() {
		return getDouble(MENTOR_RATING_KEY);
	}

	public void setMentorRating(double rating) {
		put(MENTOR_RATING_KEY, rating);
	}

	public double getMenteeRating() {
		return getDouble(MENTEE_RATING_KEY);
	}

	public void setMenteeRating(double rating) {
		put(MENTEE_RATING_KEY, rating);
	}

	public String getCommentForMentor() {
		return getString(MENTOR_COMMENT_KEY);
	}

	public void setCommentForMentor(String comment) {
		put(MENTOR_COMMENT_KEY, comment);
	}

	public String getCommentForMentee() {
		return getString(MENTEE_COMMENT_KEY);
	}

	public void setCommentForMentee(String comment) {
		put(MENTEE_COMMENT_KEY, comment);
	}

	public void setMentorRequestId(MentorRequest requestId) {
		put(MENTOR_REQUEST_KEY, requestId);
	}

	public MentorRequest getMentorRequestId() {
		try {
			return (MentorRequest) getParseObject(MENTOR_REQUEST_KEY).fetchIfNeeded();
		} catch (Exception e) {
			Log.d("DEBUG", "Error fetching mentor request from relationship");
			e.printStackTrace();
		}
		return null;
	}

}

package com.codepath.mentormatch.models.parse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.codepath.mentormatch.models.Skill;
import com.codepath.mentormatch.models.Status;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("MentorRequest")
public class MentorRequest extends ParseObject implements Serializable {

	private static final long serialVersionUID = -2947441978843755640L;

	public static final String MENTEE_USER_ID_KEY = "menteeId";
	// public static final String MENTOR_USER_ID_KEY = "mentorId";
	public static final String SKILL_KEY = "skill";
	public static final String DESCRIPTION_KEY = "description";
	public static final String STATUS_KEY = "status";
	public static final String REQUESTED_MENTORS_RELATION_KEY = "requestedMentors";
	public static final String REJECTED_MENTORS_RELATION_KEY = "rejectedMentors";

	public MentorRequest() {
		super();
	}

	public MentorRequest(ParseUser mentee, Skill skill) {
		setMentee(mentee);
		setSkill(skill);
	}

	public ParseUser getMentee() {
		try {
			return getParseUser(MENTEE_USER_ID_KEY).fetchIfNeeded();
		} catch (Exception e) {
			e.printStackTrace();
//			return getParseUser(MENTEE_USER_ID_KEY).fetchIfNeeded();
		}
		return null;
	}

	public void setMentee(ParseUser user) {
		put(MENTEE_USER_ID_KEY, user);
	}

	public List<String> getMentorList() {
		return getList(REQUESTED_MENTORS_RELATION_KEY);
	}

	public void setMentorList(List<String> value) {
		put(REQUESTED_MENTORS_RELATION_KEY, value);
	}

	public List<String> getRejectedMentorList() {
		return getList(REJECTED_MENTORS_RELATION_KEY);
	}

	public void setRejectedMentorList(List<String> value) {
		put(REJECTED_MENTORS_RELATION_KEY, value);
	}

	public void addMentorToList(String objId) {
		List<String> mentorList = getMentorList();
		if (mentorList == null) {
			mentorList = new ArrayList<String>();
		}
		if (mentorList.contains(objId)) {
			return;
		}

		mentorList.add(objId);
		setMentorList(mentorList);
	}

	public void addRejectedMentorToList(String objId) {
		List<String> mentorList = getRejectedMentorList();
		if (mentorList == null) {
			mentorList = new ArrayList<String>();
		}
		if (mentorList.contains(objId)) {
			return;
		}

		mentorList.add(objId);
		setRejectedMentorList(mentorList);
	}

	public ParseRelation<ParseUser> getRejectedMentorRelation() {
		return getRelation(REJECTED_MENTORS_RELATION_KEY);
	}

	public void addRejectedMentorToRelation(ParseUser objId) {
		// List<String> mentorList = getMentorList();
		try {
			ParseRelation<ParseUser> relation = getRejectedMentorRelation();
			ParseQuery<ParseUser> query = relation.getQuery();
			for (ParseUser user : query.find()) {
				if (user.equals(objId)) {
					return;
				}
			}

			relation.add(objId);
			this.save();
		} catch (Exception e) {
			Log.d("DEBUG", "ERROR - getting Requested Mentors List");
			e.printStackTrace();
		}
	}

	public ParseRelation<ParseUser> getMentorRelation() {
		return getRelation(REQUESTED_MENTORS_RELATION_KEY);
	}

	public void addMentorToRelation(ParseUser objId) {
		// List<String> mentorList = getMentorList();
		try {
			ParseRelation<ParseUser> relation = getMentorRelation();
			ParseQuery<ParseUser> query = relation.getQuery();
			for (ParseUser user : query.find()) {
				if (user.equals(objId)) {
					return;
				}
			}

			relation.add(objId);
			this.save();
		} catch (Exception e) {
			Log.d("DEBUG", "ERROR - getting Requested Mentors List");
			e.printStackTrace();
		}
	}

	/*
	 * public ParseUser getMentor() { return
	 * getParseUser(REQUESTED_MENTORS_LIST_KEY); }
	 * 
	 * public void setMentor(ParseUser user) { put(REQUESTED_MENTORS_LIST_KEY,
	 * user); }
	 */
	public String getSkill() {
		return getString(SKILL_KEY);
	}

	public void setSkill(Skill skill) {
		put(SKILL_KEY, skill.toString());
	}
	
	public void setStatus(Status status) {
		put(STATUS_KEY, status.toString());
	}

	public String getStatus() {
		return getString(STATUS_KEY);
	}

	public String getDescription() {
		return getString(DESCRIPTION_KEY);
	}

	public void setDescription(String message) {
		put(DESCRIPTION_KEY, message);
	}

}

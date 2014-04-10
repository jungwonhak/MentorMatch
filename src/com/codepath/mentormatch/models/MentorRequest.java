package com.codepath.mentormatch.models;

import java.io.Serializable;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("MentorRequest")
public class MentorRequest extends ParseObject implements Serializable {

	private static final long serialVersionUID = -2947441978843755640L;

	public static final String MENTEE_USER_ID_KEY = "menteeId";
	public static final String MENTOR_USER_ID_KEY = "mentorId";
	public static final String SKILL_KEY = "skill";
	public static final String STATUS_KEY = "status";

	public MentorRequest() {
		super();
	}

	public MentorRequest(ParseUser mentee, String skill) {
		setMentee(mentee);
		setSkill(skill);
	}

	public ParseUser getMentee() {
		return getParseUser(MENTEE_USER_ID_KEY);
	}

	public void setMentee(ParseUser user) {
		put(MENTEE_USER_ID_KEY, user);
	}

	public ParseUser getMentor() {
		return getParseUser(MENTOR_USER_ID_KEY);
	}

	public void setMentor(ParseUser user) {
		put(MENTOR_USER_ID_KEY, user);
	}

	public String getSkill() {
		return getString(SKILL_KEY);
	}

	public void setSkill(String skill) {
		put(SKILL_KEY, skill);
	}

	public String getStatus() {
		return getString(STATUS_KEY);
	}

}

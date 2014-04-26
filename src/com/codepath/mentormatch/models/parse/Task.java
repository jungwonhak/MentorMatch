package com.codepath.mentormatch.models.parse;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Task")
public class Task extends ParseObject {
	public static final String RELATIONSHIP_KEY = "relationshipId";
	public static final String DESCRIPTION_KEY = "description";
	public static final String DUE_DATE_KEY = "dueDate";
	public static final String MENTEE_RATING_KEY = "menteeRating";
	public static final String STATUS_KEY = "status";

	public Task() {
		super();
	}
	
	public String getStatus() {
		return getString(STATUS_KEY);
	}
	
	public void setStatus(String value) {
		put(STATUS_KEY, value);
	}
	
	public String getDescription() {
		return getString(DESCRIPTION_KEY);
	}	
	
	public void setDescription(String comment) {
		put(DESCRIPTION_KEY, comment);
	}
	
	public Date getDueDate() {
		return getDate(DUE_DATE_KEY);
	}
	
	public void setDueDate(Date date) {
		put(DUE_DATE_KEY, date);
	}
	
	public void setMatchRelationshipId(MatchRelationship requestId) {
		put(RELATIONSHIP_KEY, requestId);
	}
	
	public MatchRelationship getMatchRelationshipId() {
		return (MatchRelationship) getParseObject(RELATIONSHIP_KEY);
	}

	
}

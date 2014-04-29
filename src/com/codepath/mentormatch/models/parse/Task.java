package com.codepath.mentormatch.models.parse;

import java.util.Date;

import android.text.format.DateUtils;
import android.util.Log;

import com.codepath.mentormatch.models.Status;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Task")
public class Task extends ParseObject {
	public static final String RELATIONSHIP_KEY = "relationshipId";
	public static final String DESCRIPTION_KEY = "description";
	public static final String DUE_DATE_KEY = "dueDate";
	public static final String CREATED_BY_KEY = "createdBy";
	public static final String STATUS_KEY = "status";

	public Task() {
		super();
	}

	public Status getStatus() {
		return Status.fromString(getString(STATUS_KEY));
	}

	public void setStatus(Status value) {
		put(STATUS_KEY, value.toString());
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

	public void setCreatedBy(ParseUser user) {
		put(CREATED_BY_KEY, user);
	}

	public User getCreatedBy() {
		try {
			return (User) getParseUser(CREATED_BY_KEY).fetchIfNeeded();
		} catch (Exception e) {
			Log.d("DEBUG", "Error retrieving created by user");
			e.printStackTrace();
		}
		return null;
	}

	public Date getLocalizedCreateDate() {
		Date date = getCreatedAt();
		if(date == null) {
			date = new Date();
		}

return date;
}
}

package com.codepath.mentormatch.models;

import java.util.Date;
import java.util.List;


public class User {
	private long id;
	private String email;
	private String name;
	private String location;
	private String description;
	private String jobTitle;
	private String company;
	private long yearsOfExperience;
	private String linkedInUrl;
	private String githubUrl;
	private String profileImageUrl;
	private Date creationDate;
	private Date lastAccessDate;
	private String accessToken;
	private List<Skill> skills;
	
}

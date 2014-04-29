package com.codepath.mentormatch.activities;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.core.MentorMatchApplication;
import com.codepath.mentormatch.helpers.LinkedInClient;
import com.codepath.mentormatch.models.linkedin.LinkedInUser;
import com.codepath.mentormatch.models.parse.User;
import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends OAuthLoginActivity<LinkedInClient> {
	public static final String LOGIN_EXTRA = "foo";
	private EditText etEmail;
	private EditText etPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		// Create map of user reviews
		MentorMatchApplication.retrieveUserReviews();
	}
	// Click listener for LinkedIn connect button
	public void handleLinkedInSignUp(View view) {
		getClient().connect();
	}
	
	// Click listener for SignUp button
	public void handleRegularSignUp(View v) {
		createRegularUser();
	}

	// Temporary way of showing failures
	private void userLoginOrSignupFailure(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}
	
	// Once we have a successful login
	private void userLoginSuccess() {
		final Intent i = new Intent(this, MatchResultsActivity.class);
		// Temporary - Need to decide where to drop user on log in.  Thinking of going to mentorMatch activity, but would need to
		// pull skill from request in parse
		//i.putExtra(LOGIN_EXTRA, "details");
		startActivity(i);
	}

	// Once we have a successful initial signup
	private void userSignupSuccess() {
		final Intent i = new Intent(this, ProfileBuilderActivity.class);
		i.putExtra(LOGIN_EXTRA, "login");
		startActivity(i);		
	}
	
	// Basic check for now
	private boolean validateUserNameAndPassword(String userName, String password) {
		if(userName.length() > 0 && password.length() > 0) {
			return true;
		}
		return false;
	}
	
	private void createRegularUser() {
		String email = etEmail.getText().toString();
		String password = etPassword.getText().toString();
		
		if(!validateUserNameAndPassword(email, password)) {
			userLoginOrSignupFailure("Invalid username and password");
			return;			
		}
		User u = User.getUserIfExists(email);
		if (u != null) {
			userLoginOrSignupFailure("Regular user already exists with that email");
			return;
		}
		
		User parseUser = User.fromRegularUser(email, password);

		parseUser.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					Log.d("DEBUG", "SUCCESS!!!!");
					userSignupSuccess();
				} else {
					e.printStackTrace();
					Log.d("DEBUG", "Failed");
					userLoginOrSignupFailure("Parse user signup did not succeed for regular user");
				}
			}
		});
	}

	// LinkedIn auth failure
	@Override
	public void onLoginFailure(Exception e) {
		Log.d("DEBUG", "Failure logging in through LinkedIn");
		userLoginOrSignupFailure("Unable to authenticate with LinkedIn");
		e.printStackTrace();
	}

	// LinkedIn auth success - fetch profile data
	@Override
	public void onLoginSuccess() {
		final LinkedInClient linkedInClient = MentorMatchApplication.getRestClient();

		linkedInClient.getUserInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				LinkedInUser u = LinkedInUser.fromJson(jsonUser);
				if (u == null) {
					userLoginOrSignupFailure("Unable to parse LinkedIn data");
					return;
				}
				u.setAccessToken(linkedInClient.checkAccessToken());

				User parseUser = User.getUserIfExists(u.getProfileId());

				// Existing LinkedIn user - log in
				if (parseUser != null) {
					ParseUser.logInInBackground(u.getProfileId(), u.getAccessToken(), new LogInCallback() {
						  public void done(ParseUser user, ParseException e) {
						    if (user != null) {
								userLoginSuccess();
						    } else {
						    	e.printStackTrace();
						    	userLoginOrSignupFailure("Unable to log in existing LinkedIn user");
						    }
						  }
						});
					
				} else { // New LinkedIn user
					parseUser = User.fromLinkedInUser(u);
					parseUser.signUpInBackground(new SignUpCallback() {
						public void done(ParseException e) {
							if (e == null) {
								userSignupSuccess();
							} else {
								e.printStackTrace();
								userLoginOrSignupFailure("Unable to sign up LinkedIn parse user");
							}
						}
					});
				}
			}

			@Override
			public void onFailure(Throwable e) {
				Log.d("DEBUG", "FAILED: " + e.getMessage());
				userLoginOrSignupFailure("LinkedIn REST call for profile data did not succeed");
				e.printStackTrace();
			}

			@Override
			protected void handleFailureMessage(Throwable e, String responseBody) {
				Log.d("DEBUG", "FAILED: " + responseBody);
				userLoginOrSignupFailure("LinkedIn REST call for profile data did not succeed");
				e.printStackTrace();
			}
		});
	}
}

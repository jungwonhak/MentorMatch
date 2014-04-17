package com.codepath.mentormatch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.helpers.LinkedInClient;
import com.codepath.oauth.OAuthLoginActivity;

public class LoginActivity extends OAuthLoginActivity<LinkedInClient> {
    public static final String LOGIN_EXTRA = "foo";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLoginSuccess() {
		Log.d("DEBUG", "Logged in successfully");
        Intent i = new Intent(this, Profile_Builder.class);
		i.putExtra(LOGIN_EXTRA, "login");
        startActivity(i);
		
	}

	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
		
	}

	  public void loginToRest(View view) {
	      getClient().connect();
	  }
}

package com.codepath.mentormatch.activities;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.R.layout;
import com.codepath.mentormatch.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ConnectionsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connections);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connections, menu);
		return true;
	}

}

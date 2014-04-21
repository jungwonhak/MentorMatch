package com.codepath.mentormatch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.models.MatchRelationship;

public class RatingActivity extends Activity {
	public static final String RELATIONSHIP_ID_EXTRA = "relationshipId";

	private MatchRelationship relationship;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rating, menu);
		return true;
	}

}

package com.codepath.mentormatch.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.widget.ListView;

import com.codepath.mentormatch.R;
import com.codepath.mentormatch.adapters.TasksAdapter;
import com.codepath.mentormatch.fragments.GoalsFragment;
import com.codepath.mentormatch.fragments.RatingsFragment;
import com.codepath.mentormatch.helpers.FragmentTabListener;
import com.codepath.mentormatch.models.parse.Task;

public class ConnectionDetailActivity extends FragmentActivity {
	public static final String RELATIONSHIP_ID_EXTRA = "relationshipId";

	
	private ViewPager vpPager;
	private FragmentPagerAdapter adapterViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_detail);
/*
		vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new ConnectionDetailPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setOnPageChangeListener(new OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
//                getSupportActionBar().setSelectedNavigationItem(position);
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            	Log.d("TEST", "onPageScrolled");
            }

            // Called when the scroll state changes: 
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            	Log.d("TEST", "state changed");
            }
        });
        */
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.connection_detail, menu);
		return true;
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Ratings")
			.setIcon(R.drawable.ic_home)
			.setTag("RatingsFragment")
			.setTabListener(
				new FragmentTabListener<RatingsFragment>(R.id.flFrameContainer, this, "ratings",
						RatingsFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Goals")
			.setIcon(R.drawable.ic_action_search)
			.setTag("GoalsFragment")
			.setTabListener(
			    new FragmentTabListener<GoalsFragment>(R.id.flFrameContainer, this, "goals",
			    		GoalsFragment.class));

		actionBar.addTab(tab2);
	}

	private static class ConnectionDetailPagerAdapter extends FragmentPagerAdapter {
		private static int NUM_ITEMS = 2;

	    public ConnectionDetailPagerAdapter(FragmentManager fragmentManager) {
	    	super(fragmentManager);
	    }

	    @Override
	    public int getCount() {
	    	return NUM_ITEMS;
	    }

	    @Override
	    public Fragment getItem(int position) {
	    	switch (position) {
	            case 0:
	            	return RatingsFragment.newInstance();
	            case 1: 
	            	return RatingsFragment.newInstance();
	            default:
	                return null;
	    	}
	    }
	}
}

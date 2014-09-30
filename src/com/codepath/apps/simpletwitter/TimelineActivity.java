package com.codepath.apps.simpletwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.simpletwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.simpletwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.simpletwitter.fragments.TweetsListFragment;
import com.codepath.apps.simpletwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupTabs();
		Log.d("simple_twitter", "hello world");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.twitterlist, menu);
		return true;
	}
	
	public void onComposeAction(MenuItem mi){
		Intent i  = new Intent(this, ComposeActivity.class);
		this.startActivityForResult(i, 1776);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode== RESULT_OK && requestCode==1776){
			long id = data.getLongExtra("id", Long.MAX_VALUE);
			TweetsListFragment frag = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.flContainer);
			frag.resetList(id);
		}
	}
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.ic_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this, "Home",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Second")
			.setIcon(R.drawable.ic_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this, "Mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
	
	public void onProfileView(MenuItem mi){
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);
	}

}

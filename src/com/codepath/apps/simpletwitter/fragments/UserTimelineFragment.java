package com.codepath.apps.simpletwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	String screenName;
	
	public static UserTimelineFragment newInstance(String screenName){
		UserTimelineFragment frag = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putString("screenName", screenName);
		frag.setArguments(args);
		return frag;
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screenName = getArguments().getString("screenName");
	}
	
	@Override
	protected void populateTimeline(long maxID){
		Log.d("click", "lalalala");
		client.getUserTimeline(maxID, screenName, handler);
	}
}

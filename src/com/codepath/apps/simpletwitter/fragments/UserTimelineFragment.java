package com.codepath.apps.simpletwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void populateTimeline(long maxID){
		client.getUserTimeline(maxID, handler);
	}
}

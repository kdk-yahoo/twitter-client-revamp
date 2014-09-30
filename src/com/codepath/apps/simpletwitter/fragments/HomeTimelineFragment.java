package com.codepath.apps.simpletwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.simpletwitter.R;
import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimelineFragment extends TweetsListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void populateTimeline(long maxID){
		client.getHomeTimeline(maxID, handler);
	}
}

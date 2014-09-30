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
		screenName = getArguments().getString("screenName");
		super.onCreate(savedInstanceState);
		Log.d("click", "onCreate");
		populateTimeline(Long.MAX_VALUE);
	}
	
	@Override
	protected void populateTimeline(long maxID){
		if(screenName == null){
			Log.d("popTimeLine", "null");
		}else{
			Log.d("popTimeLine", screenName);
		}
		client.getUserTimeline(maxID, screenName, handler);
	}
}

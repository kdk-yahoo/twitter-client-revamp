package com.codepath.apps.simpletwitter;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		Log.d("simple_twitter", "hello world");
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(this,tweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener(){

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				long maxID = tweets.get(totalItemsCount-1).getUid()-1;
				populateTimeline(maxID);
			}
			
		});
		populateTimeline(Long.MAX_VALUE);

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
			aTweets.clear();
			populateTimeline(id);
		}
	}
	
	private void populateTimeline(long maxID){
		client.getHomeTimeline(maxID, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("simple_twitter", json.toString());
				aTweets.addAll(Tweet.fromJSONArray(json));
			}
			
			
			@Override
			public void onFailure(Throwable e, String s){
				Log.d("Simple_twitter", "population did not work");
				Log.d("simple_twitter", s, e);
			}
		});
	}
}

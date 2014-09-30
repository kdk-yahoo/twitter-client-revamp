package com.codepath.apps.simpletwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.simpletwitter.EndlessScrollListener;
import com.codepath.apps.simpletwitter.R;
import com.codepath.apps.simpletwitter.TweetArrayAdapter;
import com.codepath.apps.simpletwitter.TwitterApplication;
import com.codepath.apps.simpletwitter.TwitterClient;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetsListFragment extends Fragment {

	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	protected TwitterClient client;
	protected JsonHttpResponseHandler handler;

	private OnItemClickListener listener;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(),tweets);
		client = TwitterApplication.getRestClient();
		handler = new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("simple_twitter", json.toString());
				addAll(Tweet.fromJSONArray(json));
			}
			
			
			@Override
			public void onFailure(Throwable e, String s){
				Log.d("Simple_twitter", "population did not work");
				Log.d("simple_twitter", s, e);
			}
		};
		populateTimeline(Long.MAX_VALUE);
	}
	

	public interface OnItemClickListener{
		public void onTweetClicked(String screenName);
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener(){

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				long maxID = tweets.get(totalItemsCount-1).getUid()-1;
				populateTimeline(maxID);
			}
			
		});

		setListener();
		return v;
	}

	private void setListener() {
		lvTweets.setOnItemClickListener( new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("click", "click");
				listener.onTweetClicked(tweets.get(position).getUser().getScreenName());
				
			}
		});
		
	}

	public void onAttach(Activity activity){
		super.onAttach(activity);
		if(activity instanceof OnItemClickListener){
			listener = (OnItemClickListener) activity;
		}else{
			throw new ClassCastException(activity.toString() + " must implement TweetListFragment.OnItemClickListener");
		}
	}
	
	
	public void addAll(ArrayList<Tweet> tweets){
		aTweets.addAll(tweets);
	}
	
	abstract protected void populateTimeline(long maxID);


	public void resetList(long id){
		aTweets.clear();
		populateTimeline(id);
	}
}



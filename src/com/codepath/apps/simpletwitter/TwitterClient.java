package com.codepath.apps.simpletwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "6A0Pw1IikOVFtJgYvysw0R3XO";       // Change this
	public static final String REST_CONSUMER_SECRET = "kd81uS4KJ9AeF7ih9Ai07me03BmQ5A26UwKSrPFgQtzmBgXuwe"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getHomeTimeline(long maxID, AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		if(maxID != Long.MAX_VALUE){ 
			params.put("max_id", Long.toString(maxID));
		}
		params.put("count", "50");
		params.put("since_id","1");
		client.get(apiURL,params, handler);
	}
	
	public void getMentionsTimeline(long maxID, AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		if(maxID != Long.MAX_VALUE){ 
			params.put("max_id", Long.toString(maxID));
		}
		params.put("count", "50");
		params.put("since_id","1");
		client.get(apiURL,params, handler);
	}
	
	public void getMyInfo(AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl("account/verify_credentials.json");
		client.get(apiURL, null, handler);
	}
	
	public void getUserTimeline(long maxID, String screenName, AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		if(maxID != Long.MAX_VALUE){ 
			params.put("max_id", Long.toString(maxID));
		}
		if(screenName != null){
			Log.d("click", screenName);
			params.put("screen_name", screenName);
		}
		Log.d("click", "screen_name null");
		params.put("count", "50");
		params.put("since_id","1");
		client.get(apiURL,params, handler);
	}
	
	public void postUpdate(String status, AsyncHttpResponseHandler handler){
		String apiURL = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", status);
		client.post(apiURL, params, handler);
	}

	public void getInfo(String screenName, JsonHttpResponseHandler handler) {
		String apiURL = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenName);
		client.get(apiURL,params, handler);
	}
	


	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}*/

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}
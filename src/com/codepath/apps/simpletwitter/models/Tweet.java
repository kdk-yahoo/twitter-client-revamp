package com.codepath.apps.simpletwitter.models;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

public class Tweet {
	private String body;
	private long uid;
	private long createdAt;
	private User user;

	public static Tweet fromJSON(JSONObject json){
		Tweet tweet = new Tweet();
		try{
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			tweet.createdAt = getTwitterDate(json.getString("created_at"));
			tweet.user = User.fromJson(json.getJSONObject("user"));

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return tweet;
	}


	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public User getUser() {
		return user;
	}


	public static ArrayList<Tweet> fromJSONArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());

		for(int i = 0; i < json.length(); i++){
			JSONObject tweetJson = null;
			try{
				tweetJson = json.getJSONObject(i);
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJSON(tweetJson);
			if(tweet!= null){
				tweets.add(tweet);
			}
		}
		return tweets;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getBody();
	}

	private static long getTwitterDate(String date) throws ParseException  {

		final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(TWITTER, Locale.ENGLISH);
		sf.setLenient(true);
		return sf.parse(date).getTime();
	}
	
	public static String getTwitterReldate(long date){
		String ret = DateUtils.getRelativeTimeSpanString(date, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
		String [] strings = ret.split(" ");
		if(strings.length == 3){
			if(strings[0].equals("in")){
				return "0s";
			}
			return strings[0].concat(Character.toString((strings[1].charAt(0))));
		}else{
			return ret;
		}
	}
}

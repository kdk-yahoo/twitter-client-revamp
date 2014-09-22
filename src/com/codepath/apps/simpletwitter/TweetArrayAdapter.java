package com.codepath.apps.simpletwitter;

import java.sql.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context,0, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet  tweet = getItem(position);
		
		View v;
		if(convertView == null){
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.tweet_item,parent, false);
		}else{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView) v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView) v.findViewById(R.id.tvBody);
		TextView tvName = (TextView) v.findViewById(R.id.tvName);
		TextView tvRelativeTime = (TextView) v.findViewById(R.id.tvRelativeTime);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText("@".concat(tweet.getUser().getScreenName()));
		tvName.setText(tweet.getUser().getName());
		tvBody.setText(tweet.getBody());
		tvRelativeTime.setText(Tweet.getTwitterReldate(tweet.getCreatedAt()));
		
		return v;
		
	}

}

package com.codepath.apps.simpletwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletwitter.fragments.TweetsListFragment.OnItemClickListener;
import com.codepath.apps.simpletwitter.fragments.UserTimelineFragment;
import com.codepath.apps.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity implements OnItemClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Log.d("tag", "here");
		if(null == getIntent().getStringExtra("screenName")){
			loadMyProfileInfo();
		}else{
			loadProfileInfo(getIntent().getStringExtra("screenName"));
		}
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		UserTimelineFragment frag =  UserTimelineFragment.newInstance(getIntent().getStringExtra("screenName"));
		ft.replace(R.id.flProfileTweets, frag);
		ft.commit();
	}

	private void loadMyProfileInfo(){
		Log.d("simpletwitter", "load my info");
		TwitterApplication.getRestClient().getMyInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}
		});
	}
	
	private void loadProfileInfo(String screenName){
		Log.d("simpletwitter", "load info");
		TwitterApplication.getRestClient().getInfo(screenName, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}
		});
	}
	
	protected void populateProfileHeader(User u) {
		TextView tvProfName =  (TextView) findViewById(R.id.tvProfName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfImage = (ImageView) findViewById(R.id.ivProfImage);
		
		tvProfName.setText(u.getName());
		//TODO: ADD these fields to the the user object. 
		tvTagline.setText(u.getTagLine());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFriendsCount() + " Following");
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfImage);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTweetClicked(String screenName) {
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("screenName", screenName);
		startActivity(i);
	}
}

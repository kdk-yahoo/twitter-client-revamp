package com.codepath.apps.simpletwitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class ComposeActivity extends Activity {

	private TwitterClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		client = TwitterApplication.getRestClient();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
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
	
	public void onSubmitAction(MenuItem mi){
		EditText etTweet = (EditText) findViewById(R.id.etTweet);
		client.postUpdate(etTweet.getText().toString(), new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
					try {
						long id = json.getLong("id");
						Intent i = new Intent();
						i.putExtra("id", id);
						setResult(RESULT_OK, i);
						finish();
					} catch (JSONException e) {
						Toast.makeText(ComposeActivity.this, "Tweet Failed", Toast.LENGTH_SHORT).show();
					}
					
			}
			
			
			@Override
			public void onFailure(Throwable e, String s){
				Toast.makeText(ComposeActivity.this, "Tweet Failed", Toast.LENGTH_SHORT).show();
			}
		});
	}
	

}

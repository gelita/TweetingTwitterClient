package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends FragmentActivity {
	EditText etBody;
	String name;
	String screenName;
	String url;
	TextView tvName;
	TextView tvScreenName;
	TextView tvUrl; 
	ImageView ivProfileImage;
	String status;
	TwitterClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_compose_tweet);		
		client = TwitterApplication.getRestClient();
		client.getAuthUser(
				new JsonHttpResponseHandler(){
					public void onSuccess(JSONObject json){	
						User u = User.fromJSON(json); 
						getActionBar().setTitle("");
						setUpViews(u);						
					}
					@Override
					public void onFailure(Throwable arg0) {
						Toast.makeText(ComposeTweetActivity.this, "FAIL", Toast.LENGTH_LONG).show();
					}
				});
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_compose_tweet, menu);			
		return true;
	}
	
	public void onCancelTweet(MenuItem item){
		finish();		
	}

	public void onPostTweet(MenuItem item){		
		final String newStatus = etBody.getText().toString();
		client.postTweet(newStatus,new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONObject jsonObj){				
				Intent data = new Intent();
				data.putExtra("status", newStatus);				
				setResult(RESULT_OK, data);	
				finish();
			}

			@Override
			public void onFailure(Throwable e, String s){
				super.onFailure(e,s);						
			}
		});
	}

	private void setUpViews(User u){
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(u.getName());
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		tvScreenName.setText("@" + u.getScreenName());
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);		
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(),ivProfileImage);
		etBody = (EditText) findViewById(R.id.etBody);
	}
}
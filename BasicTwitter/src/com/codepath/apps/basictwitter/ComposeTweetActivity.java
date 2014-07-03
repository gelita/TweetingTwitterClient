package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {
	private EditText etBody;
	private String name, screenName;
	private String url;
	private TextView tvName, tvScreenName;
	//private ImageView ivProfileImage;
	private String newStatus;
	private TwitterClient c;
	private TextView tvCounter;
	private User u;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_compose_tweet);
		getExtras();
		setUpViews();
		c = TwitterApplication.getRestClient();
		c.getAuthUser(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonUser){
				User u = User.fromJSON(jsonUser);			
				name = u.getName();
				screenName = u.getScreenName();
				url = u.getProfileImageUrl();
			}
		});	
		
		etBody.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.d("debug", 140 - etBody.length() + "");
				int length = 140 - etBody.length();		    	
				tvCounter = (TextView)findViewById(R.id.tvCounter);		        
				tvCounter.setText(length + "");
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
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
			newStatus = etBody.getText().toString();
			Tweet newTweet = new Tweet();
			newTweet.setUser(u);			
			newTweet.setBody(etBody.getText().toString());
			//++fix this trying to show right away ++
			//newTweet.setCreatedAt(newTweet.setCreatedAt(
			//??
			//newTweet.setUid();
		c.postTweet(newStatus , new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, JSONObject jsonObj){
				Toast.makeText(getApplicationContext(),"Tweeting!", Toast.LENGTH_SHORT).show();
			}
			@Override
			public void onFailure(Throwable e, String s){
				super.onFailure(e,s);						
			}
		});
		Intent data = new Intent();
		data.putExtra("status", newStatus);				
		setResult(RESULT_OK, data);	
		finish();
	}

	private void getExtras(){
		name = getIntent().getStringExtra("name");
		screenName = getIntent().getStringExtra("screenName");	
		url = getIntent().getStringExtra("url");		
	}

	private void setUpViews(){
		tvName = (TextView) findViewById(R.id.tvName);
		tvScreenName = (TextView) findViewById(R.id.tvScreenName);
		tvName.setText(name);
		tvScreenName.setText("@" +screenName);
		etBody = (EditText) findViewById(R.id.etBody);
		ImageView profileImage = (ImageView) findViewById(R.id.ivProfileImage);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(url, profileImage);
	}	
}
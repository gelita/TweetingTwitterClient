package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GenUserTimelineFragment extends TweetsListFragment {
	User user;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		TwitterApplication.getRestClient().getUserInfo("LevisStadium", new JsonHttpResponseHandler(){
			
			@Override
			public void onSuccess(JSONArray json){
				addAll(Tweet.fromJSONArray(json));	
			}
		}
				);	
	}	
	public void setUser(User user) {
		this.user = user;
	}
}
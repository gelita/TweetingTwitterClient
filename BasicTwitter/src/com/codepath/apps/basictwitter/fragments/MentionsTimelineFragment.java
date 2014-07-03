package com.codepath.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimelineFragment extends TweetsListFragment {
	private TwitterClient client;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline();
	}
	
	//this fragment extends TweetsListFragment and therefore shares the view of the
		//fragment.  no need for onCreateView()
	public void populateTimeline(){
		client.getMentionsTimeline(new JsonHttpResponseHandler(){		
			//this handler takes the json and puts it into a very manageable form
			@Override
			public void onSuccess(JSONArray json){
				super.onSuccess(json);				
				addAll(Tweet.fromJSONArray(json));			
			}
			@Override
			public void onFailure(Throwable e, String s){			
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}		
}

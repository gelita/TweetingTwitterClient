package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.UserProfileActivity;
import com.codepath.apps.basictwitter.adapters.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

public class TweetsListFragment extends Fragment{
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets; //list view variable
		
	 @Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//non view initialization goes here
			tweets = new ArrayList<Tweet>();	//a single tv for each item
			aTweets = new TweetArrayAdapter(getActivity(),tweets);
		 //use getActivity rarely!!! - as little as possible	- see my cp notes for details*********		
	}
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//inflate the layout here
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		//assign our view references - ex:findViewById.... 
		lvTweets = (ListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long rowid) {
				Intent i = new Intent(getActivity(), UserProfileActivity.class);
				i.putExtra("tweet", aTweets.getItem(position));
				startActivity(i);
			}

		}); 
		//return the layout view- ? 
		return v;
	}
	
	//delegate adding of the tweets to the internal adapter- preferred *** see notes
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);		
	}
}
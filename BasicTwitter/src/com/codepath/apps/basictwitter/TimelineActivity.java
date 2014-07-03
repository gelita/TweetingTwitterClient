package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity {	
	protected TwitterClient client;
	private static final int REQUEST_CODE = 20;
	String name;
	String screenName;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);	
		setupTabs();
		client = TwitterApplication.getRestClient();
		client.getAuthUser(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject jsonUser){
				User u = User.fromJSON(jsonUser);
				name = u.getName();
				screenName = u.getScreenName();
				url = u.getProfileImageUrl();
			}
		});		
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		//will use tabs for navigation
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//still would like the title to show on actionbar 
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_home)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimelineFragment>
						(R.id.flContainer, this, "first",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		//setting tab 1 as the default at runtime
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_mention)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>
						(R.id.flContainer, this, "second",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}

	public void onProfileView (MenuItem mi){
		Intent i = new Intent(this, ProfileActivity.class);
		startActivity(i);		
	}

	@Override
	protected void onActivityResult(int resultCode, int requestCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			//insert tweet to front of list
		}
	}		

	public void onCompose (MenuItem mi){
		Intent i = new Intent(getBaseContext(), ComposeTweetActivity.class);
		i.putExtra("name", name);
		i.putExtra("screenName",  screenName);
		i.putExtra("url", url);
		startActivityForResult(i, REQUEST_CODE);	
	}

	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.tweets,menu);
		return true;	
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft){
		//
	}
}
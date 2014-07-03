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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity{	
	private static final int REQUEST_CODE = 20;
	User authUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);	
		setupTabs();
		getAuthUser(); 
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
		
	public void onCompose (MenuItem mi){
		Intent i = new Intent(getBaseContext(), ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_CODE);			
	}
	@Override
	protected void onActivityResult(int resultCode, int requestCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			String newStatus = data.getExtras().getString("status");
			Toast.makeText(getBaseContext(),newStatus,Toast.LENGTH_SHORT).show();
			//populateTimeline(newStatus, false);		
		}
	}	
	
	 public void onExpandUser(View v) {
		TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);		 
		tvScreenName.getText().toString();
		Intent i = new Intent(this, UserActivity.class);
		Toast.makeText(this, tvScreenName.getText().toString(), Toast.LENGTH_SHORT).show();
		i.putExtra("screenName", tvScreenName.getText().toString());	
		startActivity(i);
	}
	public void getAuthUser(){
		TwitterApplication.getRestClient().getAuthUser(new JsonHttpResponseHandler() {
         @Override
         public void onSuccess(JSONObject json) {
             authUser = User.fromJSON(json);
         }
		});
	}
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_main,menu);
		return true;	
	}
	public void onTabReselected(Tab tab, FragmentTransaction ft){

	}
}
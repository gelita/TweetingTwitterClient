package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.basictwitter.fragments.GenUserTimelineFragment;
import com.codepath.apps.basictwitter.models.Tweet;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserProfileActivity extends FragmentActivity {
	Tweet t;
	protected User user;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_user_profile);
			Intent i= getIntent();
			t = (Tweet) i.getSerializableExtra("tweet");		
			User user = t.getUser();
			loadProfileInfo(user);
			GenUserTimelineFragment gUserTimelineFrag = (GenUserTimelineFragment) getSupportFragmentManager().
					findFragmentById(R.id.fragmentUserTimeline);
			gUserTimelineFrag.setUser(user);
					
	}

	private void loadProfileInfo(User u) {
		String screenName = u.getScreenName();
		TwitterApplication.getRestClient().getUserInfo(screenName,
				new JsonHttpResponseHandler(){
					public void onSuccess(JSONObject json){	
						User u = User.fromJSON(json); 
						getActionBar().setTitle("@LevisStadium");// + u.getScreenName());
						populateProfileHeader(u);						
					}
					@Override
					public void onFailure(Throwable arg0) {
						Toast.makeText(UserProfileActivity.this, "FAIL", Toast.LENGTH_LONG).show();
					}
				});
	}

	protected void populateProfileHeader(User user) {
		TextView tvName = (TextView)findViewById(R.id.tvName);
		TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
		TextView tvTweets = (TextView)findViewById(R.id.tvTweets);
		tvName.setText(user.getName());
		tvTagline.setText(user.getTagline());
		tvFollowers.setText(user.getFollowersCount() + "\nFOLLOWERS");
		tvFollowing.setText(user.getFriendsCount() + "\nFOLLOWING");
		tvTweets.setText(user.getStatusesCount() + "\nTWEETS");
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(),ivProfileImage);		
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_user_profile,
					container, false);
			return rootView;
		}
	}	
}
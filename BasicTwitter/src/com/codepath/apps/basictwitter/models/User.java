package com.codepath.apps.basictwitter.models;


import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	private static final long serialVersionUID = 4654897646L;
	//tutorial- http://www.dreamincode.net/forums/topic/248522-serialization-in-android/
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
	private String description;
	private int friendsCount;
	private int followersCount;
	private int statusesCount;
	//private String profileBackroundImageUrl; 
	
	//user from json
	public static User fromJSON(JSONObject json){
		User u = new User();
		try{
			u.name = json.getString("name");
			u.uid = json.getLong("id");
			u.screenName = json.getString("screen_name");
			u.profileImageUrl = json.getString("profile_image_url");
			//u.profileBackroundImageUrl = json.getString("profile_backround_image_url");
			u.description = json.getString("description");
			u.followersCount = json.getInt("followers_count");
			u.friendsCount = json.getInt("friends_count");			
			u.statusesCount = json.getInt("statuses_count");
			
		}catch(JSONException e){
			e.printStackTrace();		
		}
		return u;
	}

	public long getUid() {
		return uid;
	}
	
/*
	 public String getProfileBackroundImageUrl(){	 
		return profileBackroundImageUrl;
	}
*/
	
	public String getTagline(){
		return description;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getName() {
		return name;
	}

	public int getFollowersCount() {
		return followersCount;
	}

	public int getFriendsCount() {
		return friendsCount;
	}

	public int getStatusesCount() {
		return statusesCount;
	}
	
}


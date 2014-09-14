package com.calebbrose.loofiti.localdata;

import com.calebbrose.loofiti.R;

import android.content.Context;
import android.content.SharedPreferences;

public class AchievementStorage {
	
	private SharedPreferences prefs;
	
	public AchievementStorage(Context context) {
		prefs = context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE);
	}
	
	public void storeAchievement(String key, Achievement achievement) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putBoolean(key + "_completed", achievement.isCompleted());
		if (achievement.isUsingProgress())
			editor.putInt(key + "_progress", achievement.getProgress());
		editor.commit();
	}
	
	public Achievement retrieveAchievement(String key, String title, String description, int experience, int total) {
		boolean completed = prefs.getBoolean(key + "_completed", false);
		int progress;
		if (total != 0) {
			progress = prefs.getInt(key + "_progress", 0);
			return new Achievement(title, description, experience, completed, progress, total);
		}
		return new Achievement(title, description, experience, completed);
	}

}

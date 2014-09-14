package com.calebbrose.loofiti.localdata;

import java.util.ArrayList;
import java.util.List;

import com.calebbrose.loofiti.R;

import android.content.Context;

/**
 * Contains a list of Achievements. Retrieves and stores achievements upon request.
 */
public class AchievementManager {
	
	private Context context;
	private List<Achievement> achievements;
	private AchievementStorage storage;
	private int numAchievements;
	private int numCompleted;
	
	public AchievementManager(Context context) {
		achievements = new ArrayList<Achievement>();
		storage = new AchievementStorage(context);
		numAchievements = Integer.valueOf(context.getString(R.string.num_achievements));
		numCompleted = 0;
		this.context = context;
	}
	
	public List<Achievement> retrieveAchievements() {
		int resID;
		String key;
		String[] stringArray;
		Achievement achievement;
		
		for (int i = 1; i <= numAchievements; ++i) {
			key = "achievement_" + i;
			resID = context.getResources().getIdentifier(key, "array", context.getPackageName());
			stringArray = context.getResources().getStringArray(resID);
			achievement = storage.retrieveAchievement(key, stringArray[0], stringArray[1],
					Integer.valueOf(stringArray[2]), Integer.valueOf(stringArray[3]));
			if (achievement.isCompleted()) numCompleted++;
			achievements.add(achievement);
		}
		return achievements;
	}
	
	public void storeAchievements() {
		String key;
		for (int i = 1; i <= numAchievements; ++i) {
			key = "achievement_" + i;
			storage.storeAchievement(key, achievements.get(i-1));
		}
	}
	
	public int getNumCompleted() {
		return numCompleted;
	}
	
	public int getNumAchievements() {
		return numAchievements;
	}
	
	public void clearAchievements() {
		for (int i = 0; i < numAchievements; ++i) {
			achievements.get(i).clear();
		}
	}

}

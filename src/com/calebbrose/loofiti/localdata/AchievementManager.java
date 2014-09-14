package com.calebbrose.loofiti.localdata;

import java.util.ArrayList;
import java.util.List;

import com.calebbrose.loofiti.R;

import android.content.Context;

/**
 * Contains a list of Achievements. Retrieves and stores achievements upon request.
 */
public class AchievementManager {

	private static List<Achievement> achievements;
	private static AchievementStorage storage;
	private static int numAchievements;
	private static int numCompleted;
	
	public static void initialize(Context context) {
		achievements = new ArrayList<Achievement>();
		storage = new AchievementStorage(context);
		numAchievements = Integer.valueOf(context.getString(R.string.num_achievements));
		numCompleted = 0;
		
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
	}
	
	public static List<Achievement> getAchievements() {
		return achievements;
	}
	
	public static Achievement getAchievement(int index) {
		return (index >= 0 && index < achievements.size()) ? achievements.get(index) : null;
	}
	
	public static void storeAchievements() {
		String key;
		for (int i = 1; i <= numAchievements; ++i) {
			key = "achievement_" + i;
			storage.storeAchievement(key, achievements.get(i-1));
		}
	}
	
	public static int getNumCompleted() {
		return numCompleted;
	}
	
	public static int getNumAchievements() {
		return numAchievements;
	}
	
	public static void clearAchievements() {
		for (int i = 0; i < numAchievements; ++i) {
			achievements.get(i).clear();
		}
	}

}

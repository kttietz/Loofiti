package com.calebbrose.loofiti.localdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data class for holding user statistics.
 */
public class AppStats {
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
	
	private int sessionCount;
	private int userExperience;
	private int userLevel;
	private String lastLocation;
	private Date lastDate;
	private int totalTime;
	
	public AppStats(int sessionCount, int userExperience, int userLevel,
			String lastLocation, String lastDate, int totalTime) {
		this.sessionCount = sessionCount;
		this.userExperience = userExperience;
		this.userLevel = userLevel;
		setLastLocation(lastLocation);
		try {
			setLastDate(formatter.parse(lastDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.totalTime = totalTime;
	}
	
	public int getSessionCount() {
		return sessionCount;
	}
	
	public void incrementSessionCount() {
		this.sessionCount++;
	}
	
	public int getUserExperience() {
		return userExperience;
	}
	
	public void addUserExperience(int experiencePoints) {
		this.userExperience += experiencePoints;

		// Update level if needed
		int needed = (userLevel)*(userLevel + 1)*50;
		while (userExperience > needed) {
			userExperience -= needed;
			userLevel++;
			needed = (userLevel)*(userLevel + 1)*50;
		}
	}
	
	public int getUserLevel() {
		return userLevel;
	}
	
	public String getLastLocation() {
		return lastLocation;
	}
	
	public void setLastLocation(String lastLocation) {
		this.lastLocation = lastLocation;
	}
	
	public Date getLastDate() {
		return lastDate;
	}
	
	public String getLastDateString() {
		return formatter.format(lastDate);
	}
	
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	public int getTotalTime() {
		return totalTime;
	}
	
	public void addToTotalTime(int time) {
		this.totalTime += time;
	}

	public int getAverageTime() {
		return (sessionCount == 0) ? 0 : totalTime / sessionCount;
	}
	

}

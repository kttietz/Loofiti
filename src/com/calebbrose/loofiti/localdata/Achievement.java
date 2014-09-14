package com.calebbrose.loofiti.localdata;

/**
 * Stores achievement data
 */
public class Achievement {
	
	private String title;
	private String description;
	private int experience;
	private boolean completed;
	
	private boolean usingProgress;
	private int progress;
	private int total;
	
	private void setTitle(String title) {
		this.title = title;
	}
	private void setDescription(String description) {
		this.description = description;
	}
	private void setExperience(int experience) {
		this.experience = experience;
	}
	private void setCompleted(boolean completed) {
		this.completed = completed;
	}
	private void setUsingProgress(boolean usingProgress) {
		this.usingProgress = usingProgress;
	}
	private void setProgress(int progress) {
		this.progress = progress;
	}
	private void setTotal(int total) {
		this.total = total;
	}
	
	public Achievement(String title, String description, int experience, boolean completed) {
		setTitle(title);
		setDescription(description);
		setExperience(experience);
		setCompleted(completed);
		setUsingProgress(false);
	}
	
	public Achievement(String title, String description, int experience, boolean completed, int progress, int total) {
		setTitle(title);
		setDescription(description);
		setExperience(experience);
		setCompleted(completed);
		setUsingProgress(true);
		setProgress(progress);
		setTotal(total);
	}
	
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public int getExperience() {
		return experience;
	}
	public boolean isCompleted() {
		return completed;
	}
	public boolean isUsingProgress() {
		return usingProgress;
	}
	public int getProgress() {
		return progress;
	}
	public int getTotal() {
		return total;
	}

	// Complete the achievement, and return true if achievement was not previously completed.
	// In this way, the achievement can only be completed once.
	public boolean completeAchievement() {
		if (!completed) {
			// TODO: Activate intent to notify about achievement?
			completed = true;
			return true;
		}
		return false;
	}
	
	// Increment achievement progress, and return true if achievement is completed as a result
	public boolean incrementAchievement(int value) {
		if (completed) return false;
		
		progress += value;
		if (progress >= total) {
			progress = total;
			return completeAchievement();
		}
		return false;
	}

	
	
	

}

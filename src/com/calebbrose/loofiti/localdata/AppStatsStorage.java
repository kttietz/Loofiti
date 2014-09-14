package com.calebbrose.loofiti.localdata;

import com.calebbrose.loofiti.R;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Facilitates shared preferences storage of AppStats objects.
 */
public class AppStatsStorage {
	
	private SharedPreferences prefs;
	private Context context;
	
	public AppStatsStorage(Context context) {
		this.context = context;
		prefs = context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE);
	}
	
	public void storeAppStats(AppStats stats) {
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(context.getString(R.string.session_count), stats.getSessionCount());
		editor.putInt(context.getString(R.string.user_experience), stats.getUserExperience());
		editor.putInt(context.getString(R.string.user_level), stats.getUserLevel());
		editor.putString(context.getString(R.string.last_location), stats.getLastLocation());
		editor.putString(context.getString(R.string.last_date), stats.getLastDateString());
		editor.putInt(context.getString(R.string.total_time), stats.getTotalTime());
		editor.commit();
	}
	
	public AppStats retrieveAppStats() {
		AppStats stats = new AppStats(
				prefs.getInt(context.getString(R.string.session_count), 0),
				prefs.getInt(context.getString(R.string.user_experience), 0),
				prefs.getInt(context.getString(R.string.user_level), 0),
				prefs.getString(context.getString(R.string.last_location), ""),
				prefs.getString(context.getString(R.string.last_date), ""),
				prefs.getInt(context.getString(R.string.total_time), 0));
		return stats;
	}

}

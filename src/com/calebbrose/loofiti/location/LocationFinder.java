package com.calebbrose.loofiti.location;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * 
 * Simple static interface for listening for location updates.
 *
 */
public class LocationFinder {
	
	private static LocationManager manager;
	private static Location bestLocation;
	private static boolean waitingForFirst;
	private static boolean listening = false;
	private static LocationListener listener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			updateLocation(location);
			Log.i("LOCATION", "Updated location");
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
		}
		
	};
	
	private static void updateLocation(Location newLocation) {
		if (bestLocation == null || waitingForFirst) {
			bestLocation = newLocation;
			waitingForFirst = false;
			return;
		}
		
		int accuracyDelta = (int) (newLocation.getAccuracy() - bestLocation.getAccuracy());
		boolean isMoreAccurate = accuracyDelta < 0;
		
		if (isMoreAccurate) {
			bestLocation = newLocation;
		}
	}
	
	/**
	 * Begins listening for location updates using most accurate location provider
	 * @param context context containing system services
	 * @return true if listening was started, false if already listening
	 */
	public static boolean startListening(Context context) {
		if (listening) return false;
		
		manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		bestLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		manager.requestLocationUpdates(manager.getBestProvider(criteria, true), 0, 0, listener);
		
		waitingForFirst = true;
		listening = true;
		return true;
	}
	
	/**
	 * Finish listening for location updates and return the most accurate location found
	 * @return most accurate location
	 */
	public static Location stopListening() {
		manager.removeUpdates(listener);
		Location returnLocation = bestLocation;
		listening = false;
		bestLocation = null;
		return returnLocation;
	}
}

package com.calebbrose.loofiti;

import com.calebbrose.loofiti.location.LocationFinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {
	
	private static int TIME_OUT = 3000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		LocationFinder.startListening(this);
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				LocationFinder.stopListening();
				startActivity(i);
				finish();
			}			
		}, TIME_OUT);
	}

}

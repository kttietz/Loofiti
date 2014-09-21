package com.calebbrose.loofiti;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	ListView listView;
	String[] bldgArray;
	
	public static String BuildingName = "Pearson"; // Because no time
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//TextView text = (TextView) findViewById(R.id.dispText);
		//text.setText(new WallRequester().connect("http://www.google.com"));
		
		listView = (ListView)findViewById(R.id.buildings);
		List<String> list = new ArrayList<String>();
		
		//populate list from string
		//URL: 173.28.253.204/Loofiti/loofiti.php?x=_y=_
		// Black: x = 100, y = 150 
		// Coover: x = 100, y = 100
		// Howe: x = 200, y = 200
		// Pearson: ?? near Black
		int x = 100, y = 150;
				
		do {
			String bldgList = new WallRequester()
				.connect("http://173.28.253.204/Loofiti/loofiti.php?", new String[] { "x", "y" }, new String[] { ""+x, ""+y });
			bldgArray = bldgList.split(" ");
		}
		while (bldgArray.length < 1);
		//System.out.println(bldgList);
		
		//TextView text = (TextView) findViewById(R.id.select_closest);
		//text.setText(bldgList);
		
		TextView text = (TextView) findViewById(R.id.bldg_closest);
		text.setText(bldgArray[1]);
		
		//list.remove(0);
		
		for(int i = 2; i < bldgArray.length; i++)
			list.add(bldgArray[i]);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        
        //assign Adapter to ListView
        listView.setAdapter(adapter);
    	
    	listView.setOnItemClickListener(new OnItemClickListener() {
    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    			switchScreen(view, position + 2);
    		}
    	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void switchScreen(View view, int index) {
		//send info of selected building to database, Sam takes data for deserializing
		
		Intent intent = new Intent(this, StallActivity.class);
		BuildingName = bldgArray[index];
		intent.putExtra("building", bldgArray[index]);
		startActivity(intent);
	}
	
	public void switchScreenClosest(View view) {
		//send info of selected building to database, Sam takes data for deserializing
		
		Intent intent = new Intent(this, StallActivity.class);
		intent.putExtra("building", bldgArray[1]);
		startActivity(intent);
	}
	
}

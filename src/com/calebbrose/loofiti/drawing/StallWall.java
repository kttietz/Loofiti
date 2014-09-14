package com.calebbrose.loofiti.drawing;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.calebbrose.loofiti.WallRequester;

import android.os.StrictMode;
import android.util.Log;

public class StallWall {
	
	public String building;
	public ArrayList<MyDrawing> prevOnWall;
	public MyDrawing currSession;
	
	public StallWall(){
		building = "Pearson";
		prevOnWall = new ArrayList<MyDrawing>();
		startNewSession();
	}
	
	//Grab bathroom with specific name ("i.e. Coover, Howe, etc.")
	public StallWall(String buildingName){
		this();
		building = buildingName;
	}
	
	public void getDrawingsFromDatabase() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
			
		URL url;
		HttpURLConnection urlCon = null;
		Object reply = null;
		
		try{
			url = new URL("http://173.28.253.204/Loofiti/loofiti.php?x=30&y=23&b=" + building);
			urlCon = (HttpURLConnection) url.openConnection();
			urlCon.setRequestMethod("GET");
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		// recieve reply
	    try {
	    	//received reply!
	        ObjectInputStream objIn = new ObjectInputStream(urlCon.getInputStream());
	        reply = objIn.readObject();
	        objIn.close();
	        String str = (String) reply; 
	        parseSerializedString(str);
	        
	    } catch (Exception ex) {
	        System.out.println("No Object Returned");
	        if (!(ex instanceof EOFException))
	            ex.printStackTrace();
	            System.err.println("*");
	    }
	}

	//set the currSession to a new drawing layer and return it
	public MyDrawing startNewSession(){
		currSession = new MyDrawing();
		return currSession;
	}
	
	public ArrayList<MyDrawing> getPrevOnWall(){
		return prevOnWall;
	}
	
	public void flushCurrSessionToWall(){
		prevOnWall.add(currSession);
		sendToDatabase(prevOnWall);
		startNewSession();
	}

	public void sendToDatabase(ArrayList<MyDrawing> data) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
			
		URL url;
		HttpURLConnection urlCon = null;
		Object reply = null;
		
			String str = "";
			for (MyDrawing drawing : prevOnWall)
			{
				str += drawing.toString() + "|";
			}
			
			String path = "http://173.28.253.204/Loofiti/loofiti.php?b=" + building + "&d=" + str;
			new WallRequester().connect(path);
		
			/*
		try{
			
			url = new URL();
			urlCon = (HttpURLConnection) url.openConnection();
			
			urlCon.setRequestMethod("GET");
			
			//String urlParameters = "b=" + stallName + "&d=";
			urlCon.setDoInput(true);
			urlCon.setDoOutput(true);
			
			ObjectOutputStream out = new ObjectOutputStream(urlCon.getOutputStream());
			
			//out.writeBytes(urlParameters);
			out.writeObject(str);
			
			out.flush();
			out.close();	
		} catch (IOException e) {
		    e.printStackTrace();
		}
		// recieve reply
	    try {
	        ObjectInputStream objIn = new ObjectInputStream(urlCon.getInputStream());
	        reply = objIn.readObject();
	        objIn.close();
	    } catch (Exception ex) {
	        // it is ok if we get an exception here
	        // that means that there is no object being returned
	        System.out.println("No Object Returned");
	        if (!(ex instanceof EOFException))
	            ex.printStackTrace();
	            System.err.println("*");
	    }
	    */
		
	}
	
	void parseSerializedString(String str)
	{
		this.prevOnWall = new ArrayList<MyDrawing>();
		
		String[] drawings = str.split("|");
		for (String drawing : drawings)
		{
			prevOnWall.add(new MyDrawing(drawing));
		}
	}

}

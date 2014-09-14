package com.calebbrose.loofiti.drawing;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StallWall {
	
	public String stallName;
	public ArrayList<MyDrawing> prevOnWall;
	public MyDrawing currSession;
	
	public StallWall(){
		stallName = "NewLoo";
		prevOnWall = new ArrayList<MyDrawing>();
		startNewSession();
	}
	
	//Grab bathroom with specific name ("i.e. Coover, Howe, etc.")
	public StallWall(String stallName){
		this();
		prevOnWall = getDrawingsFromDatabase(stallName);
	}
	
	private ArrayList<MyDrawing> getDrawingsFromDatabase(String stallName) {
		//***** GET REQUEST TO SERVER, THEN CONVERT SERIALIZED DATA INTO ARRAYLIST OF DRAWINGS
		return new ArrayList<MyDrawing>();
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
		URL url;
		HttpURLConnection urlCon;
		ObjectOutputStream out;
		
		try{
			url = new URL("http://173.28.253.204/loofiti");
			urlCon = (HttpURLConnection) url.openConnection();
			
			urlCon.setRequestMethod("POST");
			urlCon.setDoOutput(true);
			
			out = new ObjectOutputStream(urlCon.getOutputStream());
			out.writeObject(data);
			out.close();	
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}

}

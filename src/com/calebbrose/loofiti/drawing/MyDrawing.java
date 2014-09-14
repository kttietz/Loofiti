package com.calebbrose.loofiti.drawing;

import java.io.Serializable;
import java.util.ArrayList;

public class MyDrawing implements Serializable {

	private static final long serialVersionUID = -4870758867053500171L;
	
	private ArrayList<MyPath> pathList = new ArrayList<MyPath>();
	
	public MyDrawing()
	{
		super();
	}
	
	public MyDrawing(String ser)
	{
		this();
		
		String[] paths = ser.split("_");
		for (String path : paths)
		{
			pathList.add(new MyPath(path));
		}
	}
	
	public void addPath(MyPath p) {
		pathList.add(p);
	}
	
	public ArrayList<MyPath> getPathList(){
		return pathList;
	}
	
	public String toString()
	{
		String ret = "";
		for (MyPath path : pathList)
		{
			ret += path.toString() + "_" ;
		}
		
		return ret;
	}
	
}

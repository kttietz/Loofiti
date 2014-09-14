package com.calebbrose.loofiti.drawing;

import java.io.Serializable;
import java.util.ArrayList;

public class MyDrawing implements Serializable {

	private static final long serialVersionUID = -4870758867053500171L;
	
	private ArrayList<MyPath> pathList = new ArrayList<MyPath>();
	
	public void addPath(MyPath p) {
		pathList.add(p);
	}
	
	public ArrayList<MyPath> getPathList(){
		return pathList;
	}
	
}

package com.calebbrose.loofiti.drawing;

import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Path;

public class MyPath extends Path implements Serializable {
	private static final long serialVersionUID = 7487498859947421468L;

	private ArrayList<float[]> pathPoints = new ArrayList<float[]>();
	
	private int pathColor = Color.BLACK;
	private boolean isErase = false;
	
	public void setColor(int color){
		this.pathColor = color;
	}
	public int getColor(){
		return this.pathColor;
	}
	public void setIsErase(boolean isErase){
		this.isErase = isErase;
	}
	public boolean getIsErase(){
		return this.isErase;
	}
	
	public ArrayList<float[]> getPathPoints(){
		return pathPoints;
	}
	
	public void addToPointsList(float x, float y){
		pathPoints.add(new float[]{x,y});
	}


}

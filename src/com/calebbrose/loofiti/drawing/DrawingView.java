package com.calebbrose.loofiti.drawing;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.util.AttributeSet;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;


public class DrawingView extends View {
	//current stall wall
	private StallWall currStallWall;
	//drawing path
	private MyPath drawPath;
	//drawing and canvas paint
	private Paint drawPaint, canvasPaint, prevPaint;
	//initial color
	private int paintColor = 0xFF660000;
	//canvas
	private Canvas drawCanvas;
	//canvas bitmap
	private Bitmap canvasBitmap;

	private boolean erase=false;
	
	public DrawingView(Context context, AttributeSet attrs){
		super(context, attrs);
		setupDrawings();
	}

	private void setupDrawings() {
		//create MyPath and Paint to be used
		drawPath = new MyPath();
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(20);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		canvasPaint = new Paint(Paint.DITHER_FLAG);
		
		//****Figure out where to grab the Stall to draw
		currStallWall = new StallWall("NEW");
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		//will be called view is assigned a size
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
		
		//load what was previously on the stall wall
		loadPrevDrawingOnCurrStallWall();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//caused when view is redrawn. this will happen because we will invalidate it after every touch event
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//detect user touch
		float touchX = event.getX();
		float touchY = event.getY();
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				//create a new path and add it to the current session
				drawPath = new MyPath();
				drawPath.setColor(paintColor);
				drawPath.setIsErase(erase);
				
				currStallWall.currSession.addPath(drawPath);
			
				//add current point to path
				drawPath.moveTo(touchX, touchY);
				drawPath.addToPointsList(touchX, touchY);
				break;
			case MotionEvent.ACTION_MOVE:
				//add current point to path
				drawPath.lineTo(touchX, touchY);
				drawPath.addToPointsList(touchX, touchY);
				break;
			case MotionEvent.ACTION_UP:
				//makes path "stick" to canvas
				drawCanvas.drawPath(drawPath, drawPaint);

				drawPath.reset();	
				break;
			default:
				return false;
		}
		
		invalidate();	//this will cause onDraw() to be called
		return true;
	}

	public void setColor(String newColor){
		//set color
		invalidate();
		paintColor = Color.parseColor(newColor);
		drawPaint.setColor(paintColor);
	}
	
	
	public void setErase(boolean isErase){
		//set erase true or false
		erase = isErase;
		if(erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR) );
		else drawPaint.setXfermode(null);
	}
	
	public void startNew(){
		//clears canvas to white
		drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
		invalidate();
	}
	
	public void loadPrevDrawingOnCurrStallWall(){
		//create new paint object
		prevPaint = new Paint();
		prevPaint.setColor(paintColor);
		prevPaint.setAntiAlias(true);
		prevPaint.setStrokeWidth(20);
		prevPaint.setStyle(Paint.Style.STROKE);
		prevPaint.setStrokeJoin(Paint.Join.ROUND);
		prevPaint.setStrokeJoin(Paint.Join.ROUND);
		prevPaint.setStrokeCap(Paint.Cap.ROUND);
		
		//Iterate through each layer on the stall
		ArrayList<MyDrawing> prevDrawings = currStallWall.getPrevOnWall();
		//iterate through each drawing layer
		for (MyDrawing d : prevDrawings){
			//iterate through each path in the layer
			for (MyPath p : d.getPathList()){
				//modify paint based on values in the path
				//Is the path an "Erase path"?
				if(p.getIsErase())
					prevPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR) );
				else 
					prevPaint.setXfermode(null);
				
				//What color should the path be?
				prevPaint.setColor(p.getColor());
				
				boolean firstIteration = true;
				//iterate through points and make calls to moveTo() and lineTo() appropriately
				for (float[] point : p.getPathPoints()){
					if (firstIteration){
						p.moveTo(point[0], point[1]);
						firstIteration = false;
					}
					else{
						p.lineTo(point[0], point[1]);
					}
				}
				
				//draw this path
				drawCanvas.drawPath(p, prevPaint);
				p.reset();
				invalidate();	//this will cause onDraw() to be called	

			}				
		}
	}
	
	public void flush(){
		currStallWall.flushCurrSessionToWall();
	}
}


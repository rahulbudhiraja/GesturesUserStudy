package com.example.gesturesuserstudy;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;


public class TouchEventClassifier extends SimpleOnGestureListener  {

	private static int number_of_touches;
	private static String Action;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static double scaling_factor=1.0;
	Context mainActivityContext;
	boolean lock=false;
	private float prevX=0,currentX=0,prevY=0,currentY=0,diffX=0,diffY=0;
	
  
	View.OnTouchListener gestureListener;
	ScaleGestureDetector scaleGestureDetector;
	GestureDetector gestureDetector;
	
	TouchEventClassifier(Context c)
	{
		
		mainActivityContext=c;
		number_of_touches=0;
		scaleGestureDetector = new ScaleGestureDetector(c, new simpleOnScaleGestureListener());
	    gestureDetector=new GestureDetector(c,new MyGestureDetector());
	    Action ="";
	
	}
	
	public void processTouch(MotionEvent event)
	{
		int x = (int)event.getX();
	    int y = (int)event.getY();
	    
	  //  Log.d("", "X position:"+x+"Y position:"+y);
	    switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	          	//Action="Button Down";
	        	
	        	/// Store Starting position of fingers ..
	        	
	        	prevX=event.getX();
	        	prevY=event.getY();
	          	break;
	          	
	        case MotionEvent.ACTION_MOVE:
	        	//Action="Moving";
	        	if(event.getPointerCount()==2)
	        		{	
	        			diffX=event.getX()-prevX;
	        			diffY=event.getY()-prevY;
	        			//lock=true; 
	        			if(Math.abs(diffX)>40 ||Math.abs(diffY)>40)
	        			Action="Translation,X,"+(event.getX()-640)+",Y,"+(event.getY()-260);
	        			
//	        			Log.d("Values of First Finger",event.getX(0)+"       "+event.getY(0));
//	        			Log.d("Values of Second Finger",event.getX(1)+"       "+event.getY(1));
//	        			if(event.)
	        		}
	        	else 
	        	{
	        	 //Rotate ..
	        		
	        		diffX=event.getX()-prevX;
        			diffY=event.getY()-prevY;
        			if(Math.abs(diffX)>Math.abs(diffY))
        				Action="Rotate,Z,"+diffX;
        			else Action="Rotate,Y,"+diffY;
	        	
	        	}
	        	  
	        	break;
	        	
	        case MotionEvent.ACTION_UP:
	        	//Action="Button up";
	        	
	        	prevX=currentX=prevY=currentY=diffX=diffY=0;
	        	
	        	/// Ending POsition ..
	    }
	    
		    scaleGestureDetector.onTouchEvent(event);
		    gestureDetector.onTouchEvent(event);

	    
	}
	
	public class simpleOnScaleGestureListener extends SimpleOnScaleGestureListener {

		 public boolean onScale(ScaleGestureDetector detector) {
		  // TODO Auto-generated method stub
			 scaling_factor*=detector.getScaleFactor();
			 
			 if(scaling_factor<1.2&&scaling_factor>0.8)
				 ;//Action="Translating !";
			 else if(detector.getScaleFactor()>1)
		      Action="Scaling Up,"+ scaling_factor+",ds,";
			 else Action="Scaling Down,"+scaling_factor+",ds,";
		  
			 return true;
		 }

		 public boolean onScaleBegin(ScaleGestureDetector detector) {
		  // TODO Auto-generated method stub
		  //Log.d("Scaling Start"," ");
			 scaling_factor=1.0;
		  return true;
		 }

		 public void onScaleEnd(ScaleGestureDetector detector) {
		  // TODO Auto-generated method stub
			// Log.d("Scaling End"," ");
			 
		 }

		}

		 class MyGestureDetector extends SimpleOnGestureListener {
		        @Override
		        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		            try {
		            	Log.d("passing ","     ");
		            	
		                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
		                    return false;
		                // right to left swipe
		                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		                	  Action="Left Swipe";
		                }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
		                  	Action="Right Swipe";
		                	
		                }
		            } catch (Exception e) {
		                // nothing
		            }
		            return false;
		        }

	
		 }

		public String getAction() 
		{
			// TODO Auto-generated method stub
			if(Action.length()>0)
			return Action;
			else return "";
		}
		
		public void setAction(String temp)
		{
			Action=temp;
		}
		
}

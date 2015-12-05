package com.battleslug.glbase.geometry;

import static java.lang.Math.*;

public class Circle {
	private float r;
	
	public final static float DEGREES = 360;
	
	public Circle(float pointA, float pointB){
		this((float)(sqrt(pow(pointA, 2f)+pow(pointB, 2f))));;
	}
	
	public Circle(float radius){
		r = radius;
	}
	
	public float getRotation(float xLocal, float yLocal){
		float rotation = 0;
		
		/*
		 * Calculate which part of circle the point is located in.
		 * Then use SOH to find angle.
		 * We have to be careful to remember the grid system OpenGl uses..
		 */
		if (xLocal >= 0 && yLocal < 0){
			rotation = 0;
			rotation += toRadians(asin((xLocal-0)/r));
		}
		else if(xLocal >= 0 && yLocal >= 0){
			rotation = 90;
			rotation += toRadians(asin((yLocal-0)/r));
		}
		else if(xLocal < 0 && yLocal >= 0){
			rotation = 180;
			rotation += toRadians(asin((0-xLocal)/r));
		}
		else if(xLocal < 0 && yLocal < 0){
			rotation = 270;
			rotation += toRadians(asin((0-yLocal)/r));
		}
		
		//TODO for some reason rotation is off by 45 degrees. Figure out why and fix if needed.
		rotation -= 45;
		
		return rotation;
	}
	
	public void setRadius(float radius){
		r = radius;
	}
	
	public float getRadius(){
		return r;
	}
	
	public float getA(float degrees){
		return new Double(r*cos(toRadians(degrees))).floatValue();
	}
	
	public float getB(float degrees){
		return new Double(r*sin(toRadians(degrees))).floatValue();
	}
}

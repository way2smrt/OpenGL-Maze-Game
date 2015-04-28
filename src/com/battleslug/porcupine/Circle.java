package com.battleslug.porcupine;

import static java.lang.Math.*;

public class Circle {
	private float r;
	private float xLocal, yLocal;
	
	public final static float DEGREES = 360;
	
	public Circle(float xLocal, float yLocal, float radius){
		this.xLocal = xLocal;
		this.yLocal = yLocal;
		r = radius;
	}
	
	public float getRotation(float xLocal, float yLocal){
		float rotation = 0;
		
		/*
		 * Calculate which part of circle the point is located in.
		 * Then use SOH to find angle.
		 * We have to be careful to remember the grid system OpenGl uses..
		 */
		if (xLocal >= this.xLocal && yLocal < this.yLocal){
			rotation = 0;
			rotation += toRadians(asin((xLocal-this.xLocal)/r));
		}
		else if(xLocal >= this.xLocal && yLocal >= this.yLocal){
			rotation = 90;
			rotation += toRadians(asin((yLocal-this.yLocal)/r));
		}
		else if(xLocal < this.xLocal && yLocal >= this.yLocal){
			rotation = 180;
			rotation += toRadians(asin((this.xLocal-xLocal)/r));
		}
		else if(xLocal < this.xLocal && yLocal < this.yLocal){
			rotation = 270;
			rotation += toRadians(asin((this.yLocal-yLocal)/r));
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
	
	public float getX(float degrees){
		return new Double(yLocal+(r*cos(toRadians(degrees)))).floatValue();
	}
	
	public float getY(float degrees){
		return new Double(xLocal+(r*sin(toRadians(degrees)))).floatValue();
	}
}

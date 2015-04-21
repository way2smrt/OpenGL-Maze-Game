package com.battleslug.logl2d;

import static java.lang.Math.*;

public class Circle {
	private float r;
	private int xLocal, yLocal;
	
	public final static int DEGREES = 360;
	
	public Circle(int xLocal, int yLocal, float radius){
		this.xLocal = xLocal;
		this.yLocal = yLocal;
		r = radius;
	}
	
	public int getRotation(int xLocal, int yLocal){
		int rotation = 0;
		
		/*
		 * Calculate which part of circle the point is located in.
		 * Then use SOH to find angle.
		 * We have to be careful to remember the grid system OpenGl uses..
		 */
		if (xLocal >= this.xLocal && yLocal < this.yLocal){
			rotation = 0;
			rotation += new Float(toRadians(asin((xLocal-this.xLocal)/r))).intValue();
		}
		else if(xLocal >= this.xLocal && yLocal >= this.yLocal){
			rotation = 90;
			rotation += new Float(toRadians(asin((yLocal-this.yLocal)/r))).intValue();
		}
		else if(xLocal < this.xLocal && yLocal >= this.yLocal){
			rotation = 180;
			rotation += new Float(toRadians(asin((this.xLocal-xLocal)/r))).intValue();
		}
		else if(xLocal < this.xLocal && yLocal < this.yLocal){
			rotation = 270;
			rotation += new Float(toRadians(asin((this.yLocal-yLocal)/r))).intValue();
		}
		return rotation;
	}
	
	public float getRadius(){
		return r;
	}
	
	public int getX(int degrees){
		return new Double(yLocal+(r*cos(toRadians(degrees)))).intValue();
	}
	
	public int getY(int degrees){
		return new Double(xLocal+(r*sin(toRadians(degrees)))).intValue();
	}
}

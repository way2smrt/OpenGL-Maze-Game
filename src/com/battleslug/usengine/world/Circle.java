package com.battleslug.usengine.world;

public class Circle {
	private float r;
	private int x, y;
	
	public final static int DEGREES = 360;
	
	public Circle(int x, int y, float radius){
		this.x = x;
		this.y = y;
		r = radius;
	}
	
	public int getRotation(int x, int y){
		return new Double(Math.toDegrees(Math.acos(y/(this.x+r)))).intValue();
	}
	
	public float getRadius(){
		return r;
	}
	
	public int getX(int degrees){
		return new Double(y+(r*Math.cos(Math.toRadians(degrees)))).intValue();
	}
	
	public int getY(int degrees){
		return new Double(x+(r*Math.sin(Math.toRadians(degrees)))).intValue();
	}
}

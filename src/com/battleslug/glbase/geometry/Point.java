package com.battleslug.glbase.geometry;

import static java.lang.Math.*;

public class Point {
	private float x, y, z;
	
	public Point(float x, float y){
		this(x, y, 0);
	}
	
	public Point(float x, float y, float z){
		this.x = x;
		this.y = y; 
		this.z = z;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getZ(){
		return z;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setZ(float z){
		this.z = z;
	}
	
	public void update(Speed speed, double timePassed){
		x += (float)(speed.getXSpeed()*timePassed);
		y += (float)(speed.getXSpeed()*timePassed);
		z += (float)(speed.getXSpeed()*timePassed);
	}
	
	public static float getDistance(Point p1, Point p2){
		float deltaX = p2.getX() - p1.getX();
		float deltaY = p2.getY() - p1.getY();
		float deltaZ = p2.getZ() - p1.getZ();

		float distance = (float)sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ);
		return distance;
	}
}

package com.battleslug.glbase.geometry;

import static java.lang.Math.*;

public class Point {
	private float x, y, z;
	private float xSpeed, ySpeed, zSpeed;
	
	public Point(float x, float y){
		this(x, y, 0);
	}
	
	public Point(float x, float y, float z){
		this.x = x;
		this.y = y; 
		this.z = z;
		
		this.xSpeed = 0;
		this.ySpeed = 0;
		this.zSpeed = 0;
	}
	
	public Point(float x, float y, float z, float xSpeed, float ySpeed, float zSpeed){
		this.x = x;
		this.y = y; 
		this.z = z;
		
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
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
	
	public float getXSpeed(){
		return xSpeed;
	}
	
	public float getYSpeed(){
		return ySpeed;
	}
	
	public float getZSpeed(){
		return zSpeed;
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
	
	public void setXSpeed(float xSpeed){
		this.xSpeed = xSpeed;
	}
	
	public void setYSpeed(float ySpeed){
		this.ySpeed = ySpeed;
	}
	
	public void setZSpeed(float zSpeed){
		this.zSpeed = zSpeed;
	}
	
	public void update(double timePassed){
		x += (float)(xSpeed*timePassed);
		y += (float)(ySpeed*timePassed);
		z += (float)(zSpeed*timePassed);
	}
	
	public static float getDistance(Point p1, Point p2){
		float deltaX = p2.getX() - p1.getX();
		float deltaY = p2.getY() - p1.getY();
		float deltaZ = p2.getZ() - p1.getZ();

		float distance = (float)sqrt(deltaX*deltaX+deltaY*deltaY+deltaZ*deltaZ);
		return distance;
	}
}

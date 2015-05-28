package com.battleslug.glbase.geometry;

public class Speed {
	private float xSpeed, ySpeed, zSpeed;
	
	public Speed(float xSpeed, float ySpeed, float zSpeed){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
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
	
	public void setXSpeed(float xSpeed){
		this.xSpeed = xSpeed;
	}
	
	public void setYSpeed(float ySpeed){
		this.ySpeed = ySpeed;
	}
	
	public void setZSpeed(float zSpeed){
		this.zSpeed = zSpeed;
	}
}

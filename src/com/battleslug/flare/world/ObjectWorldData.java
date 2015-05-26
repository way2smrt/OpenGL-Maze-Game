package com.battleslug.flare.world;

public class ObjectWorldData {
	private float x, y, z;
	private float xSpeed, ySpeed, zSpeed;
	
	public ObjectWorldData(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		
		xSpeed = 0;
		ySpeed = 0;
		zSpeed = 0;
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
}

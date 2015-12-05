package com.battleslug.glbase.geometry;

public class Speed {
	private float xSpeed, ySpeed, zSpeed;
	private float xSpeedMax, ySpeedMax, zSpeedMax;
	
	private boolean speedLimit;
	
	public Speed(float xSpeed, float ySpeed, float zSpeed){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
		
		this.speedLimit = false;
	}
	
	public Speed(float xSpeed, float ySpeed, float zSpeed, float xSpeedMax, float ySpeedMax, float zSpeedMax){
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.zSpeed = zSpeed;
		
		this.xSpeedMax = xSpeedMax;
		this.ySpeedMax = ySpeedMax;
		this.zSpeedMax = zSpeedMax;
		
		this.speedLimit = true;
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
	
	public float getXSpeedMax(){
		return xSpeedMax;
	}
	
	public float getYSpeedMax(){
		return ySpeedMax;
	}
	
	public float getZSpeedMax(){
		return zSpeedMax;
	}
	
	public boolean hasSpeedLimit(){
		return speedLimit;
	}
	
	public void clearXSpeed(){
		xSpeed = 0f;
	}
	
	public void clearYSpeed(){
		ySpeed = 0f;
	}
	
	public void clearZSpeed(){
		zSpeed = 0f;
	}
	
	public void setXSpeedMax(float xSpeedMax){
		this.xSpeedMax = xSpeedMax;
		
		speedLimit = true;
	}
	
	public void setYSpeedMax(float ySpeedMax){
		this.ySpeedMax = ySpeedMax;
		
		speedLimit = true;
	}
	
	public void setZSpeedMax(float zSpeedMax){
		this.zSpeedMax = zSpeedMax;
		
		speedLimit = true;
	}
	
	public void setSpeedMax(float xSpeedMax, float ySpeedMax, float zSpeedMax){
		setXSpeedMax(xSpeedMax);
		setYSpeedMax(ySpeedMax);
		setZSpeedMax(zSpeedMax);
	}
	
	public void setXSpeed(float xSpeed){
		this.xSpeed = xSpeed;
		
		if(speedLimit){
			if(xSpeed > xSpeedMax){
				xSpeed = xSpeedMax;
			}
			else if(xSpeed < -xSpeedMax){
				xSpeed = -xSpeedMax;
			}
		}
	}
	
	public void setYSpeed(float ySpeed){
		this.ySpeed = ySpeed;
		
		if(speedLimit){
			if(ySpeed > ySpeedMax){
				ySpeed = ySpeedMax;
			}
			else if(ySpeed < -ySpeedMax){
				ySpeed = -ySpeedMax;
			}
		}
	}
	
	public void setZSpeed(float zSpeed){
		this.zSpeed = zSpeed;
		
		if(speedLimit){
			if(zSpeed > zSpeedMax){
				zSpeed = zSpeedMax;
			}
			else if(zSpeed < -zSpeedMax){
				zSpeed = -zSpeedMax;
			}
		}
	}
}

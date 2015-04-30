package com.battleslug.flare;

import com.battleslug.porcupine.Circle;
import com.battleslug.porcupine.Display;

public class Player {
	private float speedForward, speedBackward, speedStrafe;
	
	private float xGlobal, yGlobal, zGlobal;
	private float xSpeedGlobal, ySpeedGlobal, zSpeedGlobal;
	private float xSpeedGlobalMax, ySpeedGlobalMax, zSpeedGlobalMax;
	
	private float rotHori;
	
	public enum Direction{FORWARD, BACKWARD, LEFT, RIGHT};
	
	public Player(){
		rotHori = 0;
	}
	
	public void setLocation(float xGlobal, float yGlobal, float zGlobal){
		this.xGlobal = xGlobal;
		this.yGlobal = yGlobal;
		this.zGlobal = zGlobal;
	}
	
	public void setXSpeedGlobal(float xSpeedGlobal){
		this.xSpeedGlobal = xSpeedGlobal;
		
		if(xSpeedGlobal > xSpeedGlobalMax){
			xSpeedGlobal = xSpeedGlobalMax;
		}
		else if(xSpeedGlobal < -xSpeedGlobalMax){
			xSpeedGlobal = -xSpeedGlobalMax;
		}
	}
	
	public void setYSpeedGlobal(float ySpeedGlobal){
		this.ySpeedGlobal = ySpeedGlobal;
		
		if(ySpeedGlobal > ySpeedGlobalMax){
			ySpeedGlobal = ySpeedGlobalMax;
		}
		else if(ySpeedGlobal < -ySpeedGlobalMax){
			ySpeedGlobal = -ySpeedGlobalMax;
		}
	}
	
	public void setZSpeedGlobal(float zSpeedGlobal){
		this.zSpeedGlobal = zSpeedGlobal;
		
		if(zSpeedGlobal > zSpeedGlobalMax){
			zSpeedGlobal = zSpeedGlobalMax;
		}
		else if(zSpeedGlobal < -zSpeedGlobalMax){
			zSpeedGlobal = -zSpeedGlobalMax;
		}
	}
	
	public void setXSpeedGlobalMax(float xSpeedGlobalMax){
		this.xSpeedGlobalMax = xSpeedGlobalMax;
	}
	
	public void setYSpeedGlobalMax(float ySpeedGlobalMax){
		this.ySpeedGlobalMax = ySpeedGlobalMax;
	}
	
	public void setZSpeedGlobalMax(float zSpeedGlobalMax){
		this.zSpeedGlobalMax = zSpeedGlobalMax;
	}
	
	public float getXGlobal(){
		return xGlobal;
	}
	
	public float getYGlobal(){
		return yGlobal;
	}
	
	public float getZGlobal(){
		return zGlobal;
	}
	
	public float getXSpeedGlobal(){
		return xSpeedGlobal;
	}
	
	public float getYSpeedGlobal(){
		return ySpeedGlobal;
	}
	
	public float getZSpeedGlobal(){
		return zSpeedGlobal;
	}
	
	public void setSpeed(float speedForward, float speedBackward, float speedStrafe){
		this.speedForward = speedForward;
		this.speedBackward = speedBackward;
		this.speedStrafe = speedStrafe;
	}
	
	public float getSpeedForward(){
		return speedForward;
	}
	
	public float getSpeedBackward(){
		return speedBackward;
	}
	
	public float getSpeedStrafe(){
		return speedStrafe;
	}
	
	public void setRotationHorizontal(float rotHori){
		this.rotHori = rotHori;
	}
	
	public float getRotationHorizontal(){
		return rotHori;
	}
	
	public void move(Direction dir, float change){
		float rot = rotHori;
		Circle circle = new Circle(0, 0, change);
		
		//no change for a direction of forward
		if(dir == Direction.BACKWARD){
			rot += 180;
		}
		else if(dir == Direction.LEFT){
			rot += 270;
		}
		else if(dir == Direction.RIGHT){
			rot += 90;
		}
		
		setLocation(xGlobal+circle.getX(rot), yGlobal, zGlobal+circle.getY(rot));
	}
}

package com.battleslug.flare.world;

import java.util.Arrays;

import com.battleslug.porcupine.Circle;

public class Pivot {
	private float rotXAxis, rotYAxis, rotZAxis;
	
	private float rotXAxisMin, rotYAxisMin, rotZAxisMin;
	private float rotXAxisMax, rotYAxisMax, rotZAxisMax;
	
	public Pivot(float rotXAxis, float rotYAxis, float rotZAxis){
		this.rotXAxis = rotXAxis;
		this.rotYAxis = rotYAxis;
		this.rotZAxis = rotZAxis;
		
		rotXAxisMin = 0;
		rotYAxisMin = 0;
		rotZAxisMin = 0;
		
		rotXAxisMax = 360;
		rotYAxisMax = 360;
		rotZAxisMax = 360;
	}
	
	public float getRotXAxisMin(){
		return rotXAxisMin;
	}
	
	public float getRotXAxisMax(){
		return rotXAxisMax;
	}
	
	public float getRotYAxisMin(){
		return rotYAxisMin;
	}
	
	public float getRotYAxisMax(){
		return rotYAxisMax;
	}
	
	public float getRotZAxisMin(){
		return rotZAxisMin;
	}
	
	public float getRotZAxisMax(){
		return rotZAxisMax;
	}
	
	public void setRotXAxisLimits(float rotMin, float rotMax){
		rotXAxisMin = rotMin;
		rotXAxisMax = rotMax;
	}
	
	public void setRotYAxisLimits(float rotMin, float rotMax){
		rotYAxisMin = rotMin;
		rotYAxisMax = rotMax;
	}

	public void setRotZAxisLimits(float rotMin, float rotMax){
		rotZAxisMin = rotMin;
		rotZAxisMax = rotMax;
	}
	
	public void setRotation(float rotXAxis, float rotYAxis, float rotZAxis){
		this.rotXAxis = rotXAxis;
		while(this.rotXAxis > rotXAxisMax){
			this.rotXAxis -= rotXAxisMax-rotXAxisMin;
		}
		while(this.rotXAxis < rotXAxisMin){
			this.rotXAxis += rotXAxisMax-rotXAxisMin;
		}
		
		this.rotYAxis = rotYAxis;
		while(this.rotYAxis > rotYAxisMax){
			this.rotYAxis -= rotYAxisMax-rotYAxisMin;
		}
		while(this.rotYAxis < rotYAxisMin){
			this.rotYAxis += rotYAxisMax-rotYAxisMin;
		}
		
		this.rotZAxis = rotZAxis;
		while(this.rotZAxis > rotZAxisMax){
			this.rotZAxis -= rotZAxisMax-rotZAxisMin;
		}
		while(this.rotZAxis < rotZAxisMin){
			this.rotZAxis += rotZAxisMax-rotZAxisMin;
		}
		

	}
	
	public float getRotXAxis(){
		return rotXAxis;
	}
	
	public float getRotYAxis(){
		return rotYAxis;
	}
	
	public float getRotZAxis(){
		return rotZAxis;
	}

}

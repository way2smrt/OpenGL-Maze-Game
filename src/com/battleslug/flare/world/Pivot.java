package com.battleslug.flare.world;

public class Pivot {
	private float rotXZAxis, rotYZAxis, rotXYAxis;
	
	private float rotXZAxisMin, rotYZAxisMin, rotXYAxisMin;
	private float rotXZAxisMax, rotYZAxisMax, rotXYAxisMax;
	
	public Pivot(float rotXAxis, float rotYAxis, float rotZAxis){
		this.rotXZAxis = rotXAxis;
		this.rotYZAxis = rotYAxis;
		this.rotXYAxis = rotZAxis;
		
		rotXZAxisMin = 0;
		rotXYAxisMin = 0;
		rotXYAxisMin = 0;
		
		rotXZAxisMax = 360;
		rotYZAxisMax = 360;
		rotXYAxisMax = 360;
	}
	
	public float getRotXZAxisMin(){
		return rotXZAxisMin;
	}
	
	public float getRotXZAxisMax(){
		return rotYZAxisMax;
	}
	
	public float getRotYZAxisMin(){
		return rotXYAxisMin;
	}
	
	public float getRotYZAxisMax(){
		return rotYZAxisMax;
	}
	
	public float getRotXYAxisMin(){
		return rotXYAxisMin;
	}
	
	public float getRotXYAxisMax(){
		return rotXYAxisMax;
	}
	
	public void setRotXZAxisLimits(float rotMin, float rotMax){
		rotXZAxisMin = rotMin;
		rotXZAxisMax = rotMax;
	}
	
	public void setRotYZAxisLimits(float rotMin, float rotMax){
		rotYZAxisMin = rotMin;
		rotYZAxisMax = rotMax;
	}

	public void setRotXYAxisLimits(float rotMin, float rotMax){
		rotXYAxisMin = rotMin;
		rotXYAxisMax = rotMax;
	}
	
	public void setRotation(float rotXZAxis, float rotYZAxis, float rotXYAxis){
		this.rotXZAxis = rotXZAxis;
		while(this.rotXZAxis > rotXZAxisMax){
			this.rotXZAxis -= rotXZAxisMax-rotXZAxisMin;
		}
		while(this.rotXZAxis < rotXZAxisMin){
			this.rotXZAxis += rotXZAxisMax-rotXZAxisMin;
		}
		
		this.rotYZAxis = rotYZAxis;
		while(this.rotYZAxis > rotYZAxisMax){
			this.rotYZAxis -= rotYZAxisMax-rotYZAxisMin;
		}
		while(this.rotYZAxis < rotYZAxisMin){
			this.rotYZAxis += rotYZAxisMax-rotYZAxisMin;
		}
		
		this.rotXYAxis = rotXYAxis;
		while(this.rotXYAxis > rotXYAxisMax){
			this.rotXYAxis -= rotXYAxisMax-rotXYAxisMin;
		}
		while(this.rotXYAxis < rotXYAxisMin){
			this.rotXYAxis += rotXYAxisMax-rotXYAxisMin;
		}
		

	}
	
	public float getRotXZAxis(){
		return rotXZAxis;
	}
	
	public float getRotYZAxis(){
		return rotYZAxis;
	}
	
	public float getRotXYAxis(){
		return rotXYAxis;
	}
	
	public void setRotXZAxis(float rotXZAxis){
		this.rotXZAxis = rotXZAxis;
	}
	
	public void setRotYZAxis(float rotYZAxis){
		this.rotYZAxis = rotYZAxis;
	}
	
	public void setRotXYAxis(float rotXYAxis){
		this.rotXYAxis = rotXYAxis;
	}
}

package com.battleslug.glbase.geometry;

public class Pivot {
	private float rotXZAxis, rotYZAxis, rotXYAxis;
	
	private float rotXZAxisMin, rotYZAxisMin, rotXYAxisMin;
	private float rotXZAxisMax, rotYZAxisMax, rotXYAxisMax;
	
	private LimitMode xZAxisLimitMode, yZAxisLimitMode, xYAxisLimitMode;
	
	public enum LimitMode{STOP, SKIP};
	
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
		
		rotXZAxis = 0;
		rotYZAxis = 90;
		rotXYAxis = 0;
		
		xZAxisLimitMode = LimitMode.SKIP;
		yZAxisLimitMode = LimitMode.SKIP;
		xYAxisLimitMode = LimitMode.SKIP;
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
	
	public void setXZAxisLimitMode(LimitMode mode){
		this.xZAxisLimitMode = mode;
	}
	
	public void setYZAxisLimitMode(LimitMode mode){
		this.yZAxisLimitMode = mode;
	}
	
	public void setXYAxisLimitMode(LimitMode mode){
		this.xYAxisLimitMode = mode;
	}
	
	public LimitMode getXZAxisLimitMode(){
		return xZAxisLimitMode;
	}
	
	public LimitMode getYZAxisLimitMode(){
		return yZAxisLimitMode;
	}

	public LimitMode getXYAxisLimitMode(){
		return xYAxisLimitMode;
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
		if(xZAxisLimitMode == LimitMode.SKIP){
			while(this.rotXZAxis > rotXZAxisMax){
				this.rotXZAxis -= rotXZAxisMax-rotXZAxisMin;
			}
			while(this.rotXZAxis < rotXZAxisMin){
				this.rotXZAxis += rotXZAxisMax-rotXZAxisMin;
			}
		}
		else if(xZAxisLimitMode == LimitMode.STOP){
			if(this.rotXZAxis > rotXZAxisMax){
				this.rotXZAxis = rotXZAxisMax;
			}
			else if(this.rotXZAxis < rotXZAxisMin){
				this.rotXZAxis = rotXZAxisMin;
			}
		}
		
		this.rotYZAxis = rotYZAxis;
		if(yZAxisLimitMode == LimitMode.SKIP){
			while(this.rotYZAxis > rotYZAxisMax){
				this.rotYZAxis -= rotYZAxisMax-rotYZAxisMin;
			}
			while(this.rotYZAxis < rotYZAxisMin){
				this.rotYZAxis += rotYZAxisMax-rotYZAxisMin;
			}
		}
		else if(yZAxisLimitMode == LimitMode.STOP){
			if(this.rotYZAxis > rotYZAxisMax){
				this.rotYZAxis = rotYZAxisMax;
			}
			else if(this.rotYZAxis < rotYZAxisMin){
				this.rotYZAxis = rotYZAxisMin;
			}
		}
		
		this.rotXYAxis = rotXYAxis;
		if(xYAxisLimitMode == LimitMode.SKIP){
			while(this.rotXYAxis > rotXYAxisMax){
				this.rotXYAxis -= rotXYAxisMax-rotXYAxisMin;
			}
			while(this.rotXYAxis < rotXYAxisMin){
				this.rotXYAxis += rotXYAxisMax-rotXYAxisMin;
			}
		}
		else if(xYAxisLimitMode == LimitMode.STOP){
			if(this.rotXYAxis > rotXYAxisMax){
				this.rotXYAxis = rotXYAxisMax;
			}
			else if(this.rotXYAxis < rotXYAxisMin){
				this.rotXYAxis = rotXYAxisMin;
			}
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
		setRotation(rotXZAxis, rotYZAxis, rotXYAxis);
	}
	
	public void setRotYZAxis(float rotYZAxis){
		setRotation(rotXZAxis, rotYZAxis, rotXYAxis);
	}
	
	public void setRotXYAxis(float rotXYAxis){
		setRotation(rotXZAxis, rotYZAxis, rotXYAxis);
	}
}

package com.battleslug.flare.world;

import java.util.Arrays;

import com.battleslug.logl2d.Circle;

public class Pivot {
	private int rotation;
	private int rotMin = 0;
	private int rotMax = 360;
	
	private boolean rotBindedToParent;
	
	private Pivot parent;
	
	private Pivot[] child = new Pivot[0];
	private int children;
	
	public Pivot(){
		rotation = 0;
	}
	
	public Pivot(int rotation){
		this.rotation = rotation;
	}
	
	public Pivot getParent(){
		return parent;
	}
	
	public void addChild(Pivot pivot){
		children += 1;
		
		child = Arrays.copyOf(child, children);
		
		child[children-1] = pivot;
	}
	
	public Pivot getChild(int child){
		if(child > 0 && child <= children){
			return this.child[child-1];
		}
		return null;
	}
	
	public boolean hasChildren(){
		if (children > 0){
			return true;
		}
		return false;
	}
	
	public boolean hasParent(){
		if(parent != null){
			return true;
		}
		return false;
	}
	
	
	public boolean isRotBindedToParent(){
		return rotBindedToParent;
	}
	
	public void setRotation(int rotation){
		while(rotation >= Circle.DEGREES){
			rotation -= Circle.DEGREES;
		}
		while(rotation < 0){
			rotation += Circle.DEGREES;
		}
		
		if(rotation < rotMin){
			this.rotation = rotMin;
		}
		else if(rotation > rotMax){
			this.rotation = rotMax;
		}
		else {
			this.rotation = rotation;
		}
		
		if(hasChildren()){
			updateChildren();
		}
	}
	
	public void updateChildren(){
		for (int i = 0; i != children; i++){
			if(child[i].isRotBindedToParent()){
				child[i].setRotation(child[i].getRotation()+rotation);
			}
		}
	}
	
	public int getRotation(){
		return rotation;
	}
}

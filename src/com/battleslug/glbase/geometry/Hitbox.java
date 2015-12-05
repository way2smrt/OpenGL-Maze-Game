package com.battleslug.glbase.geometry;

public class Hitbox {
	public float length, width, height;
	
	public Hitbox(float length, float width, float height){
		this.length = length;
		this.width = width;
		this.height = height;
	}
	
	public boolean isPointInside(Point hitboxLoc, Point p){
		return (p.getX()>=hitboxLoc.getX()-length && p.getX()<=hitboxLoc.getX()+length)&&(p.getY()>=hitboxLoc.getY()-height && p.getY()<=hitboxLoc.getY()+height)&&(p.getZ()>=hitboxLoc.getZ()-width && p.getZ()<=hitboxLoc.getZ()+width);
	}
}

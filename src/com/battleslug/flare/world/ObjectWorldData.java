package com.battleslug.flare.world;

import com.battleslug.glbase.geometry.*;

public class ObjectWorldData {
	private Point point;
	private Pivot pivot;
	private Speed speed;
	
	public ObjectWorldData(Point point){
		this(point, new Speed(0, 0, 0), new Pivot(0, 0, 0));
	}
	
	public ObjectWorldData(Point point, Speed speed){
		this(point, speed, new Pivot(0, 0, 0));
	}
	
	public ObjectWorldData(Point point, Speed speed, Pivot pivot){
		this.point = point;
		this.speed = speed;
		this.pivot = pivot;
	}
	
	
	public Point getPoint(){
		return point;
	}
	
	public Pivot getPivot(){
		return pivot;
	}
	
	public Speed getSpeed(){
		return speed;
	}
}

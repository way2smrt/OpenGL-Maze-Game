package com.battleslug.maze.world;

import com.battleslug.glbase.geometry.*;

public class ObjectWorldData {
	private Point point;
	private Pivot pivot;
	private Speed speed;
	
	public ObjectWorldData(){
		this(new Point(0, 0, 0), new Speed(0, 0, 0), new Pivot(0, 0, 0));
	}
	
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
	
	public void setPoint(Point point){
		this.point = point;
	}
	
	public void setPivot(Pivot pivot){
		this.pivot = pivot;
	}
	
	public void setSpeed(Speed speed){
		this.speed = speed;
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

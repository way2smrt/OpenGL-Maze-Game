package com.battleslug.flare.world;

import com.battleslug.glbase.geometry.*;

public class ObjectWorldData {
	private Point point;
	private Pivot pivot;
	
	public ObjectWorldData(Point point){
		this(point, new Pivot(0, 0, 0));
	}
	
	public ObjectWorldData(Point point, Pivot pivot){
		this.point = point;
		this.pivot = pivot;
	}
	
	public Point getPoint(){
		return point;
	}
	
	public Pivot getPivot(){
		return pivot;
	}
}

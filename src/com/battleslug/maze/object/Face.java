package com.battleslug.maze.object;

import com.battleslug.glbase.geometry.*;

public class Face {
	private Material mat;
	private Point[] point;
	
	public Face(Material mat, Point[] point){
		this.mat = mat;
	}
	
	public Material getMaterial(){
		return mat;
	}
	
	public int getNumPoints(){
		return point.length;
	}
	
	public Point getPoint(int point){
		if(point >= this.point.length){
			return this.point[point];
		}
		else {
			return null;
		}
	}
	
	public void addPoint(){
		
	}
}

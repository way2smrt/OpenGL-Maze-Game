package com.battleslug.flare.object;

import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.*;

public class Mock3DObject implements DrawObject {	
	protected Texture tex;
	
	protected Pivot viewer;
	protected Point p;
	
	protected float width, height;
	
	public Mock3DObject(float width, float height, Texture tex, Point p, Pivot viewer){
		this.tex = tex;
		
		this.width = width;
		this.height = height;
		
		this.p = p;
		this.viewer = viewer;
	}
	
	public void draw(Display display){
		//TODO implement drawing
		float rot = viewer.getRotXZAxis()+180;
		
		Circle c = new Circle(0, 0, width/2);
		
		float x1 = p.getX()+c.getY(rot);
		float z1 = p.getZ()+c.getX(rot);
		float x2 = p.getX()+c.getY(rot+180);
		float z2 = p.getZ()+c.getX(rot+180);
				
		
		display.drawQuadTextured3D(new QuadTextured3D(x1, p.getY()+height, z1, x1, p.getY(), z1, x2, p.getY(), z2, x2, p.getY()+height, z2, tex, null), 0);
	}
	
	public void update(Pivot viewer){
		this.viewer = viewer;
	}
	
	public void setViewer(Pivot viewer){
		this.viewer = viewer;
	}
	
	public Pivot getViewer(){
		return viewer;
	}
	
	public boolean shouldBeDrawn(){
		return true;
	}
}

package com.battleslug.maze.object;

import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.*;

public class Mock3DObject implements DrawObject {	
	protected Texture tex;
	
	protected Point viewer;
	protected Point p;
	
	protected float width, height;
	
	public Mock3DObject(float width, float height, Texture tex, Point p, Point viewer){
		this.tex = tex;
		
		this.width = width;
		this.height = height;
		
		this.p = p;
		this.viewer = viewer;
	}
	
	public void draw(Display display){
		Circle c = new Circle(p.getX()-viewer.getX(), p.getZ()-viewer.getZ());
		Circle c2 = new Circle(width);
		
		float rot = c.getRotation(p.getX()-viewer.getX(), p.getZ()-viewer.getZ());
		
		System.out.println(c.getRadius());
		System.out.println(rot);
		
		float x1 = p.getX()+c2.getB(rot);
		float z1 = p.getZ()+c2.getA(rot);
		float x2 = p.getX()+c2.getB(rot+180);
		float z2 = p.getZ()+c2.getA(rot+180);
				
		display.drawQuadTextured3D(new QuadTextured3D(x1, p.getY()+height, z1, x1, p.getY(), z1, x2, p.getY(), z2, x2, p.getY()+height, z2, tex, null), 0);
	}
	
	public void update(Point viewer){
		this.viewer = viewer;
	}
	
	public Point getViewer(){
		return viewer;
	}
	
	public boolean shouldBeDrawn(){
		return true;
	}
}

package com.battleslug.flare.GUI;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

public class GUIObject {
	protected Display display;

	protected Point p;
	protected int width, height; 
	
	public GUIObject(Point p, int width, int height){
		this.p = p;
		this.width = width;
		this.height = height;
	}
	
	public void bind(Display display){
		this.display = display;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public Point getPoint(){
		return p;
	}
}

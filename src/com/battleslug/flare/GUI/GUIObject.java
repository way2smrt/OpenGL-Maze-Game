package com.battleslug.flare.GUI;

import com.battleslug.porcupine.*;

public class GUIObject {
	protected Display display;

	protected int x, y, width, height; 
	
	public GUIObject(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
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
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
}

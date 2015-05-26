package com.battleslug.flare.world;

import com.battleslug.glbase.Display;
import com.battleslug.glbase.QuadTextured2D;


public class World {
	private Display display;
	
	private Bullet[] bullet;
	private QuadTextured2D[] foreground;
	private QuadTextured2D[] background;
	private PhysicalQuad[] stage;
	
	
	private int camX, camY;
	
	public World(){
	}
	
	public void bind(Display display){
		this.display = display;
	}
	
	public void DrawWorld(){
		
	}
	
	public void setCamera(int camX, int camY){
		this.camX = camX;
		this.camY = camY;
	}
	
	public int getCamX(){
		return camX;
	}
	
	public int getCamY(){
		return camY;
	}
}

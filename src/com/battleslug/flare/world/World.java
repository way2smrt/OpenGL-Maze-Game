package com.battleslug.flare.world;

import com.battleslug.logl2d.Display;
import com.battleslug.logl2d.TexturedQuad;


public class World {
	private Display display;
	
	private Bullet[] bullet;
	private TexturedQuad[] foreground;
	private TexturedQuad[] background;
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
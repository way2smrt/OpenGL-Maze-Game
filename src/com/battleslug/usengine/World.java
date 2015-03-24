package com.battleslug.usengine;

public class World {
	private Display display;
	
	private StaticArea background;
	private DynamicArea midground;
	private StaticArea foreground;
	
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
	
	public StaticArea getBackground(){
		return background;
	}
	
	public DynamicArea getMidground(){
		return midground;
	}
	
	public StaticArea getForeground(){
		return foreground;
	}
}

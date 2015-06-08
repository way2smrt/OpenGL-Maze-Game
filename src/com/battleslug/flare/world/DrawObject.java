package com.battleslug.flare.world;

import com.battleslug.glbase.Display;

public class DrawObject {
	protected ObjectWorldData objectWorldData;
	
	protected Display display;
	
	public DrawObject(Display display){
		this.display = display;
	}
	
	public void draw(){
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public ObjectWorldData getObjectWorldData(){
		return objectWorldData;
	}
}

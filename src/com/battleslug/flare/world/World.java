package com.battleslug.flare.world;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

import com.battleslug.flare.sound.*;

public class World {	
	private Display display;
	
	public float groundHeight = 0f;
	
	public float gravity = 9.81f;
	
	public SoundSystem soundSystem;
	
	public World(){
	}
	
	public void bind(Display display){
		this.display = display;
	}
	
	public void update(double timePassed){
		
	}
	
	public void draw(){
		
	}
	
	public void setGravity(float gravity){
		this.gravity = gravity;
	}
	
	public float getGravity(){
		return gravity;
	}
}

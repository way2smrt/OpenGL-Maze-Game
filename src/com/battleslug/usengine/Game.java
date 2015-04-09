package com.battleslug.usengine;

import com.battleslug.usengine.display.Texture;
import com.battleslug.usengine.event.Keyboard;
import com.battleslug.usengine.event.Timer;
import com.battleslug.usengine.world.World;


public class Game {	
	private String FOLDER_NAME;
	
	protected Timer timer;
	protected Keyboard keyboard;
	protected World world;
	
	private final static boolean DEBUG_ENABLED = true;
	
	public Game(String folderName){
		this.FOLDER_NAME = folderName;
		
		timer = new Timer();
		keyboard = new Keyboard();
		
		play();
	}
	
	
	
	public Texture loadTexture(String location){
		return loadTexture(location, Texture.NEAREST, Texture.CLAMP_TO_EDGE);
	}
	
	/**
	 * Loads a texture from the game's texture folder, which is found in game/$gamefoldername/res/tex
	 * @param location
	 * @return
	 */
	public Texture loadTexture(String location, int filt, int wrap){
		return new Texture("game/"+FOLDER_NAME+"/res/tex/"+location, filt, wrap);
	}
	
	public void play(){
	}
}

package com.battleslug.usengine;


public class Game {	
	private String FOLDER_NAME;
	
	public Game(String folderName){
		this.FOLDER_NAME = folderName;
		
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

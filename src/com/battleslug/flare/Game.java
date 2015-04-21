package com.battleslug.flare;

import com.battleslug.flare.event.Keyboard;
import com.battleslug.flare.event.Timer;
import com.battleslug.flare.world.World;
import com.battleslug.logl2d.Display;
import com.battleslug.logl2d.Texture;


public class Game {	
	protected String GAME_NAME;
	
	protected Timer timer;
	protected Keyboard keyboard;
	protected World world;
	protected Display display;
	
	final protected Texture hud_bullet_normal, hud_bullet_rifle, hud_bullet_shotgun;
	
	private final static boolean DEBUG_ENABLED = true;
	
	public Game(String gameName){
		this.GAME_NAME = gameName;
		
		timer = new Timer();
		keyboard = new Keyboard();
		
		display = new Display(GAME_NAME, 640, 640);
		display.create();
		
		hud_bullet_normal = new Texture("res/tex/hud/bullet_normal.png");
		hud_bullet_rifle = new Texture("res/tex/hud/bullet_rifle.png");
		hud_bullet_shotgun = new Texture("res/tex/hud/bullet_shotgun.png");
		
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
		return new Texture("game/"+GAME_NAME+"/res/tex/"+location, filt, wrap);
	}
	
	public void play(){
	}
	
	public void drawDebug(){
		if (DEBUG_ENABLED){
			//TODO, debug information on keypress
		}
	}
}

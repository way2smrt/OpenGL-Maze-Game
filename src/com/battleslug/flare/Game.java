package com.battleslug.flare;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import com.battleslug.flare.event.Keyboard;
import com.battleslug.flare.event.Timer;
import com.battleslug.flare.world.World;
import com.battleslug.logl2d.Display;
import com.battleslug.logl2d.Texture;


public class Game {	
	protected Timer timer;
	protected Keyboard keyboard;
	protected World world;
	protected Display display;
	
	protected Texture hud_bullet_normal, hud_bullet_rifle, hud_bullet_shotgun;
	
	private final static boolean DEBUG_ENABLED = true;
	
	public Game(){
	}
	
	public void init(){
		timer = new Timer();
		keyboard = new Keyboard();
		
		play();
	}
	
	public void loadTextures(){
		hud_bullet_normal = new Texture("res/img/hud/bullet_normal.png");
		hud_bullet_rifle = new Texture("res/img/hud/bullet_rifle.png");
		hud_bullet_shotgun = new Texture("res/img/hud/bullet_shotgun.png");
	}
	
	public void play(){
	}
	
	public void drawDebug(){
		if (DEBUG_ENABLED){
			//TODO, debug information on keypress
		}
	}
}

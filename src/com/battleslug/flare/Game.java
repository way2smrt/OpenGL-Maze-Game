package com.battleslug.flare;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;

import com.battleslug.flare.event.*;
import com.battleslug.flare.world.World;
import com.battleslug.flare.Player;

import com.battleslug.porcupine.Display;
import com.battleslug.porcupine.Texture;

public class Game {	
	protected Keyboard keyboard;
	protected Mouse mouse;
	protected World world;
	protected Display display;
	
	protected Player player;
	
	protected Texture hud_bullet_normal, hud_bullet_rifle, hud_bullet_shotgun;
	
	private final static boolean DEBUG_ENABLED = true;
	
	protected final static float WORLD_FLOOR = 0.0f;
	protected final static float WORLD_GRAVITY = 9.81f;
	//1 unit = 1 metre
	protected final static float WORLD_UNIT_IN_METRES = 1.0f;
	
	public Game(){
		if (glfwInit() != GL11.GL_TRUE){
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		keyboard = new Keyboard();
		mouse = new Mouse();
		
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

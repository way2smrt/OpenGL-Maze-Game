package com.battleslug.flare;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;

import com.battleslug.flare.event.Keyboard;
import com.battleslug.flare.world.World;
import com.battleslug.flare.Player;

import com.battleslug.porcupine.Display;
import com.battleslug.porcupine.Texture;

public class Game {	
	private double timeLast = 0;
	
	protected double timePassed = 0;
	protected Keyboard keyboard;
	protected World world;
	protected Display display;
	
	protected Player player;
	
	protected Texture hud_bullet_normal, hud_bullet_rifle, hud_bullet_shotgun;
	
	private final static boolean DEBUG_ENABLED = true;
	
	public Game(){
		if (glfwInit() != GL11.GL_TRUE){
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
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
	
	protected void updateTimer(){
		timePassed = glfwGetTime()-timeLast;
		timeLast = glfwGetTime();
	}
}

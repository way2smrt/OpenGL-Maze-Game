package com.battleslug.maze;

import static org.lwjgl.glfw.GLFW.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.opengl.*;
import org.lwjgl.openal.*;

import sun.applet.Main;

import java.io.*;

import com.battleslug.maze.*;
import com.battleslug.maze.object.*;
import com.battleslug.maze.sentient.Player;
import com.battleslug.maze.world.*;
import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.geometry.Pivot.LimitMode;
import com.battleslug.glbase.Display;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.event.*;

import static java.lang.Math.*;

public class Game {	
	private final static float WORLD_FLOOR = 0.0f;
	
	private static final float CURSOR_SPEED = 0.4f;
	
	private Keyboard keyboard;
	private Mouse mouse;
	private World world;
	private Display display;
	
	private Player player;
	
	private Texture texDoge;
	
	private Texture[] texGround;
	private Texture[] texWall;
	
	private Thread musicThread;
	
	public Game(){
		init();
		play();
	}
	
	public void init(){
		if(glfwInit() != GL11.GL_TRUE){
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		keyboard = new Keyboard();
		mouse = new Mouse();
		
		display = new Display("The Maze Game", 900, 720, false);
		display.create();
		
		display.setCursorLocked(true);
		
		loadTextures();
		
		world = new World(3, 5, 1f, texWall[0]);
		world.bind(display);
		
		player = new Player("PlayerName", world, keyboard, mouse);
		
		keyboard.bind(display);
		mouse.bind(display);
	}
	
	public void play(){	
		final int CROSSHAIR_DIST_MIN = 5;
		final int CROSSHAIR_DIST_MAX = 35;
		float crossHairDist = CROSSHAIR_DIST_MIN;
	
		float cubeX = -40f;
		
		VectorColor HUDBackColor = new VectorColor(1.0f, 0.0f, 0.0f, 0.75f);
		
		//Sound soundTest = new Sound("game/OPSF/res/sound/test_sound.wav");
		
		VectorColor colorBlue = new VectorColor(0f, 0f, 1f);
		VectorColor colorWhite = new VectorColor(1f, 1f, 1f);
		VectorColor colorBlack = new VectorColor(0f, 0f, 0f);
		VectorColor colorGreen = new VectorColor(0f, 1f, 0f);
		VectorColor colorRedDark = new VectorColor(1f, 0f, 0.5f);
		
		int score = 0;
		
		while(true){
			//update mouse and keyboard events
			keyboard.update();
			mouse.update();
			
			//update display events
			Display.updateEvents();
			
			//update crosshair
			if(mouse.wasPressedLeftButton()){
				crossHairDist = CROSSHAIR_DIST_MAX;
			}
			else if(crossHairDist > CROSSHAIR_DIST_MIN){
				crossHairDist -= (float)(display.getTimePassed()*50);
			}
			else {
				crossHairDist = CROSSHAIR_DIST_MIN;
			}
			
			//draw the crosshair
			drawCrosshair(5, (int)(crossHairDist), colorBlack, colorBlack);
			drawCrosshair(7, 3, colorWhite, colorWhite);
			
			//draw the ground
			drawGround(texGround[0]);
			
			//draw the world/maze, making sure it is drawn so the player is in the starting cell
			world.draw();
			
			//originate text and draw
			display.setTextDrawOrigin(new Point(0, 0));
			display.drawText("Score: "+ new Integer(score).toString(), display.getWidth()/3, Display.DEF_CHAR_WIDTH, Display.DEF_CHAR_HEIGHT, HUDBackColor);

			//get user action on player
			player.think(display.getTimePassed());
				
			//set display camera to player's
			display.setCamera(player.getCamera());
			
			//check for game kill
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			//playSound("res/sound/gunshot1.wav");
			
			//update the display
			display.update();
			display.clear();
			
			//update the game timer
			display.updateTimer();
		}
	}
	
	private void loadTextures(){
		texDoge = new Texture("res/tex/misc/doge.png");
		
		texGround = new Texture[2];
		texGround[0] = new Texture("res/tex/material/rocks2.png");
		texGround[1] = new Texture("res/tex/material/sand1.png");
		
		texWall = new Texture[1];
		texWall[0] = new Texture("res/tex/material/brick1.png");
	}
	
	private void drawCrosshair(int size, int spacing, VectorColor cInner, VectorColor cOuter){
		display.drawLine(new Point((display.getWidth()/2)-size-spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)-spacing, (display.getHeight()/2)), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)-size-spacing), new Point((display.getWidth()/2), (display.getHeight()/2)-spacing), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2)+spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)+size+spacing, (display.getHeight()/2)), cInner, cOuter);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)+spacing), new Point((display.getWidth()/2), (display.getHeight()/2)+size+spacing), cInner, cOuter);
	}
	
	private void drawGround(Texture texFloor){
		display.drawQuadTextured3D(new QuadTextured3D(1000, WORLD_FLOOR, 1000, 1000, WORLD_FLOOR, -1000, -1000, WORLD_FLOOR, -1000, -1000, WORLD_FLOOR, 1000, texFloor, null), 0.15f);
	}
	
	private static synchronized void playSound(final String file){
		//invoke new thread which plays sound
		new Thread(new Runnable(){
			public void run(){
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(file));
					clip.open(inputStream);
					clip.loop(0);
					clip.start();
				} 
				catch(Exception e){
					System.err.println(e.getMessage());
				}
				while(clip.isRunning()){}
				clip.close();
			}
		}).start();
	}
	
	public static void main(String[] args){
		new Game();
	}
}

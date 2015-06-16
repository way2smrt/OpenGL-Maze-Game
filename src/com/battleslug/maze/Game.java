package com.battleslug.maze;

import static org.lwjgl.glfw.GLFW.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.lwjgl.opengl.*;

import java.io.*;
import java.util.Random;

import com.battleslug.maze.sentient.Player;
import com.battleslug.maze.world.*;
import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.Display;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.event.*;

public class Game {	
	private Keyboard keyboard;
	private Mouse mouse;
	private World world;
	private Display display;
	
	private Player player;
	
	private Texture[] texGround;
	private Texture[] texWall;
	
	private int level;
	
	private double timeEnd;
	private double timeStart;
	
	private String playerName = "";
	
	private String highscorePlayerName;
	private double highscoreTime;
	
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
		
		level = 1;
		
		//load the highscores for the first level
		loadHighscore();
		
		world = new World(2, 5, (int)(Math.pow(2, (level-1))), getRandWallTex(), getRandGroundTex());
		world.bind(display);
		
		player = new Player(playerName, world, keyboard, mouse);
		
		keyboard.bind(display);
		mouse.bind(display);
		
		//put player at maze start
		spawnPlayer();
	}
	
	public void play(){	
		final int CROSSHAIR_DIST_MIN = 5;
		final int CROSSHAIR_DIST_MAX = 35;
		float crossHairDist = CROSSHAIR_DIST_MIN;
		
		VectorColor colorBlue = new VectorColor(0f, 0f, 1f);
		VectorColor colorWhite = new VectorColor(1f, 1f, 1f);
		VectorColor colorBlack = new VectorColor(0f, 0f, 0f);
		VectorColor colorGreen = new VectorColor(0f, 1f, 0f);
		VectorColor colorPurple = new VectorColor(1f, 0f, 0.8f);
		
		//setup game
		while(!keyboard.wasPressed(GLFW_KEY_ENTER)){
			keyboard.update();
			
			Display.updateEvents();
			
			display.setTextDrawOrigin(new Point(0, 0));
			display.drawText("Welcome to the maze game!", 640, 16, 24, colorPurple);
			display.drawText("Find the red exit and try to beat the highscore.", 640, 16, 24, colorPurple);
			display.drawText("Maze size will increase every level.", 640, 16, 24, colorPurple);
			display.drawText("Please type your name: ", 640, 16, 24, colorPurple);
			display.drawText(playerName, 640, 16, 24, colorGreen);
			
			world.draw();
			
			//set display camera to player's
			player.thinkMouseOnly();
			
			display.setCamera(player.getCamera());
			
			//check for game kill
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			//delete character in player name
			if(keyboard.wasPressed(GLFW_KEY_BACKSPACE)){
				if(playerName.length() > 0){
					playerName = playerName.substring(0, playerName.length()-1);
				}	
			}
			
			display.update();
			display.clear();
			
			if(display.hasUnreadInput()){
				playerName += display.getChar();
			}
		}
			
		//play game
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
			
			//draw the world/maze, making sure it is drawn so the player is in the starting cell
			world.draw();
			
			//originate text and draw game data to screen
			display.setTextDrawOrigin(new Point(0, 0));
			display.drawText("Name: "+playerName, 640, 16, 24, colorPurple);
			display.drawText("Level: "+new Integer((int)(level)).toString(), 640, 16, 24, colorPurple);
			display.drawText("Size: "+new Integer((int)(world.getMazeWidth())).toString()+"x"+new Integer((int)(world.getMazeWidth())).toString(), 640, 16, 24, colorPurple);
			display.drawText(new Float((float)(display.getTime()-timeStart)).toString(), 640, 16, 24, colorPurple);
			
			//draw highscores
			display.drawText("Highscore player name: "+highscorePlayerName, 640, 16, 24, colorBlue);
			display.drawText("Highscore: "+new Double(highscoreTime).toString(), 640, 16, 24, colorBlue);
			
			//get user action on player
			player.think(display.getTimePassed());
				
			//set display camera to player's
			display.setCamera(player.getCamera());
			
			//check for game kill
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			//check to see whether player has made it to the exit
			checkNextLevel();
		
			//playSound("res/sound/gunshot1.wav");
			
			//update the display
			display.update();
			display.clear();
			
			//update the game timer
			display.updateTimer();
		}
	}
	
	private void checkNextLevel(){
		float pX = player.getObjectWorldData().getPoint().getX();
		float pZ = player.getObjectWorldData().getPoint().getZ();
		
		float exitX = world.getExitPoint().getX(); 
		float exitZ = world.getExitPoint().getY();
		
		if(pX > exitX && pX < exitX+world.getCellWidth() && pZ > exitZ && pZ < exitZ+world.getCellWidth()){
			nextLevel();
		}
	}
	
	private Texture getRandWallTex(){
		return texWall[new Random().nextInt(texWall.length)];
	}
	
	private Texture getRandGroundTex(){
		return texGround[new Random().nextInt(texGround.length)];
	}
	
	private void nextLevel(){
		timeEnd = display.getTime()-timeStart;
		
		saveScore();
		
		level += 1;
		
		System.out.println("World: Proceeding to next level");
		spawnPlayer();
		world = new World(world.getMazeWidth()+1, world.getCellWidth(), (int)(Math.pow(2, (level-1))), getRandWallTex(), getRandGroundTex());
		
		//bind player to new world
		player.bindWorld(world);
		
		//bind new world to display
		world.bind(display);
		
		timeStart = display.getTime();
		
		loadHighscore();
	}
	
	private void spawnPlayer(){
		player.getObjectWorldData().getPoint().setX(0+world.getCellHeight()/2);
		player.getObjectWorldData().getPoint().setZ(0+world.getCellWidth()/2);
	}
	
	private void loadTextures(){		
		texGround = new Texture[8];
		texGround[0] = new Texture("res/tex/material/rocks2.png");
		texGround[1] = new Texture("res/tex/material/sand1.png");
		texGround[2] = new Texture("res/tex/material/grass3.png");
		texGround[3] = new Texture("res/tex/material/stoneRoad1.png");
		texGround[4] = new Texture("res/tex/material/leaves2.png");
		texGround[5] = new Texture("res/tex/material/sandRocks2.png");
		texGround[6] = new Texture("res/tex/material/dirtDried1.png");
		texGround[7] = new Texture("res/tex/material/rock4.png");
		
		texWall = new Texture[6];
		texWall[0] = new Texture("res/tex/material/brick1.png");
		texWall[1] = new Texture("res/tex/material/rocks1.png");
		texWall[2] = new Texture("res/tex/material/dirtDried1.png");
		texWall[3] = new Texture("res/tex/material/brick2.png");
		texWall[4] = new Texture("res/tex/material/bark1.png");
		texWall[5] = new Texture("res/tex/material/concrete1.png");
	}
	
	private void drawCrosshair(int size, int spacing, VectorColor cInner, VectorColor cOuter){
		display.drawLine(new Point((display.getWidth()/2)-size-spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)-spacing, (display.getHeight()/2)), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)-size-spacing), new Point((display.getWidth()/2), (display.getHeight()/2)-spacing), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2)+spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)+size+spacing, (display.getHeight()/2)), cInner, cOuter);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)+spacing), new Point((display.getWidth()/2), (display.getHeight()/2)+size+spacing), cInner, cOuter);
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
	
	private void loadHighscore(){
		highscorePlayerName = "none";
		highscoreTime = 0.0d;
		
		BufferedReader in = null;
		
		//load file
		try {
			in = new BufferedReader(new FileReader("data/score/"+new Integer(level).toString()+".txt"));
		
			double time;
			
			if(in != null){
				try{
					while(in.ready()){
						highscorePlayerName = in.readLine();
						time = new Double(in.readLine());
						
						if(time > highscoreTime){
							highscoreTime = time;
						}
						
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Score .txt file not found for level: "+new Integer(level).toString());
			
			highscorePlayerName = "none";
			highscoreTime = 0d;
		}
		finally {
			try {
				if(in != null){
					in.close();
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private void saveScore(){
		PrintWriter out = null;
		
		//load file
		try {
			out = new PrintWriter("data/score/"+new Integer(level).toString()+".txt");
			
			out.println(playerName);
			out.println(new Double(timeEnd).toString());
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally {
			if(out != null){
				out.close();
			}	
		}
	}
	
	public static void main(String[] args){
		new Game();
	}
}

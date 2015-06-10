package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.*;
import org.lwjgl.openal.*;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
import com.battleslug.flare.sentient.Player;
import com.battleslug.flare.object.*;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.geometry.Pivot.LimitMode;
import com.battleslug.glbase.Display;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.event.*;

import static java.lang.Math.*;

public class OPSF {	
	private final static float WORLD_FLOOR = 0.0f;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	private static final float CURSOR_SPEED = 0.4f;
	
	private Keyboard keyboard;
	private Mouse mouse;
	private World world;
	private Display display;
	
	private Player player;
	
	private Image imgDoge;
	
	private Texture texTest1, texGrass;
	
	public OPSF(){
		init();
		play();
	}
	
	public void init(){
		if(glfwInit() != GL11.GL_TRUE){
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		keyboard = new Keyboard();
		mouse = new Mouse();
		
		display = new Display("Operation Solar Fury", 900, 720, false);
		display.create();
		
		display.setCursorLocked(true);
	}
	
	public void play(){	
		world = new World();
		world.bind(display);
		
		player = new Player("Bob the test dummy", world, keyboard, mouse);
		
		keyboard.bind(display);
		mouse.bind(display);
		
		texTest1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png", Texture.NEAREST, Texture.REPEAT);
		texGrass = new Texture(GAME_FOLDER+"/res/tex/grass3.png", Texture.NEAREST, Texture.REPEAT);
		imgDoge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		imgDoge.setDimensions(50, 50);
		
		Texture tex1 = new Texture(GAME_FOLDER+"/res/tex/sand1.png");
		Texture tex2 = new Texture(GAME_FOLDER+"/res/tex/rock1.png");
		Texture tex3 = new Texture(GAME_FOLDER+"/res/tex/grassFlowers1.png");
		Texture tex4 = new Texture(GAME_FOLDER+"/res/tex/bark1.png");
		Texture tex5 = new Texture(GAME_FOLDER+"/res/tex/stoneRoad1.png");
		Texture tex6 = new Texture(GAME_FOLDER+"/res/tex/sandRocks2.png");
		Texture texTree = new Texture(GAME_FOLDER+"/res/tex/testTree1.png");
		
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
		
		Mock3DObject tree = new Mock3DObject(3, 7, texTree, new Point(0, 0, 0), player.getObjectWorldData().getPoint());
		
		while(true){
			keyboard.update();
			mouse.update();
			
			Display.updateEvents();
			
			world.update(display.getTimePassed());
			
			Point pO = new Point(0, 0, 0);
			
			//make cursor move on click
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
			
			display.drawCube(3, 0.5f, 5, 1, tex1);
			display.drawCube(3, 1.5f, 5, 1, tex2);
			display.drawCube(3, 2.5f, 5, 1, tex3);
			display.drawCube(-7, 0.5f, 3, 1, tex2);
			display.drawCube(3, 0.5f, -1, 1, tex3);
			display.drawCube(-3, 0.5f, -4, 1, tex4);
			display.drawCube(0, 0.5f, 0, 1, imgDoge.getTexture());
			
			tree.update(player.getObjectWorldData().getPoint());
			tree.draw(display);
			
			//soundTest.play();
			
			display.drawCube(cubeX, 5, 0, 5, tex5);
			cubeX += (float)(2f*display.getTimePassed());
			
			drawFloor(tex1);
			
			display.drawLine3D(pO, new Point(0, 5, 0), colorBlue, colorWhite);
			display.drawLine3D(pO, new Point(cubeX, 5f, 0), colorGreen, colorGreen);
			
			world.draw();
			
			display.setTextDrawOrigin(new Point(0, 0));
			display.drawText("Alpha 0.0.0", display.getWidth()/3, Display.DEF_CHAR_WIDTH, Display.DEF_CHAR_HEIGHT, HUDBackColor);
			display.drawText("8=========D", display.getWidth()/3, Display.DEF_CHAR_WIDTH, Display.DEF_CHAR_HEIGHT, HUDBackColor);
			
			if(keyboard.isDown(GLFW_KEY_Z)){										
				imgDoge.setLocal(imgDoge.getWidth()/2, imgDoge.getHeight()/2);
				
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, imgDoge.getTexture(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 400, display.getTexFont(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, imgDoge.getTexture(), null));
			}

			player.think(display.getTimePassed());
				
			display.setCamera(player.getCamera());
			
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			display.update();
			display.clear();
			display.updateTimer();
		}
	}
	
	private void drawCrosshair(int size, int spacing, VectorColor cInner, VectorColor cOuter){
		display.drawLine(new Point((display.getWidth()/2)-size-spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)-spacing, (display.getHeight()/2)), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)-size-spacing), new Point((display.getWidth()/2), (display.getHeight()/2)-spacing), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2)+spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)+size+spacing, (display.getHeight()/2)), cInner, cOuter);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)+spacing), new Point((display.getWidth()/2), (display.getHeight()/2)+size+spacing), cInner, cOuter);
	}
	
	private void drawFloor(Texture texFloor){
		display.drawQuadTextured3D(new QuadTextured3D(100, WORLD_FLOOR, 100, 100, WORLD_FLOOR, -100, -100, WORLD_FLOOR, -100, -100, WORLD_FLOOR, 100, texFloor, null), 0.15f);
	}
	
	public static void main(String[] args) {
		new OPSF();
	}
}

package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
import com.battleslug.flare.world.Pivot.LimitMode;
import com.battleslug.flare.sentient.Sentient;
import com.battleslug.porcupine.*;

import static java.lang.Math.*;

public class OPSF extends Game {
	private Player player;
	
	private World world;
	
	private Texture texTest1, texGrass;
	private Image imgDoge;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	private static final float CURSOR_SPEED = 0.4f;
	
	public OPSF(){
	}
	
	@Override 
	public void play(){
		display = new Display("Operation Solar Fury (Alpha 0.0.0)", 900, 720, false);
		display.create();
		
		display.setCursorLocked(true);
		
		player = new Player("Bob the test dummy", 125);
		player.setSpeed(5, 2, 3);
		//average height for a human
		player.setYCamLocal(1.7f);
		player.setPivot(new Pivot(0, 90, 0));
		player.getPivot().setRotYZAxisLimits(0f, 180f);
		player.getPivot().setYZAxisLimitMode(LimitMode.STOP);
		
		display.setPivotCam(player.getPivot());
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		loadTextures();
		texTest1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png", Texture.NEAREST, Texture.REPEAT);
		texGrass = new Texture(GAME_FOLDER+"/res/tex/grass3.png", Texture.NEAREST, Texture.REPEAT);
		imgDoge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		imgDoge.setDimensions(50, 50);
		
		Texture tex1 = new Texture(GAME_FOLDER+"/res/tex/sand1.png");
		Texture tex2 = new Texture(GAME_FOLDER+"/res/tex/rock1.png");
		Texture tex3 = new Texture(GAME_FOLDER+"/res/tex/grassFlowers1.png");
		Texture tex4 = new Texture(GAME_FOLDER+"/res/tex/bark1.png");
		Texture tex5 = new Texture(GAME_FOLDER+"/res/tex/stoneRoad1.png");
		
		while(true){
			keyboard.update();
			Display.pollEvents();
			display.setCamLocation(player.getXGlobal()+player.getXCamLocal(), player.getYGlobal()+player.getYCamLocal(), player.getZGlobal()+player.getZCamLocal());
			
			drawCrosshair(12, 5);
			
			display.drawCube(3, 0.5f, 5, 1, tex1);
			display.drawCube(3, 1.5f, 5, 1, tex2);
			display.drawCube(3, 2.5f, 5, 1, tex3);
			display.drawCube(-7, 0.5f, 3, 1, tex2);
			display.drawCube(3, 0.5f, -1, 1, tex3);
			display.drawCube(-3, 0.5f, -4, 1, tex4);
			display.drawCube(25, 0.5f, 25, 1, tex5);
			display.drawCube(0, 0.5f, 0, 1, imgDoge.getTexture());
			display.drawCube(-1, -1, 3, 5, texGrass);
			
			drawFloor(texGrass);
			
			if(keyboard.isDown(GLFW_KEY_Z)){										
				imgDoge.setLocal(imgDoge.getWidth()/2, imgDoge.getHeight()/2);
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, imgDoge.getTexture(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, imgDoge.getTexture(), null));
			}

			if(keyboard.isDown(GLFW_KEY_W)){
				player.move(Sentient.Direction.FORWARD, (float)(player.getSpeedForward()*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_S)){
				player.move(Sentient.Direction.BACKWARD, (float)(player.getSpeedBackward()*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_A)){
				player.move(Sentient.Direction.LEFT, (float)(player.getSpeedStrafe()*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_D)){
				player.move(Sentient.Direction.RIGHT, (float)(player.getSpeedStrafe()*timePassed));
			}
			if(keyboard.wasPressed(GLFW_KEY_SPACE) && player.getYGlobal() == 0f){
				player.setYSpeedGlobal(0.05f);
				System.out.println("ayy lmao");
			}
				
			//update camera
			player.getPivot().setRotXZAxis(player.getPivot().getRotXZAxis()+(float)(display.getCursorRotXZAxisChange())*CURSOR_SPEED);
			player.getPivot().setRotYZAxis(player.getPivot().getRotYZAxis()+(float)(display.getCursorRotYZAxisChange())*CURSOR_SPEED);
			display.setPivotCam(player.getPivot());
			player.setYSpeedGlobal(player.getYSpeedGlobal()-((float)(WORLD_GRAVITY*pow(timePassed, 2))));
			
			//invoke gravity on player
			player.setYGlobal(player.getYGlobal()+player.getYSpeedGlobal());			
			if(player.getYGlobal() < WORLD_FLOOR){
				player.setYGlobal(WORLD_FLOOR);
				player.setYSpeedGlobalMax(0f);
			}
			
			System.out.println(display.getCursorRotXZAxisChange());
			
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			display.update();
			display.clear();
			updateTimer();
		}
	}
	
	private void drawCrosshair(int size, int spacing){
		VectorColor cInner = new VectorColor(1f, 0f, 0.5f);
		VectorColor cOuter = new VectorColor(0f, 0f, 0f);
		
		display.drawLine((display.getWidth()/2)-size-spacing, (display.getHeight()/2), (display.getWidth()/2)-spacing, (display.getHeight()/2), cOuter, cInner);
		display.drawLine((display.getWidth()/2), (display.getHeight()/2)-size-spacing, (display.getWidth()/2), (display.getHeight()/2)-spacing, cOuter, cInner);
		display.drawLine((display.getWidth()/2)+spacing, (display.getHeight()/2), (display.getWidth()/2)+size+spacing, (display.getHeight()/2), cInner, cOuter);
		display.drawLine((display.getWidth()/2), (display.getHeight()/2)+spacing, (display.getWidth()/2), (display.getHeight()/2)+size+spacing, cInner, cOuter);
	}
	
	private void drawFloor(Texture texFloor){
		display.drawQuadTextured3D(new QuadTextured3D(display.FAR, WORLD_FLOOR, display.FAR, display.FAR, WORLD_FLOOR, -display.FAR, -display.FAR, WORLD_FLOOR, -display.FAR, -display.FAR, WORLD_FLOOR, display.FAR, texFloor, null));
	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

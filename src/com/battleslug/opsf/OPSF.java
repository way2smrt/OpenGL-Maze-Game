package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
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
		player.setSpeed(10, 2, 3);
		//average height for a human
		player.setYCamLocal(1.7f);
		player.setPivot(new Pivot(0, 90, 0));
		
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
		
		final int LOCX = 100;
		final int LOCY = 100;
		
		while(true){
			keyboard.update();
			
			display.setCamRotZAxis(player.getRotationHorizontal());
			display.setCamLocation(player.getXGlobal()+player.getXCamLocal(), player.getYGlobal()+player.getYCamLocal(), player.getZGlobal()+player.getZCamLocal());
			
			drawCrosshair(2);
			
			display.drawCube(3, 0, 5, tex1);
			display.drawCube(-7, 0, 3, tex2);
			display.drawCube(3, 0, -1, tex3);
			display.drawCube(-3, 0, -4, tex4);
			display.drawCube(25, 0, 25, tex5);
			display.drawCube(0, 0, 0, imgDoge.getTexture());
			display.drawCube(-1, -1, 3, texGrass);
			
			drawFloor(texGrass);
			
			if(keyboard.isDown(GLFW_KEY_Z)){										
				imgDoge.setLocal(imgDoge.getWidth()/2, imgDoge.getHeight()/2);
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, imgDoge.getTexture(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, imgDoge.getTexture(), null));
			}

			if(keyboard.isDown(GLFW_KEY_W)){
				player.move(Player.Direction.FORWARD, (float)(player.getSpeedForward()*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_S)){
				player.move(Player.Direction.BACKWARD, (float)(player.getSpeedBackward()*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_A)){
				player.move(Player.Direction.LEFT, (float)(player.getSpeedStrafe()*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_D)){
				player.move(Player.Direction.RIGHT, (float)(player.getSpeedStrafe()*timePassed));
			}
			if(keyboard.wasPressed(GLFW_KEY_SPACE) && player.getYGlobal() == 0f){
				player.setYSpeedGlobal(0.05f);
				System.out.println("ayy lmao");
			}
				
			//update camera
			player.getPivot().setRotXAxis(player.getPivot().getRotXZAxis()+(float)(display.getCursorRotXAxisChange())*CURSOR_SPEED);
			display.setPivotCam(player.getPivot());
			player.setYSpeedGlobal(player.getYSpeedGlobal()-((float)(WORLD_GRAVITY*pow(timePassed, 2))));
			
			//gravity
			player.setYGlobal(player.getYGlobal()+player.getYSpeedGlobal());			
			if(player.getYGlobal() < WORLD_FLOOR){
				player.setYGlobal(WORLD_FLOOR);
				player.setYSpeedGlobalMax(0f);
			}
			
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			display.update();
			display.clear();
			updateTimer();
		}
	}
	
	private void drawCrosshair(int size){
		VectorColor cInner = new VectorColor(1f, 1f, 1f);
		VectorColor cOuter = new VectorColor(1f, 1f, 1f);
		
		//we add 1 to account for rounding
		display.drawLine((display.getWidth()/2)-size-1, (display.getHeight()/2), (display.getWidth()/2), (display.getHeight()/2), cOuter, cInner);
		display.drawLine((display.getWidth()/2), (display.getHeight()/2)-size-1, (display.getWidth()/2), (display.getHeight()/2), cOuter, cInner);
		display.drawLine((display.getWidth()/2), (display.getHeight()/2), (display.getWidth()/2)+size, (display.getHeight()/2), cInner, cOuter);
		display.drawLine((display.getWidth()/2), (display.getHeight()/2), (display.getWidth()/2), (display.getHeight()/2)+size, cInner, cOuter);
	}
	
	private void drawFloor(Texture texFloor){
		display.drawQuadTextured3D(new QuadTextured3D(display.FAR, WORLD_FLOOR, display.FAR, display.FAR, WORLD_FLOOR, -display.FAR, -display.FAR, WORLD_FLOOR, -display.FAR, -display.FAR, WORLD_FLOOR, display.FAR, texFloor, null));
	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
import com.battleslug.porcupine.*;

import static java.lang.Math.*;

public class OPSF extends Game {
	private Player player;
	
	private World world;
	
	private Texture tex_test1, tex_grass;
	private Image img_doge;
	
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
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		loadTextures();
		tex_test1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png", Texture.NEAREST, Texture.REPEAT);
		tex_grass = new Texture(GAME_FOLDER+"/res/tex/grass3.png", Texture.NEAREST, Texture.REPEAT);
		img_doge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		img_doge.setDimensions(50, 50);
		
		final int MUCH_DOGE = 1;
		
		Pivot pivot_doge = new Pivot();
		pivot_doge.addChild(new Pivot());
		pivot_doge.getChild(MUCH_DOGE).setRotation(90);
		
		Texture tex1 = new Texture(GAME_FOLDER+"/res/tex/sand1.png");
		Texture tex2 = new Texture(GAME_FOLDER+"/res/tex/rock1.png");
		Texture tex3 = new Texture(GAME_FOLDER+"/res/tex/grassFlowers1.png");
		Texture tex4 = new Texture(GAME_FOLDER+"/res/tex/bark1.png");
		Texture tex5 = new Texture(GAME_FOLDER+"/res/tex/stoneRoad1.png");
		
		final int LOCX = 100;
		final int LOCY = 100;
		
		while(true){
			keyboard.update();
			
			display.setCamHorizontalRot(player.getRotationHorizontal());
			display.setCamLocation(player.getXGlobal()+player.getXCamLocal(), player.getYGlobal()+player.getYCamLocal(), player.getZGlobal()+player.getZCamLocal());
			
			drawCrosshair(3);
			drawFloor(tex_grass);
			
			display.drawCube(3, 0, 5, tex1);
			display.drawCube(-7, 0, 3, tex2);
			display.drawCube(3, 0, -1, tex3);
			display.drawCube(-3, 0, -4, tex4);
			display.drawCube(25, 0, 25, tex5);
			display.drawCube(0, 0, 0, img_doge.getTexture());
			display.drawCube(-1, -1, 3, tex_grass);
			
			if(keyboard.isDown(GLFW_KEY_Z)){								
				img_doge.draw(display, LOCX, LOCY, pivot_doge.getRotation());
				img_doge.draw(display, LOCX+100, LOCY, pivot_doge.getChild(MUCH_DOGE).getRotation());
				
				img_doge.setLocal(img_doge.getWidth()/2, img_doge.getHeight()/2);
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, img_doge.getTexture(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, img_doge.getTexture(), null));
			}
			if(keyboard.isDown(GLFW_KEY_X)){
				//1 rotation per second
				pivot_doge.setRotation(pivot_doge.getRotation()+new Double(timePassed*Circle.DEGREES).intValue());
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
				player.setYSpeedGlobal(0.03f);
				System.out.println("ayy lmao");
			}
				
			player.setRotationHorizontal(player.getRotationHorizontal()+(float)(display.getCursorRotHoriChange())*CURSOR_SPEED);
			player.setYSpeedGlobal(player.getYSpeedGlobal()-((float)(WORLD_GRAVITY*pow(timePassed, 2))));
			
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
	
	private void drawCrosshair(int width){
		//TODO fix crosshair, not drawing. Maybe 2D drawing problem.
		display.drawPixel(display.getWidth()/2, display.getHeight()/2, new VectorColor(1f, 1f, 1f));
		for(int i = 1; i != width+1; i++){
			display.drawPixel((display.getWidth()/2)+i, (display.getHeight()/2), new VectorColor(1f, 1f, 1f));
			display.drawPixel((display.getWidth()/2)-i, (display.getHeight()/2), new VectorColor(1f, 1f, 1f));
			display.drawPixel((display.getWidth()/2), (display.getHeight()/2)+i, new VectorColor(1f, 1f, 1f));
			display.drawPixel((display.getWidth()/2), (display.getHeight()/2)-i, new VectorColor(1f, 1f, 1f));
		}	
	}
	
	private void drawFloor(Texture tex_floor){
		display.drawQuadTextured3D(new QuadTextured3D(10, WORLD_FLOOR, 10, 10, WORLD_FLOOR, -10, -10, WORLD_FLOOR, -10, -10, WORLD_FLOOR, 10, tex_floor, null));
		display.drawQuadTextured3D(new QuadTextured3D(-10, WORLD_FLOOR, -10, -10, WORLD_FLOOR, 10, 10, WORLD_FLOOR, 10, 10, WORLD_FLOOR, -10, tex_floor, null));
	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.sentient.*;
import com.battleslug.flare.world.*;
import com.battleslug.porcupine.*;
import com.battleslug.porcupine.Display.DrawMode;

public class OPSF extends Game {	
	private World world;
	
	private Texture tex_test1, tex_grass;
	private Image img_doge;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	public OPSF(){
	}
	
	@Override 
	public void play(){
		display = new Display("Operation Solar Fury", 640, 480, false);
		display.create();
		//display.show();
		
		player = new Player();
		player.setSpeed(1f, 0.5f, 1.5f);
		player.setXSpeedGlobalMax(10f);
		player.setYSpeedGlobalMax(10f);
		player.setZSpeedGlobalMax(10f);
		player.setLocation(0f, 0f, 0f);
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		loadTextures();
		tex_test1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png");
		tex_grass = new Texture(GAME_FOLDER+"/res/tex/grass2.png");
		img_doge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		img_doge.setDimensions(50, 50);
		
		final int MUCH_DOGE = 1;
		
		Pivot pivot_doge = new Pivot();
		pivot_doge.addChild(new Pivot());
		pivot_doge.getChild(MUCH_DOGE).setRotation(90);
		
		float xRot = 0;
		
		while(true){
			final int LOCX = 100;
			final int LOCY = 100;
			
			keyboard.update();
			
			display.setCamHorizontalRot(xRot);
			display.setCamLocation(player.getXGlobal(), player.getYGlobal(), player.getZGlobal());
			
			display.coolTestShit(tex_test1, tex_grass);
			
			if(keyboard.isDown(GLFW_KEY_Z)){								
				img_doge.draw(display, LOCX, LOCY, pivot_doge.getRotation());
				img_doge.draw(display, LOCX+100, LOCY, pivot_doge.getChild(MUCH_DOGE).getRotation());
				
				img_doge.setLocal(img_doge.getWidth()/2, img_doge.getHeight()/2);
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, tex_test1, null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, tex_test1, null));
			}
			if(keyboard.isDown(GLFW_KEY_X)){
				//1 rotation per second
				pivot_doge.setRotation(pivot_doge.getRotation()+new Double(timePassed*Circle.DEGREES).intValue());
				System.out.println(pivot_doge.getRotation());
			}
			
			final float TRACTION = 1f;
			
			//z movement
			if(keyboard.isDown(GLFW_KEY_W)){
				player.setZSpeedGlobal(player.getZSpeedGlobal()+(float)(player.getSpeedForward()*timePassed));	
			}
			else if(keyboard.isDown(GLFW_KEY_S)){
				player.setZSpeedGlobal(player.getZSpeedGlobal()-(float)(player.getSpeedBackward()*timePassed));	
			}
			else {
				if (player.getXSpeedGlobal() > 0){
					if(player.getZSpeedGlobal()-player.getSpeedForward()/TRACTION*timePassed >= 0){
						player.setZSpeedGlobal(player.getZSpeedGlobal()-(float)(player.getSpeedForward()/TRACTION*timePassed));
					}
					else {
						player.setZSpeedGlobal(0f);
					}
				}
				else if(player.getZSpeedGlobal() < 0){
					if(player.getZSpeedGlobal()+player.getSpeedBackward()/TRACTION*timePassed <= 0){
						player.setZSpeedGlobal(player.getZSpeedGlobal()+(float)(player.getSpeedBackward()/TRACTION*timePassed));
					}
					else {
						player.setZSpeedGlobal(0f);
					}
				}
			}
			
			//x movement
			if(keyboard.isDown(GLFW_KEY_A)){
				player.setXSpeedGlobal(player.getXSpeedGlobal()-(float)(player.getSpeedStrafe()*timePassed));	
			}
			else if(keyboard.isDown(GLFW_KEY_D)){
				player.setXSpeedGlobal(player.getXSpeedGlobal()+(float)(player.getSpeedStrafe()*timePassed));	
			}
			else {
				if (player.getXSpeedGlobal() > 0){
					if(player.getXSpeedGlobal()-player.getSpeedStrafe()/TRACTION*timePassed >= 0){
						player.setXSpeedGlobal(player.getXSpeedGlobal()-(float)(player.getSpeedStrafe()/TRACTION*timePassed));
					}
					else {
						player.setXSpeedGlobal(0f);
					}
				}
				else if(player.getXGlobal() < 0){
					if(player.getXSpeedGlobal()+player.getSpeedStrafe()/TRACTION*timePassed <= 0){
						player.setXSpeedGlobal(player.getXSpeedGlobal()+(float)(player.getSpeedStrafe()/TRACTION*timePassed));
					}
					else {
						player.setXSpeedGlobal(0f);
					}
				}
			}
			
			if(keyboard.isDown(GLFW_KEY_R)){
				player.setLocation(player.getXGlobal(), player.getYGlobal()-player.getSpeedForward()*(float)timePassed, player.getZGlobal());
			}
			if(keyboard.isDown(GLFW_KEY_F)){
				player.setLocation(player.getXGlobal(), player.getYGlobal()+player.getSpeedForward()*(float)timePassed, player.getZGlobal());
			}
			if(keyboard.isDown(GLFW_KEY_Q)){
				xRot -= 50*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_E)){
				xRot += 50*timePassed;
			}
			
			player.setLocation(player.getXGlobal()+(float)(player.getXSpeedGlobal()*timePassed), player.getYGlobal()+(float)(player.getYSpeedGlobal()*timePassed), player.getZGlobal()+(float)(player.getZSpeedGlobal()*timePassed));
			
			System.out.println(player.getXSpeedGlobal());
			
			if(keyboard.wasPressed(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			display.update();
			display.clear();
			updateTimer();
		}

	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

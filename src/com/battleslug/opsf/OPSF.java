package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
import com.battleslug.porcupine.*;

public class OPSF extends Game {
	private Player player;
	
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
		player.setSpeed(5, 2, 3);
		
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
		
		float camY = 0;
		
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
			display.setCamLocation(player.getXGlobal(), camY, player.getZGlobal());
			
			display.coolTestShit(tex_test1, tex_grass);
				
			display.drawCube(3, 0, 5, tex1);
			display.drawCube(-7, 0, 3, tex2);
			display.drawCube(3, 0, -1, tex3);
			display.drawCube(-3, 0, -4, tex4);
			display.drawCube(25, 0, 25, tex5);
			
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
			if(keyboard.isDown(GLFW_KEY_R)){
				camY += 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_F)){
				camY -= 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_Q)){
				player.setRotationHorizontal((float)(player.getRotationHorizontal()-75*timePassed));
			}
			if(keyboard.isDown(GLFW_KEY_E)){
				player.setRotationHorizontal((float)(player.getRotationHorizontal()+75*timePassed));
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

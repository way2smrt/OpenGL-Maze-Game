package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.sentient.*;
import com.battleslug.flare.world.*;
import com.battleslug.porcupine.*;
import com.battleslug.porcupine.Display.DrawMode;

public class OPSF extends Game {
	private Sentient player;
	
	private World world;
	
	private Texture tex_test1;
	private Image img_doge;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	public OPSF(){
	}
	
	@Override 
	public void play(){
		display = new Display("Operation Solar Fury", 640, 480, false);
		display.create();
		//display.show();
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		loadTextures();
		tex_test1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png");
		img_doge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		img_doge.setDimensions(50, 50);
		
		final int MUCH_DOGE = 1;
		
		Pivot pivot_doge = new Pivot();
		pivot_doge.addChild(new Pivot());
		pivot_doge.getChild(MUCH_DOGE).setRotation(90);
		
		float camX = 0;
		float camY = 0;
		float camZ = 0;
		
		float xRot = 0;
		
		while(true){
			final int LOCX = 100;
			final int LOCY = 100;
			
			keyboard.update();
			
			display.setCamHorizontalRot(xRot);
			display.setCamLocation(camX, camY, camZ);
			
			display.coolTestShit(tex_test1);
			
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
				camX += 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_S)){
				camX -= 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_A)){
				camZ += 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_D)){
				camZ -= 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_R)){
				camY += 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_F)){
				camY -= 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_Q)){
				xRot -= 50*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_E)){
				xRot += 50*timePassed;
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

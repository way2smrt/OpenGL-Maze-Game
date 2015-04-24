package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.sentient.*;
import com.battleslug.flare.world.*;
import com.battleslug.porcupine.*;

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
		display = new Display("Operation Solar Fury", 1024, 900, true);
		display.create();
		//display.show();
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		loadTextures();
		tex_test1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png");
		img_doge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		img_doge.setDimensions(50, 50);
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		final int MUCH_DOGE = 1;
		
		Pivot pivot_doge = new Pivot();
		pivot_doge.addChild(new Pivot());
		pivot_doge.getChild(MUCH_DOGE).setRotation(90);
		
		float angle = 0;
		float xLoc = 0;
		float yLoc = 0;
		float zLoc = 0;
		
		while(true){
			final int LOCX = 100;
			final int LOCY = 100;
			
			keyboard.update();
			
			display.coolTestShit(tex_test1, 30, xLoc, yLoc, zLoc);
			
			if(keyboard.isDown(GLFW_KEY_Z)){
				img_doge.draw(display, LOCX, LOCY, pivot_doge.getRotation());
				img_doge.draw(display, LOCX+100, LOCY, pivot_doge.getChild(MUCH_DOGE).getRotation());
				//display.drawTexturedQuad(new TexturedQuad(0, 0, 150, 750, 650, 370, 400, -200, tex_test1, null));
				img_doge.setLocal(img_doge.getWidth()/2, img_doge.getHeight()/2);
				
				display.drawTexturedQuad(new TexturedQuad(0, 0, 200, 200, tex_test1, null));
				display.drawTexturedQuad(new TexturedQuad(200, 200, 400, 0, tex_test1, null));
			}
			if(keyboard.isDown(GLFW_KEY_X)){
				//1 rotation per second
				pivot_doge.setRotation(pivot_doge.getRotation()+new Double(timePassed*Circle.DEGREES).intValue());
				System.out.println(pivot_doge.getRotation());
			}
			
			
			if(keyboard.isDown(GLFW_KEY_W)){
				xLoc += 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_S)){
				xLoc -= 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_A)){
				yLoc += 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_D)){
				yLoc -= 1*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_Q)){
				zLoc += 10*timePassed;
			}
			if(keyboard.isDown(GLFW_KEY_E)){
				zLoc -= 10*timePassed;
			}
			
			//display.drawRectangle(0, 0, 500, 500, new VectorColor(0.5f, 0.3f, 0.8f));
			display.update();
			display.clear();
			updateTimer();
		}

	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

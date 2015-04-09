package com.battleslug.unitystrike;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.usengine.*;
import com.battleslug.usengine.display.Display;
import com.battleslug.usengine.display.Texture;
import com.battleslug.usengine.display.TexturedQuad;
import com.battleslug.usengine.display.VectorColor;
import com.battleslug.usengine.sentient.Sentient;
import com.battleslug.usengine.world.World;

public class UnityStrike extends Game {
	private Display display;
	
	private Sentient player;
	
	private World world;
	
	private Texture tex_test1, tex_doge;
	
	int rx = 0;
	int ry = 0;
	
	public UnityStrike(){
		super("Unity-Strike");
	}
	
	@Override 
	public void play(){	
		display = new Display("Cool Swaggy Stuff That Doesn't Work Properly Yet", 640, 640);
		display.create();
		
		tex_test1 = loadTexture("misc/test.png");
		tex_doge = loadTexture("misc/doge.png");
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		rx = 200;
		ry = 200;
		
		while(true){
			keyboard.update();
			
			if(keyboard.isDown(GLFW_KEY_A)){
				display.drawTexturedQuad(new TexturedQuad(0, 0, 0, 200, 200, 200, 200, 0, tex_doge, null));  		
			}
			
			display.drawTexture(tex_doge, rx, ry);
			display.drawRectangle(rx, ry, rx+64, ry+64, new VectorColor(0.8f, 0.3f, 0.5f));
			display.drawRectangle(0, 0, 500, 500, new VectorColor(0.5f, 0.3f, 0.8f));
			display.update();
			display.clear();
		}

	}
	
	private void moveUp(){
		ry -= 32;
	}
	
	private void moveDown(){
		ry += 32;
	}

	private void moveLeft(){
		rx -= 32;
	}

	private void moveRight(){
		rx += 32;
	}
	
	public static void main(String[] args) {
        new UnityStrike().play();
    }
}

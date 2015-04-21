package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.*;
import com.battleslug.flare.sentient.Sentient;
import com.battleslug.flare.world.World;
import com.battleslug.logl2d.*;

public class OPSF extends Game {
	private Sentient player;
	
	private World world;
	
	private Texture tex_test1, tex_doge;
	private Image img_doge;
	
	public OPSF(){
		super("Unity-Strike");
	}
	
	@Override 
	public void play(){	
		tex_test1 = loadTexture("misc/test.png");
		tex_doge = loadTexture("misc/doge.png");
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		img_doge = new Image(tex_doge);
		
		int rotation = 100;
		while(true){
			keyboard.update();
			
			if(keyboard.isDown(GLFW_KEY_A)){
				display.drawTexturedQuad(new TexturedQuad(0, 0, 0, 100, 100, 100, 100, 0, tex_doge, null));
				img_doge.draw(display, 100, 100, rotation);
			}
			if(keyboard.wasReleased(GLFW_KEY_D)){
				rotation += 30;
			}
			
			System.out.println(rotation);
			display.drawRectangle(0, 0, 500, 500, new VectorColor(0.5f, 0.3f, 0.8f));
			display.update();
			display.clear();
		}

	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

package com.battleslug.opsf;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
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
		super("OPSF");
	}
	
	@Override 
	public void play(){	
		display.setTitle("Operation Solar Flare");
		
		tex_test1 = loadTexture("misc/test.png");
		tex_doge = loadTexture("misc/doge.png");
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		img_doge = new Image(tex_doge);
		img_doge.setDimensions(50, 50);
		
		int rotation = 100;
		while(true){
			final int LOCX = 100;
			final int LOCY = 100;
			
			keyboard.update();
			
			if(keyboard.isDown(GLFW_KEY_A)){
				display.drawTexturedQuad(new TexturedQuad(0, 0, 0, 100, 100, 100, 100, 0, tex_doge, null));
				img_doge.draw(display, LOCX, LOCY, rotation);
				img_doge.setLocal(img_doge.getWidth()/2, img_doge.getHeight()/2);
			}
			if(keyboard.isDown(GLFW_KEY_D)){
				rotation += 2;
			}
			
			display.drawRectangle(0, 0, 500, 500, new VectorColor(0.5f, 0.3f, 0.8f));
			display.update();
			display.clear();
		}

	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

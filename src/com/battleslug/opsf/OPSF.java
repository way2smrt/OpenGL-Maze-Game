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
	
	private Texture tex_test1;
	private Image img_doge;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	public OPSF(){
	}
	
	@Override 
	public void play(){
		display = new Display("Operation Solar Flare", 640, 640);
		display.create();
		//display.show();
		
		world = new World();
		world.bind(display);
		
		keyboard.bind(display);
		
		loadTextures();
		tex_test1 = new Texture(GAME_FOLDER+"/res/tex/rocksFine1.png");
		img_doge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		img_doge.setDimensions(50, 50);
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		int rotation = 100;
		while(true){
			final int LOCX = 100;
			final int LOCY = 100;
			
			keyboard.update();
			
			if(keyboard.isDown(GLFW_KEY_A)){
				img_doge.draw(display, LOCX, LOCY, rotation);
				display.drawTexturedQuad(new TexturedQuad(0, 0, 0, 500, 500, 500, 500, 0, tex_test1, null));
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
        new OPSF().init();
    }
}

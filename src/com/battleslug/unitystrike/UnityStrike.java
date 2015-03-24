package com.battleslug.unitystrike;

import com.battleslug.usengine.Display;
import com.battleslug.usengine.Game;
import com.battleslug.usengine.Sentient;
import com.battleslug.usengine.Texture;
import com.battleslug.usengine.TexturedQuad;
import com.battleslug.usengine.Timer;
import com.battleslug.usengine.World;

public class UnityStrike extends Game {
	private Display display;
	
	private Sentient player;
	
	private Timer timer;
	
	private World world;
	
	private Texture tex_test1;
	
	public UnityStrike(){
		super("Unity-Strike");
	}
	
	@Override 
	public void play(){
		timer = new Timer();
		
		display = new Display("Test", 640, 480);
		display.create();
		
		tex_test1 = loadTexture("misc/test.png");
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		
		while(true){
			timer.update();
			
			display.drawTexturedQuad(new TexturedQuad(0, 0, 25, 450, 470, 510, 530, 120, tex_test1, null));
			
			display.update();
			display.clear();
			
		}
	}

	
	public static void main(String[] args) {
        new UnityStrike().play();
    }
}

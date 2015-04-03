package com.battleslug.unitystrike;

import com.battleslug.usengine.*;

public class UnityStrike extends Game {
	private Display display;
	
	private Sentient player;
	
	private Timer timer;
	
	private World world;
	
	private Texture tex_test1, tex_doge;
	
	public UnityStrike(){
		super("Unity-Strike");
	}
	
	@Override 
	public void play(){
		timer = new Timer();
		
		display = new Display("Test", 640, 480);
		display.create();
		
		tex_test1 = loadTexture("misc/test.png");
		tex_doge = loadTexture("misc/doge.png");
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		while(true){
			tex_doge.setScale(4f, 4f);
			display.drawTexture(tex_doge, 0, 0);
			display.update();
			display.clear();
		}

	}

	
	public static void main(String[] args) {
        new UnityStrike().play();
    }
}

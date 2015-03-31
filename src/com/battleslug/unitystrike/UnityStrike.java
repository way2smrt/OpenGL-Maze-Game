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
		
		display = new Display("Test", 1280, 1024);
		display.create();
		
		tex_test1 = loadTexture("misc/test.png");
		tex_doge = loadTexture("misc/doge.png");
		
		player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		
		float rot = 0;
		
		while(true){
			tex_doge.setRotation(new Float(rot).intValue());
			tex_doge.setScale(1.75f, 0.5f);
			display.drawTexture(tex_doge, 0, 0);
			display.drawTexture(tex_doge, 400, 0);
			display.drawTexture(tex_doge, 800, 0);
			display.drawTexture(tex_doge, 400, 400);
			display.drawTexture(tex_doge, 800, 800);
			display.drawTexture(tex_doge, 400, 800);
			display.drawTexture(tex_doge, 800, 400);

			//quick fox, do a barrel roll!
			rot += 0.0000004*timer.getTimePassedNanos();
			
			display.update();
			display.clear();
			timer.update();
		}
	}

	
	public static void main(String[] args) {
        new UnityStrike().play();
    }
}

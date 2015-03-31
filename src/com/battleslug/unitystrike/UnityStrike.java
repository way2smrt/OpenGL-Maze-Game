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
		
		
<<<<<<< HEAD
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
=======
		int rot = 0;
		while(true){
			timer.update();
			
			tex_doge.setRotation(rot);

			rot += 3; 
>>>>>>> 5b7bde91c14779b396e7237316a61bc03e662d20
			
			display.drawTexture(tex_doge, 100, 100);
			display.update();
			display.clear();
<<<<<<< HEAD
			timer.update();
=======
>>>>>>> 5b7bde91c14779b396e7237316a61bc03e662d20
		}
	}

	
	public static void main(String[] args) {
        new UnityStrike().play();
    }
}

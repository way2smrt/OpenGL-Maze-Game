package com.battleslug.unitystrike;

import com.battleslug.usengine.*;

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
		
		display = new Display("Test", 1024, 1024, false);
		display.setHint(Display.DECORATED, false);
		display.create();
		
		tex_test1 = new Texture("res/tex/brick1.png");
		
	    player = new Sentient("Player", tex_test1, 200, 20);
		player.setHealth(125);
		
		world = new World();
		world.bind(display);
		
		timer.update();
		
		int size = 1024;
		while(true){
			for(int x = 0; (x+size)<=display.getWidth(); x+=size){
				for(int y = 0; (y+size)<=display.getHeight(); y+=size){
					display.drawTexturedQuad(new TexturedQuad(x, y, x, y+size, x+size, y+size, x+size, y, tex_test1, null));
				}
			}
			size -= 1;
			
			if (size <= 8){
				display.kill();
			}
			
			display.update();
			display.clear();
			timer.update();
		}
	}

	
	public static void main(String[] args) {
        new UnityStrike().play();
    }
}

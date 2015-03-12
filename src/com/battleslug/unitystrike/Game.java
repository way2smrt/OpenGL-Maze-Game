package com.battleslug.unitystrike;

public class Game {	
	private Display displayMain;
	private Sentient player;
	
	public Game(){
		displayMain = new Display("Test", 640, 480);
		displayMain.create();
		
		player = new Sentient("Player", new Texture("res/misc/test.png", Texture.CLAMP_TO_EDGE, Texture.LINEAR), 200, 20);
		player.setLocation(200, 200);
		player.setHealth(125);
		
		while(true){
			displayMain.update();
			displayMain.clear();
			displayMain.drawTexture(player.getTexture(), 200, 200);
		}
	}
	
	public static void main(String[] args) {
        new Game();
    }
}

package com.battleslug.unitystrike;

public class Game {
	private Display displayMain;
	private KeyActionBinder displayMainKeyboard;
	
	public Game(){
		displayMain = new Display("The swaggiest possible game", 640, 480);
		displayMain.create();
		
		displayMainKeyboard = new KeyActionBinder(displayMain.getWindowID());
		
		while(true){
			displayMain.update();
			displayMain.clear();
		}
	}
	
	public static void main(String[] args) {
        new Game();
    }
}

package com.battleslug.unitystrike;

public class HUD {
	private Display display;
	private Sentient sentient;
	
	private static final int HUD_OBJECTS = 1;
	
	public static final Texture HEART = new Texture("res/hud/heart.png");
	
	public static final int HUD_HEALTH = 0;
	
	private boolean[] objectDisplay = new boolean[HUD_OBJECTS];
	private int[] objectX = new int[HUD_OBJECTS];
	private int[] objectY = new int[HUD_OBJECTS];
	private int[] objectWidth = new int[HUD_OBJECTS];
	
	
	public HUD(Display display, Sentient sentient){
		this.display = display;
		this.sentient = sentient;
	}
	
	public void enable(int HUDObject){
		switch(HUDObject) {
			case HUD_HEALTH:
				objectDisplay[HUD_HEALTH] = true;
				break;
		}
	}
	
	public void disable(int HUDObject){
		switch(HUDObject) {
			case HUD_HEALTH:
				objectDisplay[HUD_HEALTH] = false;
				break;
		}
	}
	
	public void setConditions(int HUDObject, int x, int y, int width){
		switch(HUDObject) {
		case HUD_HEALTH:
			objectX[HUD_HEALTH] = x;
			objectY[HUD_HEALTH] = y;
			objectWidth[HUD_HEALTH] = width;
			break;
		}
	}
	
	public void rebind(Sentient sentient){
		this.sentient = sentient;
	}
	
	public void display(){	
		if (objectDisplay[HUD_HEALTH]){
			int hearts = objectWidth[HUD_HEALTH] / HEART.getWidth();
			//health per heart
			int hph = sentient.getMaxHealth() / hearts;
			
			for(int i = 0; i != hearts; i++){
				if(sentient.getHealth() >= hph*(i+1)){
					display.drawTexture(HEART, (0+i*HEART.getWidth())+objectX[HUD_HEALTH], objectY[HUD_HEALTH]);
				}
				else{
					return;
				}
			}
		}
	}
}

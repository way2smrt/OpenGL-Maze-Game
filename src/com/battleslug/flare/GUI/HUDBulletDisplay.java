package com.battleslug.flare.GUI;

import com.battleslug.porcupine.*;
import com.battleslug.flare.item.Weapon;

public class HUDBulletDisplay extends GUIObject {
	private int bulletFiredX, bulletFiredY, bulletFiredRot = 0;
	
	private Weapon weapon;
	private Texture bulletTex;
	
	private int bulletCurr, bullets, bulletsLast;
	
	private Image bulletImg;
	
	public HUDBulletDisplay(int x, int y, int width, int height, Weapon weapon, Texture bulletTex){
		super(x, y, width, height);
		
		this.weapon = weapon;
		this.bulletTex = bulletTex;
		
		bulletImg = new Image(bulletTex, bulletTex.getWidth(), bulletTex.getHeight(), bulletTex.getWidth()/2, bulletTex.getHeight()/2);
	}
	
	public void draw(){
		bulletsLast = bullets;
		bullets = weapon.getBullets();
		bulletCurr = bullets;
		
		if(bullets == bulletsLast){			
			bulletFiredY += 1000*display.getTimePassed();
			bulletFiredX += 350*display.getTimePassed();
			bulletFiredRot += 700*display.getTimePassed();
			
			//TODO image not drawing
			if(bulletImg != null){
				bulletImg.draw(display, bulletFiredX, bulletFiredY, bulletFiredRot);
			}
			
			//display.drawQuadTextured2D(new QuadTextured2D(bulletFiredX, bulletFiredY, bulletFiredX+bulletTex.getWidth(), bulletFiredY+bulletTex.getHeight(), bulletTex, null));
		}
		
		for(int cy = y; cy+bulletTex.getHeight() <= y+height && bulletCurr != 0; cy+=bulletTex.getHeight()){
			for(int cx = x; cx+bulletTex.getWidth() <= x+width && bulletCurr != 0; cx+=bulletTex.getWidth()){
				display.drawQuadTextured2D(new QuadTextured2D(cx, cy, cx+bulletTex.getWidth(), cy+bulletTex.getHeight(), bulletTex, null));
				
				if(bullets < bulletsLast && bulletCurr == 1){
					bulletFiredX = cx+bulletTex.getWidth();
					bulletFiredY = cy;
					bulletFiredRot = 0;
				}
				
				bulletCurr -= 1;
			}
		}
	}
}

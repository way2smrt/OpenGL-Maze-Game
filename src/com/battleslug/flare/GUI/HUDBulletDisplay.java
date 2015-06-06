package com.battleslug.flare.GUI;

import com.battleslug.flare.item.Weapon;
import com.battleslug.flare.item.WeaponInstance;
import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

public class HUDBulletDisplay extends GUIObject {
	private int bulletFiredX, bulletFiredY, bulletFiredRot = 0;
	private int bulletFiredX2, bulletFiredY2, bulletFiredRot2 = 0;
	private int bulletFiredX3, bulletFiredY3, bulletFiredRot3 = 0;
	
	private WeaponInstance weapon;
	private Texture bulletTex;
	
	private int bulletCurr, bullets, bulletsLast;
	
	private Image bulletImg;
	
	private VectorColor textBackColor;
	
	public HUDBulletDisplay(Point p, int width, int height, WeaponInstance weaponInstance, Texture bulletTex, VectorColor textBackColor){
		super(p, width, height);
		
		this.weapon = weaponInstance;
		this.bulletTex = bulletTex;
		
		bulletImg = new Image(bulletTex, bulletTex.getWidth(), bulletTex.getHeight(), bulletTex.getWidth()/2, bulletTex.getHeight()/2);
	}
	
	public void draw(){
		bulletsLast = bullets;
		bullets = weapon.getBullets();
		bulletCurr = bullets;
		
		if(bullets == bulletsLast){			
			bulletFiredY += 1000*display.getTimePassed();
			bulletFiredX += 250*display.getTimePassed();
			bulletFiredRot += 700*display.getTimePassed();
			bulletFiredY2 += 1000*display.getTimePassed();
			bulletFiredX2 += 250*display.getTimePassed();
			bulletFiredRot2 += 700*display.getTimePassed();
			bulletFiredY3 += 1000*display.getTimePassed();
			bulletFiredX3 += 250*display.getTimePassed();
			bulletFiredRot3 += 700*display.getTimePassed();
			
			//TODO last bullet not drawing
			if(bulletImg != null){
				display.drawImage(bulletImg, bulletFiredX, bulletFiredY, bulletFiredRot);
				display.drawImage(bulletImg, bulletFiredX2, bulletFiredY2, bulletFiredRot2);
				display.drawImage(bulletImg, bulletFiredX3, bulletFiredY3, bulletFiredRot3);
			}
		}
		
		for(int cy = (int)(p.getY()); cy+bulletTex.getHeight() <= p.getY()+height && bulletCurr != 0; cy+=bulletTex.getHeight()){
			for(int cx = (int)(p.getX()); cx+bulletTex.getWidth() <= p.getX()+width && bulletCurr != 0; cx+=bulletTex.getWidth()){
				display.drawQuadTextured2D(new QuadTextured2D(cx, cy, cx+bulletTex.getWidth(), cy+bulletTex.getHeight(), bulletTex, null));
				
				if(bullets < bulletsLast && bulletCurr == 1){
					bulletFiredX3 = bulletFiredX2;
					bulletFiredY3 = bulletFiredY2;
					bulletFiredRot3 = bulletFiredRot2;
					
					bulletFiredX2 = bulletFiredX;
					bulletFiredY2 = bulletFiredY;
					bulletFiredRot2 = bulletFiredRot;
					
					bulletFiredX = cx+bulletTex.getWidth();
					bulletFiredY = cy;
					bulletFiredRot = 0;
				}
				
				bulletCurr -= 1;
			}
		}
		
		display.setTextDrawOrigin(p);
		display.drawText(new Integer(weapon.getBullets()).toString()+"/"+new Integer(weapon.getWeapon().getAmmoMax()).toString(), width, 16, 38, textBackColor);
	}
}

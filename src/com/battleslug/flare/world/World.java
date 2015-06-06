package com.battleslug.flare.world;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

import com.battleslug.flare.item.*;

import java.util.Arrays;

public class World {	
	private Display display;
	
	private Bullet[] bullet;
	
	public float groundHeight = 0f;
	
	public float gravity = 9.81f;
	
	public WeaponSystem weaponSystem;
	
	public World(){
		bullet = new Bullet[0];
		
		weaponSystem = new WeaponSystem();
	}
	
	public WeaponSystem getWeaponSystem(){
		return weaponSystem;
	}
	
	public void addBullet(Bullet bullet){
		this.bullet = Arrays.copyOf(this.bullet, this.bullet.length+1);
		
		this.bullet[this.bullet.length-1] = bullet;
	}
	
	public void bind(Display display){
		this.display = display;
	}
	
	private void drawBullets(){
		for(int i = 0; i != bullet.length; i++){
			//TODO fix bullet drawing
			display.drawLine3D(bullet[i].getObjectWorldData().getPoint(), new Point(
					bullet[i].getObjectWorldData().getPoint().getX()+bullet[i].getObjectWorldData().getSpeed().getXSpeed(),
					bullet[i].getObjectWorldData().getPoint().getY()+bullet[i].getObjectWorldData().getSpeed().getYSpeed(),
					bullet[i].getObjectWorldData().getPoint().getZ()+bullet[i].getObjectWorldData().getSpeed().getZSpeed()
					), new VectorColor(1f, 0f, 0f), new VectorColor(1f, 0f, 0f));
		}
	}
	
	private void updateBullets(double timePassed){
		for(int i = 0; i != bullet.length; i++){
			bullet[i].getObjectWorldData().getPoint().update(bullet[i].getObjectWorldData().getSpeed(), timePassed);
		
			if(bullet[i].getDamage() <= 0){
				bullet[i] = null;
			}
		}
	}
	
	public void draw(){
		drawBullets();
	}
	
	public void update(double timePassed){
		updateBullets(timePassed);
	}
	
	public void setGravity(float gravity){
		this.gravity = gravity;
	}
	
	public float getGravity(){
		return gravity;
	}
}

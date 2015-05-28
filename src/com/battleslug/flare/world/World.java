package com.battleslug.flare.world;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

import java.util.Arrays;

public class World {	
	private Display display;
	
	private Bullet[] bullet;
	
	public World(){
		bullet = new Bullet[0];
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
					bullet[i].getObjectWorldData().getPoint().getX()+bullet[i].getObjectWorldData().getPoint().getXSpeed(),
					bullet[i].getObjectWorldData().getPoint().getY()+bullet[i].getObjectWorldData().getPoint().getYSpeed(),
					bullet[i].getObjectWorldData().getPoint().getZ()+bullet[i].getObjectWorldData().getPoint().getZSpeed()
					), new VectorColor(1f, 0f, 0f), new VectorColor(1f, 0f, 0f));
		}
	}
	
	private void updateBullets(double timePassed){
		for(int i = 0; i != bullet.length; i++){
			bullet[i].getObjectWorldData().getPoint().update(timePassed);
		
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
}

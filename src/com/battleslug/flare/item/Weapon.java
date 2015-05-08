package com.battleslug.flare.item;

import com.battleslug.flare.world.Bullet;

public class Weapon extends Item{
	private int damage;
	
	private int clipSize;
	private int clipCurr;
	
	private double fireTimeLast;
	private float fireDelay;
	
	private AmmoType ammoType;
	public enum AmmoType{Pistol, SMG, Shotgun, Rifle}
	
	public enum FireMode{Automatic, Semiautomatic};
	private FireMode fireMode;
	
	public Weapon(String name, String description, FireMode fireMode, int damage, int clipSize){
		super(name, description);
		
		this.damage = damage;
		this.clipCurr = clipSize;
		this.clipSize = clipSize;
		this.fireMode = fireMode;
		
		fireDelay = 0.1f;
	}
	
	public void setFireDelay(int fireDelay){
		this.fireDelay = fireDelay;
	}
	
	public float getFireDelay(){
		return fireDelay;
	}
	
	public FireMode getFireMode(){
		return fireMode;
	}
	
	public AmmoType getAmmoType(){
		return ammoType;
	}
	
	public boolean hasBullets(){
		if(clipCurr != 0){
			return true;
		}
		return false;
	}
	
	public boolean canShoot(double currTime){
		if(fireTimeLast+fireDelay <= currTime){
			return true;
		}
		return false;
	}
	
	public boolean isClipFull(){
		if(clipCurr == clipSize){
			return true;
		}
		return false;
	}
	
	public Bullet shoot(int x, int y, int xSpeed, int ySpeed, double currTime){
		fireTimeLast = currTime;
		
		if(clipCurr != 0){
			clipCurr -= 1;
			return new Bullet(x, y, xSpeed, ySpeed, damage);	
		}
		return null;
	}
	
	public int getBullets(){
		return clipCurr;
	}
	
	public int getBulletsMax(){
		return clipSize;
	}
	
	public void Reload(){
		clipCurr = clipSize;
	}
}

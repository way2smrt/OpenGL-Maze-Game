package com.battleslug.flare.item;

import com.battleslug.flare.world.Bullet;

public class Weapon extends Item{
	private int damage;
	
	private int clipSize;
	private int clipCurr;
	
	private String type;
	
	private AmmoType ammoType;
	public enum AmmoType{pistol, smg, shotgun, rifle}
	
	public Weapon(String name, String description, CanUse usability, int damage, int clipSize){
		super(name, description, usability);
		
		this.damage = damage;
		this.clipSize = clipSize;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
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
	
	public boolean isClipFull(){
		if(clipCurr == clipSize){
			return true;
		}
		return false;
	}
	
	public Bullet shoot(int x, int y, int xSpeed, int ySpeed){
		if(clipCurr != 0){
			clipCurr -= 1;
			return new Bullet(x, y, xSpeed, ySpeed, damage);
			
		}
		return null;
	}
	
	public void Reload(){
		clipCurr = clipSize;
	}
}

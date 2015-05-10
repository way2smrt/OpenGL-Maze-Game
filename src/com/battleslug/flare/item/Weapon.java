package com.battleslug.flare.item;

public class Weapon extends Item {
	private int damage;

	private int ammoMax;
	
	private float fireDelay;
	private float reloadDelay;
	
	public enum AmmoType{Pistol, SMG, Shotgun, Rifle}
	private AmmoType ammoType;
	
	public enum FireMode{Automatic, Semiautomatic};
	private FireMode fireMode;
	
	public enum ReloadMode{ChamberSingle, ChamberFull, Clip}
	private ReloadMode reloadMode;
	
	public Weapon(String name, String description, FireMode fireMode, float fireDelay, int damage, ReloadMode reloadMode, float reloadDelay, AmmoType ammoType, int ammoMax){
		super(name, description);
		
		this.fireMode = fireMode;
		this.fireDelay = fireDelay;
		this.damage = damage;
		this.reloadMode = reloadMode;
		this.reloadDelay = reloadDelay;
		this.ammoType = ammoType;
		this.ammoMax = ammoMax;
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
	
	public ReloadMode getReloadMode(){
		return reloadMode;
	}
	
	public float getReloadDelay(){
		return reloadDelay;
	}
	
	public int getAmmoMax(){
		return ammoMax;
	}
	
	public int getDamage(){
		return damage;
	}
}
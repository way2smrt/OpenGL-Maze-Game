package com.battleslug.flare.item;

import com.battleslug.glbase.Texture;

public class Weapon extends Item {
	private int damage;

	private int ammoMax;
	
	private float fireDelay;
	private float reloadDelay;
	
	public static enum Type{PISTOL, SMG, SHOTGUN, RIFLE, SNIPER_RIFLE, ASSAULT_RIFLE};
	private Type type;
	
	public static enum AmmoType{PISTOL, SHOTGUN, RIFLE}
	private AmmoType ammoType;
	
	public static enum FireMode{AUTO, SEMIAUTO};
	private FireMode fireMode;
	
	public static enum ReloadMode{CHAMBER_SINGLE, CHAMBER_FULL, CLIP}
	private ReloadMode reloadMode;
	
	public static Texture texAmmoPistol, texAmmoRifle, texAmmoShotgun;
	
	public Weapon(String name, String description, Type type, FireMode fireMode, float fireDelay, int damage, ReloadMode reloadMode, float reloadDelay, AmmoType ammoType, int ammoMax){
		super(name, description);
		
		this.type = type;
		this.fireMode = fireMode;
		this.fireDelay = fireDelay;
		this.damage = damage;
		this.reloadMode = reloadMode;
		this.reloadDelay = reloadDelay;
		this.ammoType = ammoType;
		this.ammoMax = ammoMax;
		
		texAmmoPistol = new Texture("res/img/hud/bullet_normal.png");
		texAmmoRifle = new Texture("res/img/hud/bullet_rifle.png");
		texAmmoShotgun = new Texture("res/img/hud/bullet_shotgun.png");
	}
	
	public Type getType(){
		return type;
	}
	
	public static String getTypeString(Type type){
		switch(type){
		case PISTOL:
			return "pistol";
		case SMG:
			return "smg";
		case SHOTGUN:
			return "shotgun";
		case RIFLE:
			return "rifle";
		case SNIPER_RIFLE:
			return "sniper rifle";
		case ASSAULT_RIFLE:
			return "assault rifle";
		default: 
			return "unknown";
		}
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
	
	public Texture getAmmoTex(){
		switch(ammoType){
			case PISTOL:
				return texAmmoPistol;
			case RIFLE:
				return texAmmoRifle;
			case SHOTGUN:
				return texAmmoShotgun;
			default:
				return texAmmoPistol;
		}
	}
}
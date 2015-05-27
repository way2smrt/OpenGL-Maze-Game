package com.battleslug.flare.world;

import com.battleslug.flare.world.ObjectWorldData;

public class Bullet {
	public float damage;
	
	public ObjectWorldData objectWorldData;
	
	public Bullet(ObjectWorldData objectWorldData, float damage){
		this.objectWorldData = objectWorldData;
		this.damage = damage;
	}
	
	public ObjectWorldData getObjectWorldData(){
		return objectWorldData;
	}
	
	public float getDamage(){
		return damage;
	}
}

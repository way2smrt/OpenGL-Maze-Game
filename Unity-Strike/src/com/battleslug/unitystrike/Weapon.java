package com.battleslug.unitystrike;

import com.battleslug.unitystrike.Item.CanUse;

public class Weapon extends Item{
	private int damage;
	
	public Weapon(String name, String description, CanUse usability, int _damage){
		super(name, description, usability);
		
		damage = _damage;
	}
	
	public void setDamage(int _damage){
		damage = _damage;
	}
	
	public int getDamage(){
		return damage;
	}
}

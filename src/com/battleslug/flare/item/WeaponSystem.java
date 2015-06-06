package com.battleslug.flare.item;

import com.battleslug.flare.world.*;

import java.util.Arrays;

public class WeaponSystem {
	public Weapon[] weapon;
	
	public WeaponSystem(){
		weapon = new Weapon[0];
	}
	
	public void add(Weapon weapon){
		this.weapon = Arrays.copyOf(this.weapon, this.weapon.length+1);
		
		this.weapon[this.weapon.length-1] = weapon;
	}
	
	public boolean exists(String name){
		for(int i = 0; i != weapon.length; i++){
			if(weapon[i].getName() == name){
				return true;
			}
		}
		return false;
	}
	
	public Weapon getWeapon(String name){
		for(int i = 0; i != weapon.length; i++){
			if(weapon[i].getName() == name){
				return weapon[i];
			}
		}
		return null;
	}
	
	public Weapon getWeapon(int ID){
		if(ID >= 0 && ID <= weapon.length){
			return weapon[ID];
		}
		return null;
	}
	
	public int getNumWeapons(){
		return weapon.length;
	}
}

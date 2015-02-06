package com.battleslug.unitystrike;

import com.battleslug.unitystrike.Item.CanUse;

public class Armor extends Item{
	private int protection;
	
	public Armor(String name, String description, CanUse usability, int _protection){
		super(name, description, usability);
		
		protection = _protection;
	}
	
	public void setProtection(int _protection){
		protection = _protection;
	}
	
	public int getProtection(){
		return protection;
	}
}

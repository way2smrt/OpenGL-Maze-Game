package com.battleslug.flare.item;

public class Armor extends Item {
	private int protection;
	
	public Armor(String name, String description, CanUse usability, int protection){
		super(name, description, usability);
		
		this.protection = protection;
	}
	
	public void setProtection(int protection){
		this.protection = protection;
	}
	
	public int getProtection(){
		return protection;
	}
}

package com.battleslug.flare.item;

public class Armor extends Item {
	private int protection;
	
	public Armor(String name, String description, int protection){
		super(name, description);
		
		this.protection = protection;
	}
	
	public void setProtection(int protection){
		this.protection = protection;
	}
	
	public int getProtection(){
		return protection;
	}
}

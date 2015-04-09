package com.battleslug.usengine.item;

public class Item {
	private String name;
	private String description;
	
	private CanUse usability;
	
	public enum CanUse{human, alien, both}
	
	public Item(String _name, String _description, CanUse _usability){
		name = _name;
		description = _description;
		usability = _usability;
	}
	
	public void setDescription(String _description){
		description = _description;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public CanUse getUsability(){
		return usability;
	}
}

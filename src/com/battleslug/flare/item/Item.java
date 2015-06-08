package com.battleslug.flare.item;

public class Item {
	protected String name;
	protected String description;
	protected float speedMultiplier;
	
	public Item(String name){
		this(name, "", 1.0f);
	}
	
	public Item(String name, String description){
		this(name, description, 1.0f);
	}
	
	public Item(String name, String description, Float speedMultiplier){
		this.name = name;
		this.description = description;
		this.speedMultiplier = speedMultiplier;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Float getSpeedMultiplier(){
		return speedMultiplier;
	}
}

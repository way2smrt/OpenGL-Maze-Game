package com.battleslug.usengine;

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
	
	public class Armor extends Item {
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
	
	public class Weapon extends Item {
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

}

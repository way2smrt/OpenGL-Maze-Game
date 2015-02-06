package com.battleslug.unitystrike;


public class Sentient {
	private String name;
	private int x, y;
	
	private SentientDrawInfo drawInfo;
	
	private int health;
	private int speed;
	private int maxHealth;
	
	private Weapon weapon;
	private Armor armor;
	private Item item;
	
	public Sentient(String _name, int _maxHealth, int _speed){
		name = _name;
		health = _maxHealth;
		maxHealth = _maxHealth;
		speed = _speed;
	}
	
	public void getAction(){
		//AI code here sometime later
	}
	
	public String getName(){
		return name;
	}
	
	public void setLocation(int _x, int _y){
		x = _x;
		y = _y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public Armor getArmor(){
		return armor;
	}
	
	public Item getItem(){
		return item;
	}
	
	public SentientDrawInfo getDrawInfo(){
		return drawInfo;
	}
	
	public int getHealth(){
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public boolean isAlive(){
		if (health > 0){
			return true;
		}
		else if(health == 0){
			return false;
		}
		//negative values will mean the sentient is invincible
		return true;
	}
	
	public void damage(Weapon _weapon){
		if (armor != null){
			if (health - _weapon.getDamage()> 0){
				health -= _weapon.getDamage();
			}
			else if(health - _weapon.getDamage() <= 0){
				health = 0;
			}
		}
		else if (armor == null){
			//check how much damage it does, don't want to heal the sentient due to a high armor value!
			if (health - (_weapon.getDamage() - armor.getProtection()) > 0){
				health -= _weapon.getDamage() - armor.getProtection();
			}
			else if((health - _weapon.getDamage() - armor.getProtection()) <= 0){
				health = 0;
			}
		}
	}
}

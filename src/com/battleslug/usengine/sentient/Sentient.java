package com.battleslug.usengine.sentient;

import com.battleslug.usengine.display.Texture;
import com.battleslug.usengine.item.*;

public class Sentient {
	private String name;

	private int health;
	private int speed;
	private int maxHealth;
	
	private Weapon weapon;
	private Armor armor;
	private Item item;
	private Inventory inventory;
	
	private Texture texture;
	
	private Team team;
	
	public Sentient(String name, Texture texture, int maxHealth, int speed){
		this.name = name;
		this.texture = texture;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		this.speed = speed;
	}
	
	public void getAction(){
	}
	
	public String getName(){
		return name;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void setInventory(Inventory inventory){
		this.inventory = inventory;
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
	
	public int getHealth(){
		return health;
	}
	
	public int getMaxHealth(){
		return maxHealth;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public Texture getTexture(){
		return texture;
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
	
	public void setTeam(Team team){
		this.team = team;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public boolean isOnTeam(Team team){
		if(team.getTeamNumber() == this.team.getTeamNumber()){
			return true;
		}
		
		return false;
	}
	
	public void damage(Weapon weapon){
		//TODO, add bullet collisions
	}
	
	public class Team {
		private int num;
		private String name;
		
		private int enemy[];
		private final int ENEMIES = 5;
		
		private int friend[];
		private final int FRIENDS = 5;
		
		public static final int FRIENDLY = 1;
		public static final int NEUTRAL = 2;
		public static final int AGGRESSIVE = 3;
		
		public Team(int num, String name){
			this.num = num;
			this.name = name;
			
			enemy = new int[ENEMIES];
			friend = new int[FRIENDS];
			
			if(num == 0){
				System.out.println("Caution: Team number may not be 0!");
			}
		}
		
		public int getTeamNumber(){
			return num;
		}
		
		public String getTeamName(){
			return name;
		}
		
		public void addEnemy(int team, int slot){
			if(slot > -1 && slot <= 5){
				enemy[slot] = team;
			}
		}
		
		public void removeEnemy(int team){
			for (int i = 0; i != ENEMIES; i++){
				if(enemy[i] == team){
					enemy[i] = 0;
				}
			}
		}
		
		public void addFriend(int team, int slot){
			if(slot > -1 && slot <= 5){
				friend[slot] = team;
			}
		}
		
		public void removeFriend(int team){
			for (int i = 0; i != FRIENDS; i++){
				if(friend[i] == team){
					friend[i] = 0;
				}
			}
		}
		
		public int getRelationStatus(int team){
			for (int i = 0; i != ENEMIES; i++){
				if(enemy[i] == team){
					return AGGRESSIVE;
				}
			}
			
			for (int i = 0; i != FRIENDS; i++){
				if(friend[i] == team){
					return FRIENDLY;
				}
			}
			
			return NEUTRAL;
		}
		
		public int getFriendSlot(int team){
			for (int i = 0; i != ENEMIES; i++){
				if(enemy[i] == team){
					return i;
				}
			}
			
			return -1;
		}
		
		public int getEnemySlot(int team){
			for (int i = 0; i != FRIENDS; i++){
				if(friend[i] == team){
					return i;
				}
			}
			
			return -1;
		}
		
		public void changeRelationStatus(int team, int status){
			if(status == NEUTRAL){
				removeFriend(team);
				removeEnemy(team);
			}
			
			else if(status == AGGRESSIVE){
				removeFriend(team);
				addEnemy(team, getEnemySlot(team));
			}
			
			else if(status == FRIENDLY){
				removeEnemy(team);
				addFriend(team, getEnemySlot(team));
			}
		}
	}
}

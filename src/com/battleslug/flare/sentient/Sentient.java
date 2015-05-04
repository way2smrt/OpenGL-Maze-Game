package com.battleslug.flare.sentient;

import com.battleslug.flare.item.*;
import com.battleslug.porcupine.Circle;
import com.battleslug.porcupine.Texture;

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
	
	protected float speedForward, speedBackward, speedStrafe;
	
	protected float xGlobal, yGlobal, zGlobal;
	protected float xSpeedGlobal, ySpeedGlobal, zSpeedGlobal;
	protected float xSpeedGlobalMax, ySpeedGlobalMax, zSpeedGlobalMax;
	
	protected float rotHori;
	
	protected float xCamLocal, yCamLocal, zCamLocal;
	
	protected enum Direction{FORWARD, BACKWARD, LEFT, RIGHT};
	
	public Sentient(String name, int maxHealth){
		this.name = name;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
	}
	
	public void getAction(){
		//TODO maybe AI here?
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
	
	public void setLocation(float xGlobal, float yGlobal, float zGlobal){
		this.xGlobal = xGlobal;
		this.yGlobal = yGlobal;
		this.zGlobal = zGlobal;
	}
	
	public void setXGlobal(float xGlobal){
		this.xGlobal = xGlobal;
	}
	
	public void setYGlobal(float yGlobal){
		this.yGlobal = yGlobal;
	}
	
	public void setZGlobal(float zGlobal){

		this.zGlobal = zGlobal;
	}
	
	public void setXSpeedGlobal(float xSpeedGlobal){
		this.xSpeedGlobal = xSpeedGlobal;
		
		if(xSpeedGlobal > xSpeedGlobalMax){
			xSpeedGlobal = xSpeedGlobalMax;
		}
		else if(xSpeedGlobal < -xSpeedGlobalMax){
			xSpeedGlobal = -xSpeedGlobalMax;
		}
	}
	
	public void setYSpeedGlobal(float ySpeedGlobal){
		this.ySpeedGlobal = ySpeedGlobal;
		
		if(ySpeedGlobal > ySpeedGlobalMax){
			ySpeedGlobal = ySpeedGlobalMax;
		}
		else if(ySpeedGlobal < -ySpeedGlobalMax){
			ySpeedGlobal = -ySpeedGlobalMax;
		}
	}
	
	public void setZSpeedGlobal(float zSpeedGlobal){
		this.zSpeedGlobal = zSpeedGlobal;
		
		if(zSpeedGlobal > zSpeedGlobalMax){
			zSpeedGlobal = zSpeedGlobalMax;
		}
		else if(zSpeedGlobal < -zSpeedGlobalMax){
			zSpeedGlobal = -zSpeedGlobalMax;
		}
	}
	
	public void setXSpeedGlobalMax(float xSpeedGlobalMax){
		this.xSpeedGlobalMax = xSpeedGlobalMax;
	}
	
	public void setYSpeedGlobalMax(float ySpeedGlobalMax){
		this.ySpeedGlobalMax = ySpeedGlobalMax;
	}
	
	public void setZSpeedGlobalMax(float zSpeedGlobalMax){
		this.zSpeedGlobalMax = zSpeedGlobalMax;
	}
	
	public float getXGlobal(){
		return xGlobal;
	}
	
	public float getYGlobal(){
		return yGlobal;
	}
	
	public float getZGlobal(){
		return zGlobal;
	}
	
	public float getXSpeedGlobal(){
		return xSpeedGlobal;
	}
	
	public float getYSpeedGlobal(){
		return ySpeedGlobal;
	}
	
	public float getZSpeedGlobal(){
		return zSpeedGlobal;
	}
	
	public void setSpeed(float speedForward, float speedBackward, float speedStrafe){
		this.speedForward = speedForward;
		this.speedBackward = speedBackward;
		this.speedStrafe = speedStrafe;
	}
	
	public float getSpeedForward(){
		return speedForward;
	}
	
	public float getSpeedBackward(){
		return speedBackward;
	}
	
	public float getSpeedStrafe(){
		return speedStrafe;
	}
	
	public void setRotationHorizontal(float rotHori){
		this.rotHori = rotHori;
	}
	
	public float getRotationHorizontal(){
		return rotHori;
	}
	
	public void setXCamLocal(float xCamLocal){
		this.xCamLocal = xCamLocal;
	}
	
	public void setYCamLocal(float yCamLocal){
		this.yCamLocal = yCamLocal;
	}

	public void setZCamLocal(float zCamLocal){
		this.zCamLocal = zCamLocal;
	}
	
	public float getXCamLocal(){
		return xCamLocal;
	}
	
	public float getYCamLocal(){
		return yCamLocal;
	}
	
	public float getZCamLocal(){
		return zCamLocal;
	}
	
	public void move(Direction dir, float change){
		float rot = rotHori;
		Circle circle = new Circle(0, 0, change);
		
		//no change for a direction of forward
		if(dir == Direction.BACKWARD){
			rot += 180;
		}
		else if(dir == Direction.LEFT){
			rot += 270;
		}
		else if(dir == Direction.RIGHT){
			rot += 90;
		}
		
		setLocation(xGlobal+circle.getX(rot), yGlobal, zGlobal+circle.getY(rot));
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

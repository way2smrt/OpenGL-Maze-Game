package com.battleslug.unitystrike;
<<<<<<< HEAD
	
=======


>>>>>>> d4a22ec0feeed901c3c6d5a35988886e27ca3fdb
public class Sentient {
	private String name;
	private int x, y;
	
<<<<<<< HEAD
=======
	private SentientDrawInfo drawInfo;
	
>>>>>>> d4a22ec0feeed901c3c6d5a35988886e27ca3fdb
	private int health;
	private int speed;
	private int maxHealth;
	
	private Weapon weapon;
	private Armor armor;
	private Item item;
	
<<<<<<< HEAD
	private Team team;
	
=======
>>>>>>> d4a22ec0feeed901c3c6d5a35988886e27ca3fdb
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
	
<<<<<<< HEAD
=======
	public SentientDrawInfo getDrawInfo(){
		return drawInfo;
	}
	
>>>>>>> d4a22ec0feeed901c3c6d5a35988886e27ca3fdb
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
	
<<<<<<< HEAD
	public void setTeam(Team _team){
		team = _team;
	}
	
	public Team getTeam(){
		return team;
	}
	
	public boolean isOnTeam(Team t){
		if(t.getTeamNumber() == team.getTeamNumber()){
			return true;
		}
		
		return false;
	}
	
=======
>>>>>>> d4a22ec0feeed901c3c6d5a35988886e27ca3fdb
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
<<<<<<< HEAD
	
	public class Team{
		private int num;
		private String name;
		
		private int enemy[];
		private final int ENEMIES = 5;
		
		private int friend[];
		private final int FRIENDS = 5;
		
		public static final int FRIENDLY = 1;
		public static final int NEUTRAL = 2;
		public static final int AGGRESSIVE = 3;
		
		public Team(int _num, String _name){
			num = _num;
			name = _name;
			
			enemy = new int[ENEMIES];
			friend = new int[FRIENDS];
			
			if(_num == 0){
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
=======
>>>>>>> d4a22ec0feeed901c3c6d5a35988886e27ca3fdb
}

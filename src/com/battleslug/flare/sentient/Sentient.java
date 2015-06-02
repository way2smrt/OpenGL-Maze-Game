package com.battleslug.flare.sentient;

import com.battleslug.flare.item.*;
import com.battleslug.flare.world.*;
import com.battleslug.glbase.Circle;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.geometry.*;

public class Sentient {
	private String name;

	private int health;
	private int speed;
	private int maxHealth;
	
	protected WeaponInstance weaponInstance;
	protected Armor armor;
	protected Item item;
	protected Inventory inventory;
	
	private Texture texture;
	
	protected float speedForward, speedBackward, speedStrafe;
	
	protected ObjectWorldData objectWorldData;
	
	protected Point cam;
	protected float eyeHeight;
	
	public enum Direction{FORWARD, BACKWARD, LEFT, RIGHT};
	
	protected Pivot pivot;
	
	protected World world;
	
	public Sentient(World world, String name, int maxHealth){
		this.world = world;
		
		this.name = name;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		
		setSpeed(5, 2, 3);
		
		objectWorldData = new ObjectWorldData();
		cam = new Point(objectWorldData.getPoint().getX(), objectWorldData.getPoint().getY()+eyeHeight, objectWorldData.getPoint().getZ());
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
	
	public WeaponInstance getWeaponInstance(){
		return weaponInstance;
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
	
	public void damage(Weapon weapon){
		//TODO, add bullet collisions
	}
	
	public ObjectWorldData getObjectWorldData(){
		return objectWorldData;
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
	
	public void setHeight(float height){
		this.eyeHeight = height;
	}
	
	public void setCamLocation(Point cam){
		this.cam = cam;
	}
	
	public Point getCamLocation(){
		return cam;
	}
	
	public Pivot getPivot(){
		return pivot;
	}
	
	public void setPivot(Pivot pivot){
		this.pivot = pivot;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void move(Direction dir, float change){
		float rot = pivot.getRotXZAxis();
		Circle circle = new Circle(0, 0, change);
		
		//Speed speed = new Speed();
		
		if(dir == Direction.BACKWARD){
			rot += 180;
		}
		else if(dir == Direction.LEFT){
			rot += 270;
		}
		else if(dir == Direction.RIGHT){
			rot += 90;
		}
		
		cam.setY(objectWorldData.getPoint().getY()+eyeHeight);
	}

}

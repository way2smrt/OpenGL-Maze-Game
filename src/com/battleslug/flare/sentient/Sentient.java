package com.battleslug.flare.sentient;

import com.battleslug.flare.item.*;
import com.battleslug.porcupine.Circle;
import com.battleslug.porcupine.Texture;
import com.battleslug.flare.world.*;

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
	
	protected float speedForward, speedBackward, speedStrafe;
	
	protected float xGlobal, yGlobal, zGlobal;
	protected float xSpeedGlobal, ySpeedGlobal, zSpeedGlobal;
	protected float xSpeedGlobalMax, ySpeedGlobalMax, zSpeedGlobalMax;
	
	protected float rotHori;
	
	protected float xCamLocal, yCamLocal, zCamLocal;
	
	protected enum Direction{FORWARD, BACKWARD, LEFT, RIGHT};
	
	private Pivot pivot;
	
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
	
	public Pivot getPivot(){
		return pivot;
	}
	
	public void setPivot(Pivot pivot){
		this.pivot = pivot;
	}
	
	public void move(Direction dir, float change){
		float rot = pivot.getRotXZAxis();
		Circle circle = new Circle(0, 0, change);
		
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
}

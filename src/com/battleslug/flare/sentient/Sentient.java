package com.battleslug.flare.sentient;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.flare.item.*;
import com.battleslug.flare.world.*;
import com.battleslug.glbase.Circle;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.event.Keyboard;
import com.battleslug.glbase.event.Mouse;
import com.battleslug.glbase.geometry.*;

import static java.lang.Math.*;

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
	
	protected Pivot pivot;
	
	protected World world;

	public enum MoveMode{Running, Standing, Crouching, Prone}; 
	private MoveMode moveMode;
	
	public final float EYE_HEIGHT_STANDING = 1.5f;
	public final float EYE_HEIGHT_CROUCHING = 0.7f;
	public final float EYE_HEIGHT_PRONE = 0.2f;
	
	public Sentient(World world, String name, int maxHealth){
		this.world = world;
		
		this.name = name;
		this.health = maxHealth;
		this.maxHealth = maxHealth;
		
		setSpeed(5, 2, 3);
		
		objectWorldData = new ObjectWorldData();
		cam = new Point(objectWorldData.getPoint().getX(), objectWorldData.getPoint().getY()+eyeHeight, objectWorldData.getPoint().getZ());
	
		moveMode = MoveMode.Standing;
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
	
	public void setWeaponInstance(WeaponInstance weaponInstance){
		this.weaponInstance = weaponInstance;
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
	
	public MoveMode getMoveMode(){
		return moveMode;
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
	
	/**
	 * Updates with AI, making decisions by itself.
	 */
	public void updateAIControlled(double timePassed){
		//AI here
	}
	
	/**
	 * Updates with player controls
	 */
	public void updateUserControlled(Keyboard keyboard, Mouse mouse, double timePassed){
		float rotChange = 0;
		boolean hasMoved = false;
		
		if(keyboard.isDown(GLFW_KEY_W)){
			hasMoved = true;
		}
		if(keyboard.isDown(GLFW_KEY_S)){
			rotChange = 180;
			hasMoved = true;
		}
		else if(keyboard.isDown(GLFW_KEY_A)){
			rotChange = -90;
			hasMoved = true;
		}
		else if(keyboard.isDown(GLFW_KEY_D)){
			rotChange = 90;
			hasMoved = true;
		}
		
		if(keyboard.isDown(GLFW_KEY_LEFT_CONTROL)){
			//crouching movement
			moveMode = moveMode.Prone;
			if(getCamLocation().getY()-objectWorldData.getPoint().getY() != EYE_HEIGHT_PRONE){
				getCamLocation().setY(getCamLocation().getY()-(float)(3.1f*timePassed)+objectWorldData.getPoint().getY());
				if(getCamLocation().getY()-objectWorldData.getPoint().getY() < EYE_HEIGHT_PRONE){
					getCamLocation().setY(EYE_HEIGHT_PRONE+objectWorldData.getPoint().getY());
				}
				setSpeed(0, 0, 0);
			}
			else {
				setSpeed(0.7f, 0.2f, 0.5f);
			}
		}
		else if(keyboard.isDown(GLFW_KEY_LEFT_SHIFT) && getCamLocation().getY() == EYE_HEIGHT_STANDING){
			//running
			moveMode = moveMode.Running;
			setSpeed(6, 2f, 5);	
		}
		else {
			//normal standing
			moveMode = moveMode.Standing;
			if(getCamLocation().getY()-objectWorldData.getPoint().getY() != EYE_HEIGHT_STANDING){
				getCamLocation().setY(getCamLocation().getY()+(float)(1.7f*timePassed)+objectWorldData.getPoint().getY());
				if(getCamLocation().getY()-objectWorldData.getPoint().getY() > EYE_HEIGHT_STANDING){
					getCamLocation().setY(EYE_HEIGHT_STANDING+objectWorldData.getPoint().getY());
				}
				setSpeed(0, 0, 0);
			}
			else {
				setSpeed(3, 1.5f, 2);
			}
		}
		
		if(hasMoved){
			move(pivot.getRotXZAxis(), pivot.getRotXZAxis()+rotChange, timePassed);
		}
		
		//update camera rotation
		pivot.setRotXZAxis(pivot.getRotXZAxis()+(float)(mouse.getCursorRotXZChange()*mouse.getSensitivity3D()));
		pivot.setRotYZAxis(pivot.getRotYZAxis()-(float)(mouse.getCursorRotYZChange()*mouse.getSensitivity3D()));
	}
	
	private void move(float initialRot, float moveRot, double timePassed){
		Circle moveCircle;
		if(moveRot == initialRot){
			moveCircle = new Circle(0, 0, (float)(speedForward*timePassed));
		}
		else if(abs(initialRot-moveRot) <= 90 || abs(initialRot-moveRot) != 0){
			moveCircle = new Circle(0, 0, (float)(speedStrafe*timePassed));
		}
		else {
			moveCircle = new Circle(0, 0, (float)(speedBackward*timePassed));
		}
		
		objectWorldData.getPoint().setX(objectWorldData.getPoint().getX()+moveCircle.getY(moveRot));
		objectWorldData.getPoint().setZ(objectWorldData.getPoint().getZ()+moveCircle.getX(moveRot));
		cam.setY(objectWorldData.getPoint().getY()+eyeHeight);
	}
}

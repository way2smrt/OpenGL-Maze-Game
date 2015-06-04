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
	private float eyeHeight;
	
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
		cam = new Point(objectWorldData.getPoint().getX(), objectWorldData.getPoint().getY()+EYE_HEIGHT_STANDING, objectWorldData.getPoint().getZ());
	
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
	public void updateUserControlled(Keyboard keyboard, Mouse mouse, double timePassed, double currTime){
		float rotChange = 0;
		boolean hasMoved = false;
		
		//update player eye height for body stance
		if(keyboard.isDown(GLFW_KEY_LEFT_CONTROL)){
			//crouching movement
			moveMode = moveMode.Prone;
			if(eyeHeight != EYE_HEIGHT_PRONE){
				eyeHeight -= (float)(3.1f*timePassed);
				if(eyeHeight < EYE_HEIGHT_PRONE){
					eyeHeight = EYE_HEIGHT_PRONE;
				}
				setSpeed(0, 0, 0);
			}
			else {
				setSpeed(0.7f, 0.2f, 0.5f);
			}
		}
		else if(keyboard.isDown(GLFW_KEY_LEFT_SHIFT) && eyeHeight == EYE_HEIGHT_STANDING){
			//running
			moveMode = moveMode.Running;
			setSpeed(15f, 2f, 5f);	
		}
		else {
			//normal standing
			moveMode = moveMode.Standing;
			if(eyeHeight != EYE_HEIGHT_STANDING){
				eyeHeight += (float)(1.7f*timePassed);
				if(eyeHeight > EYE_HEIGHT_STANDING){
					eyeHeight = EYE_HEIGHT_STANDING;
				}
				setSpeed(0, 0, 0);
			}
			else {
				setSpeed(3, 1.5f, 2);
			}
		}
		
		if(keyboard.isDown(GLFW_KEY_W)){
			hasMoved = true;
			
			if(keyboard.isDown(GLFW_KEY_A)){
				rotChange = -45;
				hasMoved = true;
			}
			else if(keyboard.isDown(GLFW_KEY_D)){
				rotChange = 45;
				hasMoved = true;
			}
			else {
				//no change
			}
		}
		else if(keyboard.isDown(GLFW_KEY_S)){
			hasMoved = true;
			
			if(keyboard.isDown(GLFW_KEY_A)){
				rotChange = 225;
				hasMoved = true;
			}
			else if(keyboard.isDown(GLFW_KEY_D)){
				rotChange = -225;
				hasMoved = true;
			}
			else {
				rotChange = 180;
			}
		}
		else if(keyboard.isDown(GLFW_KEY_A)){
			rotChange = -90;
			hasMoved = true;
		}
		else if(keyboard.isDown(GLFW_KEY_D)){
			rotChange = 90;
			hasMoved = true;
		}
		
		if(hasMoved){
			move(pivot.getRotXZAxis(), pivot.getRotXZAxis()+rotChange, timePassed);
		}
		
		//check for jump
		if(keyboard.wasPressed(GLFW_KEY_SPACE) && objectWorldData.getPoint().getY() == 0f){
			objectWorldData.getSpeed().setYSpeed(0.5f);
		}

		eyeHeight = cam.getY()-objectWorldData.getPoint().getY();
		
		//update camera rotation with mouse input
		pivot.setRotXZAxis(pivot.getRotXZAxis()+(float)(mouse.getXCursorChange()*mouse.getSensitivity3D()));
		pivot.setRotYZAxis(pivot.getRotYZAxis()-(float)(mouse.getYCursorChange()*mouse.getSensitivity3D()));
		
		//check for reload
		if(keyboard.wasPressed(GLFW_KEY_R)){
			if(weaponInstance.getMode() == WeaponInstance.Mode.Ready){
				weaponInstance.initReload(currTime);
			}
		}
		
		invokeGravity(timePassed);
		
		updateLocation();
		updateLocationCam();
	}
	
	private void invokeGravity(double timePassed){
		//invoke gravity
		objectWorldData.getSpeed().setYSpeed(objectWorldData.getSpeed().getYSpeed()-(float)(world.getGravity()*pow(timePassed, 2)));
	}
	
	private void updateLocation(){
		objectWorldData.getPoint().setX(objectWorldData.getPoint().getX()+objectWorldData.getSpeed().getXSpeed());	
		objectWorldData.getPoint().setZ(objectWorldData.getPoint().getZ()+objectWorldData.getSpeed().getZSpeed());
		
		objectWorldData.getPoint().setY(objectWorldData.getPoint().getY()+objectWorldData.getSpeed().getYSpeed());
		if(objectWorldData.getPoint().getY() < world.groundHeight){
			objectWorldData.getPoint().setY(world.groundHeight);
			objectWorldData.getSpeed().setYSpeed(0f);
		}
		
		objectWorldData.getSpeed().clearXSpeed();
		objectWorldData.getSpeed().clearZSpeed();
	}
	
	private void updateLocationCam(){
		cam.setX(objectWorldData.getPoint().getX());
		cam.setY(objectWorldData.getPoint().getY()+eyeHeight);
		cam.setZ(objectWorldData.getPoint().getZ());
	}
	
	private void move(float initialRot, float moveRot, double timePassed){
		Circle moveCircle;
		if(moveRot == initialRot){
			moveCircle = new Circle(0, 0, (float)(speedForward*timePassed));
		}
		else if(moveRot == initialRot+180 || moveRot == initialRot-180){
			moveCircle = new Circle(0, 0, (float)(speedBackward*timePassed));
		}
		else {
			moveCircle = new Circle(0, 0, (float)(speedStrafe*timePassed));
		}
		
		System.out.println(objectWorldData.getSpeed().getXSpeed()+moveCircle.getX(moveRot));
		objectWorldData.getSpeed().setXSpeed(objectWorldData.getSpeed().getXSpeed()+moveCircle.getX(moveRot));
		objectWorldData.getSpeed().setZSpeed(objectWorldData.getSpeed().getZSpeed()+moveCircle.getY(moveRot));
	}
}

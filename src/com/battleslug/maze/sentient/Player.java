package com.battleslug.maze.sentient;

import static org.lwjgl.glfw.GLFW.*;

import com.battleslug.glbase.Texture;
import com.battleslug.glbase.event.Keyboard;
import com.battleslug.glbase.event.Mouse;
import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.geometry.Pivot.LimitMode;
import com.battleslug.maze.world.*;

public class Player implements Sentient {
	private String name;
	
	private Texture texture;
	
	protected float speedForward, speedBackward, speedStrafe;
	
	protected ObjectWorldData objectWorldData;
	
	protected ObjectWorldData cam;
	private float eyeHeight;
	
	protected World world;
	
	protected Keyboard keyboard;
	protected Mouse mouse;

	public enum MoveMode{Running, Standing, Prone}; 
	private MoveMode moveMode;
	
	public final float EYE_HEIGHT_STANDING = 1.5f;
	public final float EYE_HEIGHT_CROUCHING = 0.7f;
	public final float EYE_HEIGHT_PRONE = 0.2f;
	
	private boolean isAlive;
	
	public Player(String name, World world, Keyboard keyboard, Mouse mouse){
		this.name = name;
		
		this.world = world;
		this.keyboard = keyboard;
		this.mouse = mouse;
		
		setSpeed(5, 2, 3);
		
		objectWorldData = new ObjectWorldData();
		
		cam = new ObjectWorldData();
		eyeHeight = EYE_HEIGHT_STANDING;
	
		moveMode = MoveMode.Standing;
		
		this.isAlive = true;
		
		objectWorldData.setPivot(new Pivot(0, 90, 0));
		objectWorldData.getPivot().setRotYZAxisLimits(0f, 180f);
		objectWorldData.getPivot().setYZAxisLimitMode(LimitMode.STOP);
	}
	
	public String getName(){
		return name;
	}
	
	public MoveMode getMoveMode(){
		return moveMode;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public boolean shouldBeDrawn(){
		return isAlive;
	}
	
	public void kill(){
		isAlive = false;
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
	
	public void setCamera(ObjectWorldData cam){
		this.cam = cam;
	}
	
	public ObjectWorldData getObjectWorldData(){
		return objectWorldData;
	}
	
	public ObjectWorldData getCamera(){
		return cam;
	}
	
	public World getWorld(){
		return world;
	}
	
	/**
	 * Updates with player controls
	 */
	public void think(double timePassed){
		float rotChange = 0;
		boolean hasMoved = false;
		
		//update player eye height for body stance
		if(keyboard.isDown(GLFW_KEY_LEFT_CONTROL)){
			//crouching movement
			moveMode = MoveMode.Prone;
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
			moveMode = MoveMode.Running;
			setSpeed(7f, 2f, 5f);	
		}
		else {
			//normal standing
			moveMode = MoveMode.Standing;
			if(eyeHeight != EYE_HEIGHT_STANDING){
				eyeHeight += (float)(1.7f*timePassed);
				if(eyeHeight > EYE_HEIGHT_STANDING){
					eyeHeight = EYE_HEIGHT_STANDING;
				}
				setSpeed(0, 0, 0);
			}
			else {
				setSpeed(4, 1.5f, 3);
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
			move(objectWorldData.getPivot().getRotXZAxis(), objectWorldData.getPivot().getRotXZAxis()+rotChange, timePassed);
		}
		
		//update camera rotation with mouse input
		objectWorldData.getPivot().setRotXZAxis(objectWorldData.getPivot().getRotXZAxis()+(float)(mouse.getXCursorChange()*mouse.getSensitivity3D()));
		objectWorldData.getPivot().setRotYZAxis(objectWorldData.getPivot().getRotYZAxis()-(float)(mouse.getYCursorChange()*mouse.getSensitivity3D()));
		
		updateLocation();
		updateCamera();
	}
	
	private void updateLocation(){
		objectWorldData.getPoint().setX(objectWorldData.getPoint().getX()+objectWorldData.getSpeed().getXSpeed());	
		objectWorldData.getPoint().setZ(objectWorldData.getPoint().getZ()+objectWorldData.getSpeed().getZSpeed());
		
		objectWorldData.getPoint().setY(objectWorldData.getPoint().getY()+objectWorldData.getSpeed().getYSpeed());
		if(objectWorldData.getPoint().getY() < world.groundHeight){
			objectWorldData.getPoint().setY(world.groundHeight);
			objectWorldData.getSpeed().clearYSpeed();
		}
		
		objectWorldData.getSpeed().clearXSpeed();
		objectWorldData.getSpeed().clearZSpeed();
	}
	
	private void updateCamera(){
		cam.setPoint(new Point(objectWorldData.getPoint().getX(), objectWorldData.getPoint().getY()+eyeHeight, objectWorldData.getPoint().getZ()));
		cam.setPivot(objectWorldData.getPivot());
		cam.setSpeed(objectWorldData.getSpeed());
	}
	
	private void move(float initialRot, float moveRot, double timePassed){
		Circle moveCircle;
		if(moveRot == initialRot){
			moveCircle = new Circle((float)(speedForward*timePassed));
		}
		else if(moveRot == initialRot+180 || moveRot == initialRot-180){
			moveCircle = new Circle((float)(speedBackward*timePassed));
		}
		else {
			moveCircle = new Circle((float)(speedStrafe*timePassed));
		}

		objectWorldData.getSpeed().setXSpeed(objectWorldData.getSpeed().getXSpeed()+moveCircle.getA(moveRot));
		objectWorldData.getSpeed().setZSpeed(objectWorldData.getSpeed().getZSpeed()+moveCircle.getB(moveRot));
	}
}

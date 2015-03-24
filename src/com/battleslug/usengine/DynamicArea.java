package com.battleslug.usengine;

import com.battleslug.usengine.StaticArea.Line;

public class DynamicArea extends StaticArea{
	private Sentient sentient[] = new Sentient[1];
	private int sentients;
	
	private Bullet bullet[] = new Bullet[1];
	private int bullets;
	
	public void addSentient(Sentient sentient){
		for(int i = 0; i != sentients; i++){
			if(this.sentient[i] == null){
				this.sentient[i] = sentient;
				return;
			}
		}
		
		//resize our array for the new sentient
		Sentient temp[] = new Sentient[sentients];
		for(int i = 0; i != sentients; i++){
			temp[i] = this.sentient[i];
		}
		
		//resize the array
		this.sentient = new Sentient[sentients+1];
		
		for(int i = 0; i != sentients; i++){
			this.sentient[i] = temp[i];
		}
		
		//add our parameter to the last array slot
		this.sentient[sentients] = sentient;
	}
	
	public void removeSentient(Sentient sentient){
		for(int i = 0; i != sentients; i++){
			if(this.sentient[i] == sentient){
				this.sentient[i] = null;
			}
		}
	}
	
	private boolean checkCollision(){
		for(int i = 0; i != sentients; i++){
			//TODO
		}
		
		return false;
	}
	
	public class Bullet {
		private int damage;
		
		private int x, y;
		
		private int xSpeed, ySpeed;
		
		public Bullet(int damage) {
			this.damage = damage;
		}
		
		public int getDamage() {
			return damage;
		}
		
		public Line getLocation() {
			//TODO
			return null;
		}
		
	}
	
	public class PhysicsInfo {
		public static final int LOCATION_STATIC = 0;
		public static final int LOCATION_DYNAMIC = 1;
		
		public static final int BULLET_PROOF = 0;
		public static final int BULLET_REFLECTIVE = 1;
		public static final int BULLET_DAMAGEABLE = 2;
		
		private int x1, y1;
		private int x2, y2;
		
		private int xSpeed, ySpeed;
		private int locationType;
		
		private int damageLossOnHit;
		
		
		public PhysicsInfo(int x, int y, int locationType){
			
		}
		
		public void setLocation(int x, int y) {
			x1 = x;
			y1 = y;
		}
		
		public void setBoundingBox(int width, int height){
			x2 = x1 + width;
			y2 = y1 + height;
		}
		
		public int getX() {
			return x1;
		}
		
		public int getY() {
			return y1;
		}
		
		public void invokeMovement(int xSpeed, int ySpeed) {
			this.xSpeed += xSpeed;
			this.ySpeed += ySpeed;
		}
		
		public void invokeGravity(){
			//TODO
		}
		
		public void stopXMovement(){
			xSpeed = 0;
		}
		
		public void stopYMovement(){
			ySpeed = 0;
		}
		
		public int getXSpeed() {
			return xSpeed;
		}
		
		public int getYSpeed(){
			return ySpeed;
		}
	}
}

package com.battleslug.usengine.world;

public class Bullet {
	public int damage;
	
	private double x, y;
	private double xSpeed, ySpeed;
	
	public Bullet(double x, double y, double xSpeed, double ySpeed, int damage){
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		
		this.damage = damage;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getXSpeed(){
		return xSpeed;
	}
	
	public double getYSpeed(){
		return ySpeed;
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void update(long millisPassed){
		x += (xSpeed*millisPassed);
		y += (ySpeed*millisPassed);
	}
}

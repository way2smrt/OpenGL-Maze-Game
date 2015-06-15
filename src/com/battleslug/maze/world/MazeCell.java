package com.battleslug.maze.world;

public class MazeCell {
	private boolean up, down, left, right;
	
	public MazeCell(){
		up= true;
		down = true;
		left = true;
		right = true;
	}
	
	public boolean getUp(){
		return up;
	}
	
	public boolean getDown(){
		return down;
	}
	
	public boolean getLeft(){
		return left;
	}
	
	public boolean getRight(){
		return right;
	}
	
	public void setUp(boolean up){
		this.up = up;
	}
	
	public void setDown(boolean down){
		this.down = down;
	}
	
	public void setLeft(boolean left){
		this.left = left;
	}
	
	public void setRight(boolean right){
		this.right = right;
	}
}

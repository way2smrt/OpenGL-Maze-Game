package com.battleslug.maze.world;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

import java.util.Stack;
import java.util.Random;

public class World {	
	private Display display;
	
	public float groundHeight = 0f;
	
	MazeCell[][] maze;
	boolean[][] visited;
	
	public final int BRANCH_CHANCE = 15;
	
	private int mazeWidth;
	
	private float cellWidth;
	private float cellHeight;
	
	Texture wallTex;
	
	public World(int mazeWidth, float cellWidth, float cellHeight, Texture wallTex){
		maze = new MazeCell[mazeWidth][mazeWidth];
		visited = new boolean[mazeWidth][mazeWidth];
		
		this.mazeWidth = mazeWidth;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		this.wallTex = wallTex;
		
		for(int x = 0; x != mazeWidth; x++){
			for(int y = 0; y != mazeWidth; y++){
				maze[x][y] = new MazeCell();
			}
		}
		
		generateMaze();
	}
	
	private boolean isUnvisitedCells(){
		for(int x = 0; x != mazeWidth; x++){
			for(int y = 0; y != mazeWidth; y++){
				if(!visited[x][y]){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean hasUnvisitedNeighbours(Point p){
		Point up = new Point(p.getX(), p.getY()+1);
		Point down = new Point(p.getX(), p.getY()-1);
		Point left = new Point(p.getX()-1, p.getY());
		Point right = new Point(p.getX()+1, p.getY());
		
		if(canMoveTo(up)){
			return true;
		}
		else if(canMoveTo(down)){
			return true;
		}
		else if(canMoveTo(left)){
			return true;
		}
		else if(canMoveTo(right)){
			return true;
		}
		
		return false;
	}
	
	private Point getRandMove(Point currP){
		Random r = new Random();
		
		int move = r.nextInt(4);
		Point p = null;
		
		if(move == 0){
			p = new Point(currP.getX(), currP.getY()+1);
		}
		if(move == 1){
			p = new Point(currP.getX(), currP.getY()-1);
		}
		if(move == 2){
			p = new Point(currP.getX()+1, currP.getY());
		}
		if(move == 3){
			p = new Point(currP.getX()-1, currP.getY());
		}
		
		return p;
	}
	
	private void outputCellsUnvisited(){
		for(int x = 0; x != mazeWidth; x++){
			for(int y = 0; y != mazeWidth; y++){
				if(!visited[x][y]){
					System.out.println("UNVISITED CELL");
					System.out.println(x+1);
					System.out.println(y+1);
				}
			}
		}
	}
	
	private void generateMaze(){
		Random r = new Random();
		
		//current cell to work with
		Point currP = new Point(1, 1);
		
		//make our stack
		Stack<Point> s = new Stack<Point>();
		
		//mark it as visited and push it to the stack
		visited[(int)(currP.getX()-1)][(int)(currP.getY()-1)] = true;
		s.push(currP);
		
		while(isUnvisitedCells()){
			if(hasUnvisitedNeighbours(currP)){
				Point move = getRandMove(currP);
				
				while(!canMoveTo(move)){
					move = getRandMove(currP);
				}
				
				s.push(move);
				
				//right move
				if(currP.getX() < move.getX()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setRight(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setLeft(false);
				}
				//left move
				else if(currP.getX() > move.getX()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setLeft(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setRight(false);
				}
				//up move
				else if(currP.getY() < move.getY()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setUp(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setDown(false);
				}
				//down move
				else if(currP.getY() > move.getY()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setDown(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setUp(false);
				}
				
				currP = move;
			}
			else if(!s.isEmpty()){
				currP = s.pop();
				visited[(int)(currP.getX()-1)][(int)(currP.getY()-1)] = true;
			}
			else {
				currP = getRandCell();
				visited[(int)(currP.getX()-1)][(int)(currP.getY()-1)] = true;
			}
			
			if(r.nextInt(BRANCH_CHANCE) == 0){
				Point move = null;
				
				int rBranch = r.nextInt(4);
				if(rBranch == 0){
					move = new Point(currP.getX(), currP.getY()+1);
				}
				else if(rBranch == 1){
					move = new Point(currP.getX(), currP.getY()-1);
				}
				else if(rBranch == 2){
					move = new Point(currP.getX()+1, currP.getY());
				
				}
				else if(rBranch == 3){
					move = new Point(currP.getX()-1, currP.getY());
				}
				
				while(!cellExists(move)){
					rBranch = r.nextInt(4);
					if(rBranch == 0){
						move = new Point(currP.getX(), currP.getY()+1);
					}
					else if(rBranch == 1){
						move = new Point(currP.getX(), currP.getY()-1);
					}
					else if(rBranch == 2){
						move = new Point(currP.getX()+1, currP.getY());
					
					}
					else if(rBranch == 3){
						move = new Point(currP.getX()-1, currP.getY());
					}
				}
				
				//right move
				if(currP.getX() < move.getX()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setRight(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setLeft(false);
				}
				//left move
				else if(currP.getX() > move.getX()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setLeft(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setRight(false);
				}
				//up move
				else if(currP.getY() < move.getY()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setUp(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setDown(false);
				}
				//down move
				else if(currP.getY() > move.getY()){
					maze[(int)(currP.getX()-1)][(int)(currP.getY()-1)].setDown(false);
					maze[(int)(move.getX()-1)][(int)(move.getY()-1)].setUp(false);
				}
			}
			
			outputCellsUnvisited();
		}
	}
	
	private boolean cellExists(Point p){
		if(p.getX() > 0 && p.getX() <= mazeWidth){
			if(p.getY() > 0 && p.getY() <= mazeWidth){
				return true;
			}
		}
		return false;
	}
	
	private boolean canMoveTo(Point p){
		if(p.getX() > 0 && p.getX() <= mazeWidth){
			if(p.getY() > 0 && p.getY() <= mazeWidth){
				return !visited[(int)(p.getX()-1)][(int)(p.getY()-1)];
			}
		}
		return false;
	}
	
	private Point getRandCell(){
		Random r = new Random();
		Point p = new Point(r.nextInt(mazeWidth), r.nextInt(mazeWidth));
		
		while(visited[(int)(p.getX())][(int)(p.getY())]){
			p = new Point(r.nextInt(mazeWidth), r.nextInt(mazeWidth));
		}
		
		return p;
	}
	
	public void bind(Display display){
		this.display = display;
	}
	
	public void draw(){
		for(int cx = 0; cx != mazeWidth; cx++){
			for(int cz = 0; cz != mazeWidth; cz++){
				if(!visited[cx][cz]){
					float d = cellWidth;
					float h = cellHeight;
					
					float x = cx*cellWidth;
					float y = 0;
					float z = cz*cellWidth;
					
					if(maze[cx][cz].getRight()){
						//right wall
						display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x+d, y+h, z+d), new Point(x+d, y, z+d), new Point(x+d, y, z), new Point(x+d, y+h, z)}, new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0)}, wallTex, null);
					}
					if(maze[cx][cz].getLeft()){
						//left wall
						display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x, y+h, z), new Point(x, y, z), new Point(x, y, z+d), new Point(x, y+h, z+d)}, new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0)}, wallTex, null);
					}
					if(maze[cx][cz].getDown()){
						//bottom wall
						display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x+d, y+h, z), new Point(x+d, y, z), new Point(x, y, z), new Point(x, y+h, z)}, new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0)}, wallTex, null);
					}
					if(maze[cx][cz].getUp()){
						//top wall
						display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x, y+h, z+d), new Point(x, y, z+d), new Point(x+d, y, z+d), new Point(x+d, y+h, z+d)}, new Point[]{new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0)}, wallTex, null);
					}
				}
			}
		}
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public float getCellWidth(){
		return cellWidth;
	}
	
	public float getCellHeight(){
		return cellHeight;
	}
	
	public float getMazeWidth(){
		return mazeWidth;
	}
	
	public Texture getWallTexture(){
		return wallTex;
	}
}
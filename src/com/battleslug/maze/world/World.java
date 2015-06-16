package com.battleslug.maze.world;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

import java.util.Stack;
import java.util.Random;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class World {	
	private Display display;
	
	public float groundHeight = 0f;
	
	MazeCell[][] maze;
	boolean[][] visited;
	
	public final int BRANCH_CHANCE = 7;
	
	private int mazeWidth;
	
	private float cellWidth;
	private float cellHeight;
	
	private Point exitP;
	
	Texture texWall;
	Texture texGround;
	
	
	public World(int mazeWidth, float cellWidth, float cellHeight, Texture texWall, Texture texGround){
		maze = new MazeCell[mazeWidth][mazeWidth];
		visited = new boolean[mazeWidth][mazeWidth];
		
		this.mazeWidth = mazeWidth;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		
		this.texWall = texWall;
		this.texGround = texGround;
		
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
		if(canMoveTo(down)){
			return true;
		}
		if(canMoveTo(left)){
			return true;
		}
		if(canMoveTo(right)){
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
	
	private void generateMaze(){		
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
				visited[(int)(currP.getX()-1)][(int)(currP.getY()-1)] = true;
			}
			else {
				currP = s.pop();
				visited[(int)(currP.getX()-1)][(int)(currP.getY()-1)] = true;
			}
			
			checkGenRandBranch(currP);
		}
		
		exitP = new Point((currP.getX()-1)*cellWidth, (currP.getY()-1)*cellWidth);
	}
	
	private void checkGenRandBranch(Point currP){
		Random r = new Random();
		
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
	
	public void bind(Display display){
		this.display = display;
	}
	
	public void draw(){
		for(int cx = 0; cx != mazeWidth; cx++){
			for(int cz = 0; cz != mazeWidth; cz++){
				float d = cellWidth;
				float h = cellHeight;
					
				float x = cx*cellWidth;
				float y = 0;
				float z = cz*cellWidth;
					
				//texture scaling
				final float TS = 0.2f; 
				
				if(maze[cx][cz].getRight()){
					display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x+d, y+h, z+d), new Point(x+d, y, z+d), new Point(x+d, y, z), new Point(x+d, y+h, z)}, new Point[]{new Point(0, 0), new Point(0, cellHeight*TS), new Point(cellWidth*TS, cellHeight*TS), new Point(cellWidth*TS, 0)}, texWall, null);
				}
				if(maze[cx][cz].getLeft()){
					display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x, y+h, z), new Point(x, y, z), new Point(x, y, z+d), new Point(x, y+h, z+d)}, new Point[]{new Point(0, 0), new Point(0, cellHeight*TS), new Point(cellWidth*TS, cellHeight*TS), new Point(cellWidth*TS, 0)}, texWall, null);
				}
				if(maze[cx][cz].getDown()){
					display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x+d, y+h, z), new Point(x+d, y, z), new Point(x, y, z), new Point(x, y+h, z)}, new Point[]{new Point(0, 0), new Point(0, cellHeight*TS), new Point(cellWidth*TS, cellHeight*TS), new Point(cellWidth*TS, 0)}, texWall, null);
				}
				if(maze[cx][cz].getUp()){
					display.drawQuadTextured3DUV(Display.FrontFace.CW, new Point[]{new Point(x, y+h, z+d), new Point(x, y, z+d), new Point(x+d, y, z+d), new Point(x+d, y+h, z+d)}, new Point[]{new Point(0, 0), new Point(0, cellHeight*TS), new Point(cellWidth*TS, cellHeight*TS), new Point(cellWidth*TS, 0)}, texWall, null);
				}
				
			}
		}
		
		display.drawQuadTextured3D(new QuadTextured3D(exitP.getX(), groundHeight+0.01f, exitP.getY(), exitP.getX(), groundHeight+0.01f, exitP.getY()+cellWidth, exitP.getX()+cellWidth, groundHeight+0.01f, exitP.getY()+cellWidth, exitP.getX()+cellWidth, groundHeight+0.01f, exitP.getY(), null, new VectorColor(1f, 0f, 0f, 1f)), 0.15f);
	
		drawGround();
		
		if(texGround == null){
			System.out.println("FUCK FUCK FUCK");
		}
	}
	
	public boolean canMoveTo(Point currP, Point destP){
		Line2D moveLine = new Line2D.Float(new Point2D.Float(currP.getX(), currP.getY()), new Point2D.Float(destP.getX(), destP.getY()));
		Line2D wallLine = null;
		
		//ensure we have a small value to stop from clipping through due to being in middle
		final float c = 0.1f;
		
		for(int cx = 0; cx != mazeWidth; cx++){
			for(int cz = 0; cz != mazeWidth; cz++){
				//check up
				wallLine = new Line2D.Float(new Point2D.Float((cx*cellWidth), ((cz+1)*cellWidth)-c), new Point2D.Float(((cx+1)*cellWidth), ((cz+1)*cellWidth)-c));
				if(moveLine.intersectsLine(wallLine) && maze[cx][cz].getUp()){
					return false;
				}
					
				//check down
				wallLine = new Line2D.Float(new Point2D.Float((cx*cellWidth), (cz*cellWidth)+c), new Point2D.Float(((cx+1)*cellWidth), (cz*cellWidth)+c));
				if(moveLine.intersectsLine(wallLine) && maze[cx][cz].getDown()){
					return false;
				}
					
				//check left
				wallLine = new Line2D.Float(new Point2D.Float((cx*cellWidth)+c, (cz*cellWidth)), new Point2D.Float((cx*cellWidth)+c, ((cz+1)*cellWidth)));
				if(moveLine.intersectsLine(wallLine) && maze[cx][cz].getLeft()){
					return false;
				}
					
				//check right
				wallLine = new Line2D.Float(new Point2D.Float(((cx+1)*cellWidth)-c, (cz*cellWidth)), new Point2D.Float(((cx+1)*cellWidth)-c, ((cz+1)*cellWidth)));
				if(moveLine.intersectsLine(wallLine) && maze[cx][cz].getRight()){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void drawGround(){
		display.drawQuadTextured3D(new QuadTextured3D(1000, groundHeight, 1000, 1000, groundHeight, -1000, -1000, groundHeight, -1000, -1000, groundHeight, 1000, texGround, null), 0.15f);
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
	
	public int getMazeWidth(){
		return mazeWidth;
	}
	
	public Texture getTexWall(){
		return texWall;
	}
	
	public Texture getTexGround(){
		return texGround;
	}
	
	public Point getExitPoint(){
		return exitP;
	}
}
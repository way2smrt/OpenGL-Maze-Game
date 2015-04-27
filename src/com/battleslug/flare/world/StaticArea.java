package com.battleslug.flare.world;

import static java.lang.Math.*;

import com.battleslug.porcupine.QuadTextured2D;

public class StaticArea {
	
	private QuadTextured2D quad[] = new QuadTextured2D[1];
	private int quads;
	
	public StaticArea(){
	}
	
	public void addTexturedQuad(QuadTextured2D quad){
		for(int i = 0; i != quads; i++){
			if(this.quad[i] == null){
				this.quad[i] = quad;
				return;
			}
		}
		
		//resize our array for the new quad
		QuadTextured2D temp[] = new QuadTextured2D[quads];
		for(int i = 0; i != quads; i++){
			temp[i] = this.quad[i];
		}
		
		//resize the array
		this.quad = new QuadTextured2D[quads+1];
		
		for(int i = 0; i != quads; i++){
			this.quad[i] = temp[i];
		}
		
		//add our parameter to the last array slot
		this.quad[quads] = quad;
	}
	
	public void draw(){
		//draw texture quads
		for(int i = 0; i != quads; i++){
			if(this.quad[i] == null){
				
			}
		}
		
	}
	
	public class Line {
		private int x1, y1, x2, y2;
		
		public Line(int x1, int y1, int x2, int y2){
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		public int getX1(){
			return x1;
		}
		
		public int getY1(){
			return y1;
		}
		
		public int getX2(){
			return x2;
		}
		
		public int getY2(){
			return y2;
		}
		
		public boolean doesIntersectLine(Line l){
			//y = mx+b equation
			float m1, b1;
			float m2, b2;
			
			//determine slope, make sure x value is not 0, because you can't divide by 0
			if(!isLineVertical(this)){
				m1 = (y2-y1)/(x2-x1);
			}
			else {
				return doesIntersectVertLine(x1, y1, y2, l);
			}
			
			//determine y intercept
			return false;
		}
		
		public int getInterceptX(float m1, float b1, float m2, float b2){
			//TODO
			return 0;
		}
		
		public int getInterceptY(float m1, float b1, float m2, float b2){
			//TODO
			return 0;
		}
		
		private boolean isLineVertical(Line l){
			if(l.getX1()-l.getX2() == 0){
				return true;
			}
			return false;
		}
		
		private float getSlope(Line l){
			//make sure that the line is not vertical, we cannot divide by 0
			return (l.getY2()-l.getY1())/(l.getX2()-l.getX1());
		}
		
		public float getYIntercept(Line l, float slope){
			int y = l.getY1();
			int x = l.getX1();
			
			slope *= -1;
			
			//b=-mx+y
			return (slope*x)+y;
		}
		
		public int getLineX(int y, float m, float b){
			return round((y-b)/m);
		}
		
		public int getLineY(int x, float m, float b){
			return round((m*x)+b);
		}
		
		public boolean doesIntersectVertLine(int x, int y1, int y2, Line l){
			if(!isLineVertical(l)){
				//check to make sure the vertical line location is within range of our line
				if(x >= l.getX1() && x <= l.getX2()){
					float m = getSlope(l);
					float b = getYIntercept(l, m);
					
					if(x >= l.getX1() && x <= l.getX2() && l.getLineX(l.getLineY(x, m, b), m, b) == x){
						return true;
					}
					
					return false;
				}
				return false;
			}
			else if(l.getX1() == x){
				return true;
			}
			
			return false;
		}
	}
}

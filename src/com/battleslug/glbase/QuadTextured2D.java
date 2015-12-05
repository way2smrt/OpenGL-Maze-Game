package com.battleslug.glbase;



public class QuadTextured2D {
	private float x1, x2, x3, x4;
	private float y1, y2, y3, y4;
	
	private VectorColor c;
	private Texture tex;
	
	public QuadTextured2D(float x1, float y1, float x2, float y2, Texture tex, VectorColor c){
		this(x1, y1, x1, y2, x2, y2, x2, y1, tex, c);
	}
	
	public QuadTextured2D(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Texture tex, VectorColor c){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.x4 = x4;
		this.y4 = y4;
		
		this.c = c;
		this.tex = tex;
	}
	
	public float getX1(){
		return x1;
	}
	
	public float getY1(){
		return y1;
	}

	public float getX2(){
		return x2;
	}
	
	public float getY2(){
		return y2;
	}
	
	public float getX3(){
		return x3;
	}
	
	public float getY3(){
		return y3;
	}
	
	public float getX4(){
		return x4;
	}
	
	public float getY4(){
		return y4;
	}
	
	public VectorColor getColor(){
		return c;
	}
	
	public Texture getTexture(){
		return tex;
	}
}

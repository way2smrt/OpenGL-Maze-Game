package com.battleslug.logl2d;



public class TexturedQuad {
	private int x1, x2, x3, x4;
	private int y1, y2, y3, y4;
	
	private VectorColor c;
	private Texture tex;
	
	public TexturedQuad(int x1, int y1, int x2, int y2, Texture tex, VectorColor c){
		this(x1, y1, x1, y2, x2, y2, x2, y1, tex, c);
	}
	
	public TexturedQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Texture tex, VectorColor c){
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
	
	public int getX3(){
		return x3;
	}
	
	public int getY3(){
		return y3;
	}
	
	public int getX4(){
		return x4;
	}
	
	public int getY4(){
		return y4;
	}
	
	public VectorColor getColor(){
		return c;
	}
	
	public Texture getTexture(){
		return tex;
	}
}

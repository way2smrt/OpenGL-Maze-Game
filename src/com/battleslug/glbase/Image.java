package com.battleslug.glbase;

public class Image {
	private Texture tex;
	
	private int xLocal;
	private int yLocal;
	private int width;
	private int height;
	
	public Image(Texture tex){
		this(tex, tex.getWidth(), tex.getHeight());
	}
	
	public Image(Texture tex, int width, int height){
		this(tex, width, height, 0, 0);
	}
	
	public Image(Texture tex, int width, int height,  int xLocal, int yLocal){
		this.tex = tex;

		this.width = width;
		this.height = height;
		
		this.xLocal = xLocal;
		this.yLocal = yLocal;
	}
	
	public void setDimensions(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
	
	public void setLocal(int xLocal, int yLocal){
		this.xLocal = xLocal;
		this.yLocal = yLocal;
	}
	
	public int getXLocal(){
		return xLocal;
	}
	
	public int getYLocal(){
		return yLocal;
	}
	
	public Texture getTexture(){
		return tex;
	}
}

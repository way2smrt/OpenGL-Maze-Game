package com.battleslug.porcupine;

import static java.lang.Math.*;

public class Image {
	private Texture tex;
	
	private int xLocal;
	private int yLocal;
	private int width;
	private int height;
	private int rotation;

	private int framesX;
	private int framesY;
	//private int frameWidth;
	//private int frameHeight;
	private int frame;
	
	public Image(Texture tex){
		//TODO fix fucked up Image class
		this(tex, tex.getWidth(), tex.getHeight());
	}
	
	public Image(Texture tex, int width, int height){
		this(tex, width, height, 0, 0);
	}
	
	public Image(Texture tex, int width, int height,  int xLocal, int yLocal){
		this.tex = tex;
		
		rotation = 0;

		this.width = width;
		this.height = height;
		
		this.xLocal = xLocal;
		this.yLocal = yLocal;
		
		framesX = 1;
		framesY = 1;
	}
	
	public void setDimensions(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public void setRotation(int degrees){
		rotation = degrees;
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

	public int getRotation(){
		return rotation;
	}
	
	private float getPointLengthFromLocal(float xLocal, float yLocal){
		return new Double(sqrt((pow(abs(xLocal-this.xLocal), 2)+pow(abs(yLocal-this.yLocal), 2)))).floatValue();
	}
	
	public boolean isSpritesheet(){
		if((framesX*framesY) >= 1){
			return true;
		}
		return false;
	}

	public int getFrame(){
		return frame;
	}

	public void setFrame(int frame){
		if(frame <= (framesX * framesY) && frame > 0){
			this.frame = frame;
		}
		else {
			frame = 1;
		}
	}

	public void nextFrame(){
		if(frame != (framesX * framesY)){
			frame += 1;
		}
		else {
			frame = 1;
		}
	}

	public Texture getTexture(){
		return tex;
	}
	
	public void draw(Display display, int x, int y, int rotation){
		float xL1 = 0;
		float yL1 = 0;
		float xL2 = 0;
		float yL2 = height;
		float xL3 = width;
		float yL3 = height;
		float xL4 = width;
		float yL4 = 0;		
		
		//create a rotation circle to find our 4 rotated texture points
		Circle circle = new Circle(xLocal, yLocal, getPointLengthFromLocal(xL1, yL1));
		
		float xG1, yG1;
		float xG2, yG2;
		float xG3, yG3;
		float xG4, yG4;
		
		//TODO remove these when done fixing Image
		System.out.println(circle.getRotation(xL1, yL1));
		System.out.println(circle.getRotation(xL2, yL2));
		System.out.println(circle.getRotation(xL3, yL3));
		System.out.println(circle.getRotation(xL4, yL4));
		
		xG1 = x+circle.getX(rotation+circle.getRotation(xL1, yL1));
		yG1 = y+circle.getY(rotation+circle.getRotation(xL1, yL1));
					
		xG2 = x+circle.getX(rotation+circle.getRotation(xL2, yL2));
		yG2 = y+circle.getY(rotation+circle.getRotation(xL2, yL2));
		
		xG3 = x+circle.getX(rotation+circle.getRotation(xL3, yL3));
		yG3 = y+circle.getY(rotation+circle.getRotation(xL3, yL3));
		
		xG4 = x+circle.getX(rotation+circle.getRotation(xL4, yL4));
		yG4 = y+circle.getY(rotation+circle.getRotation(xL4, yL4));
	
		display.drawQuadTextured2D(new QuadTextured2D(xG1, yG1, xG2, yG2, xG3, yG3, xG4, yG4, this.tex, null));   
	}
}

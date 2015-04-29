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
		return new Double(sqrt(pow(abs(xLocal-this.xLocal), 2)+pow(abs(yLocal-this.yLocal), 2))).floatValue();
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
		Circle c1 = new Circle(xLocal, yLocal, getPointLengthFromLocal(xL1, yL1));
		Circle c2 = new Circle(xLocal, yLocal, getPointLengthFromLocal(xL2, yL2));
		Circle c3 = new Circle(xLocal, yLocal, getPointLengthFromLocal(xL3, yL3));
		Circle c4 = new Circle(xLocal, yLocal, getPointLengthFromLocal(xL4, yL3));
				
		float xG1, yG1;
		float xG2, yG2;
		float xG3, yG3;
		float xG4, yG4;
					
		xG1 = x+c1.getX(rotation+c1.getRotation(xL1, yL1));
		yG1 = y+c1.getY(rotation+c1.getRotation(xL1, yL1));
					
		xG2 = x+c2.getX(rotation+c2.getRotation(xL2, yL2));
		yG2 = y+c2.getY(rotation+c2.getRotation(xL2, yL2));
		
		xG3 = x+c3.getX(rotation+c3.getRotation(xL3, yL3));
		yG3 = y+c3.getY(rotation+c3.getRotation(xL3, yL3));
		
		xG4 = x+c4.getX(rotation+c4.getRotation(xL4, yL4));
		yG4 = y+c4.getY(rotation+c4.getRotation(xL4, yL4));
		
		display.drawQuadTextured2D(new QuadTextured2D(xG1, yG1, xG2, yG2, xG3, yG3, xG4, yG4, this.tex, null));   
	}
}

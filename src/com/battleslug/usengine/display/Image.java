package com.battleslug.usengine.display;



public class Image {
	private Texture tex;
	
	private int x;
	private int y;
	
	private int widthSource;
	private int heightSource;
	
	private int framesX;
	private int framesY;
	
	private int frameWidth;
	private int frameHeight;

	private int frame;

	private float xScale;
	private float yScale;

    private int rotation;
	
	public Image(Texture tex, int x, int y){
		this.tex = tex;
		
		rotation = 0;

		xScale = 1;
		yScale = 1;

		framesX = 1;
			framesY = 1;
	}
	

	public void setRotation(int degrees){
		rotation = degrees;
	}

	public int getWidth(){
		return new Float(frameWidth*xScale).intValue();
	}

	public int getHeight(){
		return new Float(frameHeight*yScale).intValue();
	}

	public int getWidthSource(){
		return widthSource;
	}

	public int getHeightSource(){
		return heightSource;
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

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void setScale(float xScale, float yScale){
		this.xScale = xScale;
		this.yScale = yScale;
	}

	public float getXScale(){
		return xScale;
	}

	public float getYScale(){
		return yScale;
	}

	public int getRotation(){
		return rotation;
	}

	public Texture getTexture(){
		return tex;
	}
}

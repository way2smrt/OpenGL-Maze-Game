package com.battleslug.usengine.display;

public class VectorColor {
	private float r, g, b, a;
	
	public VectorColor(float _r, float _g, float _b){
		r = _r;
		g = _g;
		b = _b;
		a = 1.0f;
	}
	
	public VectorColor(float _r, float _g, float _b, float _a){
		r = _r;
		g = _g;
		b = _b;
		a = _a;
	}
	
	public float getRed(){
		return r;
	}
	
	public float getGreen(){
		return g;
	}
	
	public float getBlue(){
		return b;
	}
	
	public float getAlpha(){
		return a;
	}
}

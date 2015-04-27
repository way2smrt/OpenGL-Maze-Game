package com.battleslug.porcupine;

public class QuadTextured3D extends QuadTextured2D {
	private float z1, z2, z3, z4;
	
	public QuadTextured3D(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, float x4, float y4, float z4, Texture tex, VectorColor c){
		super(x1, y1, x2, y2, x3, y3, x4, y4, tex, c);
		
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
		this.z4 = z4;
	}
	
	public float getZ1(){
		return z1;
	}
	
	public float getZ2(){
		return z2;
	}
	
	public float getZ3(){
		return z3;
	}
	
	public float getZ4(){
		return z4;
	}
}

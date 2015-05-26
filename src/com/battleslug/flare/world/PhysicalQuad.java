package com.battleslug.flare.world;

import com.battleslug.glbase.QuadTextured2D;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.VectorColor;


public class PhysicalQuad extends QuadTextured2D{
	public static final int BULLET_PROOF = 0;
	public static final int BULLET_REFLECTIVE = 1;
	public static final int BULLET_PENETRABLE = 2;
	
	private int bulletInteractType;
	private int damageLossOnHit;
	
	public PhysicalQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, Texture tex, VectorColor c, int bulletInteractType){
		super(x1, y1, x2, y2, x3, y3, x4, y4, tex, c);
		
		this.bulletInteractType = bulletInteractType;
	}
	
	public int getBulletInteractType(){
		return bulletInteractType;
	}
	
	public Bullet getBulletOnHit(Bullet b){
		if(bulletInteractType == BULLET_PROOF){
			return null;
		}
		else if (bulletInteractType == BULLET_REFLECTIVE){
			//TODO add bullet return
		}
		else if (bulletInteractType == BULLET_REFLECTIVE){
			//TODO add bullet return
		}
		return null;
	}
}

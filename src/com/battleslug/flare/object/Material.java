package com.battleslug.flare.object;

import com.battleslug.glbase.*;

public class Material {
	private Texture tex;
	private String ID;
	
	public Material(String ID, Texture tex){
		this.tex = tex;
		this.ID = ID;
	}
	
	public String getID(){
		return ID;
	}
	
	public Texture getTexture(){
		return tex;
	}
}

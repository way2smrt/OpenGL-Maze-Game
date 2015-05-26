package com.battleslug.flare.object;

import com.battleslug.glbase.*;

public class Material {
	private Texture tex;
	
	public Material(Texture tex){
		this.tex = tex;
	}
	
	public Texture getTexture(){
		return tex;
	}
}

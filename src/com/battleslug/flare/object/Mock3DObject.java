package com.battleslug.flare.object;

import com.battleslug.flare.world.*;

import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.*;

public class Mock3DObject extends DrawObject {	
	protected Texture tex;
	
	protected Pivot viewer;
	
	protected float width, height;
	
	public Mock3DObject(Display display, Texture tex, float width, float height, Pivot viewer){
		super(display);
		
		this.tex = tex;
		
		this.width = width;
		this.height = height;
		
		this.viewer = viewer;
	}
	
	@Override
	public draw(){
		display.drawQuadTextured3D(new QuadTextured3D());
	}
	
	public void setViewer(Pivot viewer){
		this.viewer = viewer;
	}
	
	public Pivot getViewer(){
		return viewer;
	}
}

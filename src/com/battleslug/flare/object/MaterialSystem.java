package com.battleslug.flare.object;

public class MaterialSystem {
	private Material[] material;
	
	public void MaterialSystem(){
		
	}
	
	public Material getMaterial(String ID){
		for(int i = 0; i != material.length; i++){
			if (material[i].getID() == ID){
				return material[i];
			}
		}
		return null;
	}
	
	public boolean doesMaterialExist(String ID){
		for(int i = 0; i != material.length; i++){
			if (material[i].getID() == ID){
				return true;
			}
		}
		return false;
	}
}

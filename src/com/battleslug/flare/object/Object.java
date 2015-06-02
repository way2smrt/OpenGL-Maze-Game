package com.battleslug.flare.object;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Object {
	private Face[] face;
	private MaterialSystem matSys;
	
	public Object(MaterialSystem matSys, String file){
		face = loadObjectFile(file);
		this.matSys = matSys;
	}
	
	public Object(Face[] face){
		this.face = face;
	}
	
	private Face[] loadObjectFile(String file){
		Face[] temp = new Face[0];
		
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(file)); 
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		
		if(in != null){
			try {
				while(in.ready()){
					//TODO add file loading
				}
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		
		
		return temp;
	}
}

package com.battleslug.glbase;

import java.io.FileNotFoundException;

import static org.lwjgl.openal.AL10.*;

import java.io.FileInputStream;

public class Sound {
	private WaveData waveData;
	
	private int buffer;
	private int source;
	
	public Sound(String file){
		loadALData(file);
	}
	
	public void loadALData(String file) {
		//generate buffers and sources
		int buffer = alGenBuffers();
		int source = alGenSources();
		
		//load wave data
		try {
			waveData =  WaveData.create(new FileInputStream(file));
			
		}
		catch(FileNotFoundException e){
			System.out.println("Could not find file: "+file);
			e.printStackTrace();
		}
		
		try {
			//copy to buffer
			alBufferData(buffer, waveData.format, waveData.data, waveData.samplerate);
		} 
		finally {
			if(waveData != null){
				waveData.dispose();
			}
			else {
				System.out.println("Error: WaveData is null");
			}
		}
			
		//set up source input
		alSourcei(source, AL_BUFFER, buffer);
		
		//loop the sound
		alSourcei(source, AL_LOOPING, AL_TRUE);
	}
	
	public void destroy(){ 
		alDeleteSources(source);
		alDeleteBuffers(buffer);
	}
	
	public void play(){
		alSourcePlay(source);
	}
	
	public void stop(){
		alSourceStop(source);
	}
}

package com.battleslug.flare.sound;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;
 
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;
import org.lwjgl.openal.ALContext;

public class Sound {
	IntBuffer buffer = BufferUtils.createIntBuffer(1);
	
	IntBuffer source = BufferUtils.createIntBuffer(1);
	
	FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	
	FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	
	FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	
	FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	
	FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();
	
	private ALContext context;
	
	public Sound(String file){
		loadALData(file);
		setListenerValues();
	}
	
	public void loadALData(String file) {
		context = ALContext.create(null, 48000, 60, false);
		
		// Load wav data into a buffer.
		AL10.alGenBuffers(buffer);
		 
		if(AL10.alGetError() != AL10.AL_NO_ERROR){
			System.out.println("shits fucked up yo");
		}
		java.io.FileInputStream fin = null;
		try {
			fin = new java.io.FileInputStream(file);
		} catch (java.io.FileNotFoundException ex) {
			ex.printStackTrace();
		}
		WaveData waveFile = WaveData.create(fin);
		try {
			fin.close();
		}
		catch (java.io.IOException ex) {
		}
	
		//Loads the wave file from this class's package in your classpath
		//WaveData waveFile = WaveData.create(file);
		
		//Bind the buffer with the source.
		AL10.alGenSources(source);
	
		AL10.alSourcei(source.get(0), AL10.AL_BUFFER, buffer.get(0));
		AL10.alSourcef(source.get(0), AL10.AL_PITCH, 1.0f);
		AL10.alSourcef(source.get(0), AL10.AL_GAIN, 1.0f);
		AL10.alSource (source.get(0), AL10.AL_POSITION, sourcePos);
		AL10.alSource (source.get(0), AL10.AL_VELOCITY, sourceVel);
	}
	
	void setListenerValues() {
		AL10.alListener(AL10.AL_POSITION, listenerPos);
		AL10.alListener(AL10.AL_VELOCITY, listenerVel);
		AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
	}
	
	public void killALData(){ 
		AL10.alDeleteSources(source);
		AL10.alDeleteBuffers(buffer);
	}
	
	public void play(){
		AL10.alSourcePlay(source.get(0));
	}
	
	public void stop(){
		AL10.alSourceStop(source.get(0));
	}
}

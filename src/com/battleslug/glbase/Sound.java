package com.battleslug.glbase;

import java.io.FileNotFoundException;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import java.io.FileInputStream;
import javax.sound.sampled.*;
import java.io.File;

import org.lwjgl.BufferUtils;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL.*;
import static org.lwjgl.openal.Util.*;

import org.lwjgl.util.WaveData;

public class Sound {
	private IntBuffer buffer;
	private IntBuffer source;

	//sound location and velocity data
	FloatBuffer sourcePos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer sourceVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer listenerPos = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer listenerVel = (FloatBuffer)BufferUtils.createFloatBuffer(3).put(new float[] { 0.0f, 0.0f, 0.0f }).rewind();
	FloatBuffer listenerOri = (FloatBuffer)BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f }).rewind();
	
	public Sound(String file){
		loadALData(file);
	}
	
	private void loadALData(String file){
		//denullify our buffers
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		IntBuffer source = BufferUtils.createIntBuffer(1);
		
		int[] format = new int[1];
		int[] size = new int[1];
		ByteBuffer[] data = new ByteBuffer[1];
		int[] freq = new int[1];
		int[] loop = new int[1];

		//load wave data into buffers
		WaveData waveFile = WaveData.create(AudioSystem.getAudioInputStream(new File(file)));
		//alutLoadWAVFile(file, format, data, size, freq, loop);
		
		//buffer the data into openal
		alBufferData(buffer.get(0), format[0], data[0], size[0], freq[0]);

		waveFile.dispose();
		
		//generate the sources from buffer
		alGenSources(source);
		
		//generate the buffers
		alGenBuffers(buffer);
			
		//set up source input
		alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
		
		//set up looping
		alSourcei(source.get(0), AL_LOOPING, AL_TRUE);
		
		setListenerValues();
	}
	
	private void setListenerValues(){
		alListener(AL_POSITION, listenerPos);
		alListener(AL_VELOCITY, listenerVel);
		alListener(AL_ORIENTATION, listenerOri);
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

package com.battleslug.unitystrike;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture {
	private ByteBuffer buffer;
	private InputStream in;
	private PNGDecoder decoder;
	
	
	public Texture(String _filename){
		loadTexture(_filename);
	}
	
	private ByteBuffer loadTexture(String filename){
		
		try{
			in = new FileInputStream(filename);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		try {
			decoder = new PNGDecoder(in);
		
			buffer = ByteBuffer.allocateDirect(4*decoder.getWidth()*decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth()*4, Format.RGBA);
			buffer.flip();
		
		return buffer;
		} 
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			}
			catch(IOException e){
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public void bindTexture(){
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}
	
	public int getHeight(){
		return decoder.getHeight();
		
	}
	
	public int getWidth(){
		return decoder.getWidth();
	}
}

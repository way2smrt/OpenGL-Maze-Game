package com.battleslug.logl2d;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Texture {
<<<<<<< HEAD:src/com/battleslug/usengine/Texture.java
    public int id;
    
    public int width;
    public int height;
    
    public float xScale;
    public float yScale;
    
    public int rotation;

=======
	private int id;
	
	private int width, height;
	
>>>>>>> 08a186fe0bb31795077213569da8f09853c7a8f0:src/com/battleslug/logl2d/Texture.java
    public static final int LINEAR = GL_LINEAR;
    public static final int NEAREST = GL_NEAREST;

    public static final int CLAMP = GL_CLAMP;
    public static final int CLAMP_TO_EDGE = GL_CLAMP_TO_EDGE;
    public static final int REPEAT = GL_REPEAT;

    public Texture(String file) {
        this(file, GL_LINEAR, GL_CLAMP_TO_EDGE);
    }

    public Texture(String file, int filt, int wrap) {
    	InputStream input = null;
    	
    	if(new File(file).exists() && !new File(file).isDirectory()){
    		try{
    			System.out.println("Loading file: "+file);
        		input = new FileInputStream(file);
        	}
        	catch(FileNotFoundException e){
        		e.printStackTrace();
        	}
    	}
    	else{
    		System.out.println("File: "+file+" does not exist or cannot be accessed");
    	}
    	
    	
        try {
        	final int bpp = 4;
      
            PNGDecoder decoder = new PNGDecoder(input);

            width = decoder.getWidth();
            height = decoder.getHeight();

            ByteBuffer buffer = BufferUtils.createByteBuffer(bpp * width * height);

            decoder.decode(buffer, width * bpp, PNGDecoder.Format.RGBA);

            buffer.flip();

            id = glGenTextures();

            bind();

            glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filt);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filt);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        } 
        catch(IOException e){
        	e.printStackTrace();
        }
        finally {
            if (input != null) {
                try{ 
                	input.close(); 
                } 
                catch(IOException e) {
                	e.printStackTrace();
                }
            }
        }
<<<<<<< HEAD:src/com/battleslug/usengine/Texture.java
        
        rotation = 0;
        
        xScale = 1;
        yScale = 1;
    }
=======
>>>>>>> 08a186fe0bb31795077213569da8f09853c7a8f0:src/com/battleslug/logl2d/Texture.java

    }
<<<<<<< HEAD:src/com/battleslug/usengine/Texture.java
    
    public void setRotation(int degrees){
    	rotation = degrees;
    }
    
=======

>>>>>>> 08a186fe0bb31795077213569da8f09853c7a8f0:src/com/battleslug/logl2d/Texture.java
    public int getWidth(){
    	return new Float(width*xScale).intValue();
    }
    
    public int getHeight(){
    	return new Float(height*yScale).intValue();
    }
    
    public int getWidthSource(){
    	return width;
    }
    
    public int getHeightSource(){
    	return height;
    }
    
<<<<<<< HEAD:src/com/battleslug/usengine/Texture.java
    public int getRotation(){
    	return rotation;
    }
    
    public void setScale(float xScale, float yScale){
    	this.xScale = xScale;
    	this.yScale = yScale;
    }
    
    public float getXScale(){
    	return xScale;
    }
    
    public float getYScale(){
    	return yScale;
    }
    
=======
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }
>>>>>>> 08a186fe0bb31795077213569da8f09853c7a8f0:src/com/battleslug/logl2d/Texture.java
}

package com.battleslug.usengine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.awt.geom.Ellipse2D;

public class Display {	
	private GLFWKeyCallback keyCallback;
	private GLFWErrorCallback errorCallback;

	private long window;
	
	public static int texWidth = 32;
	
	private String name;
	private int width, height;
	
	public Display(String _name, int _w, int _h){
		name = _name;
		width = _w;
		height = _h;
	}
	
    public void create() {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        
        if (glfwInit() != GL11.GL_TRUE){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
 
        glfwDefaultWindowHints(); 
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE); 
 
        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if(window == NULL){
            throw new RuntimeException("Failed to create the GLFW window");
        }
 
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods){
            	  if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE){
            		  glfwSetWindowShouldClose(window, GL_TRUE);
            	  }
            }
        });
 
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        //center it
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - width) / 2,
            (GLFWvidmode.height(vidmode) - height) / 2
        );
 
        glfwMakeContextCurrent(window);

        //v-sync
        glfwSwapInterval(1);
 
        glfwShowWindow(window);
        
        GLContext.createFromCurrent();
        
        glClearColor(0.4f, 0.6f, 0.8f, 0.0f);
        
        glMatrixMode(GL_PROJECTION);
    	glLoadIdentity();
    	glOrtho(0, width, height, 0, 10, -10);
    	glMatrixMode(GL_MODELVIEW);
        
        glEnable(GL_TEXTURE_2D);
<<<<<<< HEAD
        
        glEnable(GL_BLEND);
=======
        glEnable(GL_DEPTH_TEST);
        glEnable (GL_BLEND);
        
>>>>>>> 5b7bde91c14779b396e7237316a61bc03e662d20
        glBlendFunc (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glEnable(GL_DEPTH_TEST);
        
        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER, 0.0f);
    	
        glLoadIdentity();
    }
    
    public void clear(){
    	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public void update(){
    	glfwSwapBuffers(window); 	 
        glfwPollEvents();
        
        if (glfwWindowShouldClose(window) == GL_TRUE){
    		kill();
    	}
    }
    
    public void drawRectangle(int x1, int y1, int x2, int y2, VectorColor c){
    	glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
        
    	glBegin(GL_QUADS);
        glVertex2f(x1, y1);
        glVertex2f(x1, y2);
        glVertex2f(x2, y2);
        glVertex2f(x2, y1);
        glEnd();
    }
    
    public void drawTexturedQuad(TexturedQuad quad){	
    	quad.getTexture().bind();
    	
        float u = 0f;
        float v = 0f;
        float u2 = 1f;
        float v2 = 1f;
        
        if(quad.getColor() == null){
        	glColor4f(1f, 1f, 1f, 1f);
        }
        else {
        	glColor4f(quad.getColor().getRed(), quad.getColor().getGreen(), quad.getColor().getBlue(), quad.getColor().getAlpha());
        }
        
        glBegin(GL_QUADS);
        
        glTexCoord2f(u, v);
        glVertex2f(quad.getX1(), quad.getY1());
        
        glTexCoord2f(u, v2);
        glVertex2f(quad.getX2(), quad.getY2());
        
        glTexCoord2f(u2, v2);
        glVertex2f(quad.getX3(), quad.getY3());
        
        glTexCoord2f(u2, v);
        glVertex2f(quad.getX4(), quad.getY4());
        glEnd();
    }
    
    /**
     * @param tex The texture to draw
     * @param x The x location to draw it at
     * @param y The y location to draw it at
     */
    public void drawTexture(Texture tex, int x, int y){  	  	
    	if (tex.getRotation() == 0){
    		tex.bind();
    		
    		float u = 0f;
    		float v = 0f;
            float u2 = 1f;
            float v2 = 1f;

            glColor4f(1f, 1f, 1f, 1f);
            glBegin(GL_QUADS);
            
            glTexCoord2f(u, v);
            glVertex2f(x, y);
            
            glTexCoord2f(u, v2);
            glVertex2f(x, y+tex.getHeight());
            
            glTexCoord2f(u2, v2);
            glVertex2f(x+tex.getWidth(), y+tex.getHeight());
            
            glTexCoord2f(u2, v);
            glVertex2f(x+tex.getWidth(), y);
            glEnd();
    	}
    	else {
    		//find the center, then the radius using pythagorean theorem
<<<<<<< HEAD
    		Circle circle = new Circle((new Float(tex.getWidth()*tex.getXScale()).intValue()/2)+x, (new Float(tex.getHeight()*tex.getYScale()).intValue()/2)+y, new Double(Math.sqrt(Math.pow((new Float(tex.getWidth()*tex.getXScale()).intValue()/2), 2)+Math.pow((new Float(tex.getHeight()*tex.getYScale()).intValue()/2), 2))).intValue());
    		
    		int x1, y1;
    		int x2, y2;
    		int x3, y3;
    		int x4, y4;
=======
    		Circle circle = new Circle((tex.getWidth()/2)+x, (tex.getHeight()/2)+y, new Double(Math.sqrt(Math.pow((tex.getWidth()/2), 2)+Math.pow((tex.getHeight()/2), 2))).intValue());
    		
    		int x1, y1;
    		int x2, y2;
    		
    		int r2 = circle.getRotation(x+tex.getWidth(), y+tex.getWidth());
    
    		System.out.println(r2);
>>>>>>> 5b7bde91c14779b396e7237316a61bc03e662d20
    		
    		x1 = circle.getX(tex.getRotation());
    		y1 = circle.getY(tex.getRotation());
    		
<<<<<<< HEAD
    		x2 = circle.getX(90+tex.getRotation());
    		y2 = circle.getY(90+tex.getRotation());
    		
    		x3 = circle.getX(180+tex.getRotation());
    		y3 = circle.getY(180+tex.getRotation());
    		
    		x4 = circle.getX(270+tex.getRotation());
    		y4 = circle.getY(270+tex.getRotation());
    		
    		drawTexturedQuad(new TexturedQuad(x1, y1, x2, y2, x3, y3, x4, y4, tex, null));
=======
    		x2 = circle.getX(180+tex.getRotation());
    		y2 = circle.getY(180+tex.getRotation());
    		
    		System.out.println(x1);
    		System.out.println(y1);
    		
    		System.out.println(x2);
    		System.out.println(y2);
    		
    		drawTexturedQuad(new TexturedQuad(x1, y1, x1, y2, x2, y2, x2, y1, tex, null));
>>>>>>> 5b7bde91c14779b396e7237316a61bc03e662d20
    	}
    }
    
    /**
     * @param tex The texture to draw
     * @param x The x location to draw it at
     * @param y The y location to draw it at
     * @param tX The x value of the texture on the spritesheet
     * @param tY The x value of the texture on the spritesheet
     * @param tSize The width/height of the textures on the spritesheet
     */
    public void drawSpriteSheet(Texture tex, int x, int y, int tX, int tY, int tSize){  	
    	tex.bind();
    	
        float u = (tSize / tex.getWidth()) * tX;
        float v = (tSize / tex.getWidth()) * tX;
        float u2 = u+(tSize / tex.getWidth());
        float v2 = v+(tSize / tex.getWidth());

        glColor4f(1f, 1f, 1f, 1f);
        glBegin(GL_QUADS);
        
        glTexCoord2f(u, v);
        glVertex2f(x, y);
        
        glTexCoord2f(u, v2);
        glVertex2f(x, y+tex.getHeight());
        
        glTexCoord2f(u2, v2);
        glVertex2f(x+tex.getWidth(), y+tex.getHeight());
        
        glTexCoord2f(u2, v);
        glVertex2f(x+tex.getWidth(), y);
        glEnd();
    }
    
    public void kill(){
    	glfwDestroyWindow(window);
        keyCallback.release();
        glfwTerminate();
        errorCallback.release();
        System.exit(0);
    }
    
    public long getWindowID(){
    	return window;
    }
    
    public class VectorColor {
    	private float r, g, b, a;
    	
    	public VectorColor(float _r, float _g, float _b){
    		r = _r;
    		g = _g;
    		b = _b;
    		a = 1.0f;
    	}
    	
    	public VectorColor(float _r, float _g, float _b, float _a){
    		r = _r;
    		g = _g;
    		b = _b;
    		a = _a;
    	}
    	
    	public float getRed(){
    		return r;
    	}
    	
    	public float getGreen(){
    		return g;
    	}
    	
    	public float getBlue(){
    		return b;
    	}
    	
    	public float getAlpha(){
    		return a;
    	}
    }
}
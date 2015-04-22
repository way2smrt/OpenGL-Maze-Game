package com.battleslug.logl2d;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Display {	
	private GLFWKeyCallback keyCallback;
	private GLFWErrorCallback errorCallback;

	private long window;
	
	public static int texWidth = 32;
	
	private String title;
	private int width, height;
	
	private long xMouse, yMouse;
	
	public static final int HINT_RESIZABLE = GLFW_RESIZABLE;
	public static final int HINT_VISIBLE = GLFW_VISIBLE;
	public static final int HINT_DECORATED = GLFW_DECORATED;
	public static final int HINT_FLOATING = GLFW_FLOATING;
	
	public Display(String title, int width, int height){
		this.title = title;
		this.width = width;
		this.height = height;
		
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
	}
	
	public void create() {
		window = glfwCreateWindow(width, height, title, NULL, NULL);
		
		if(window == NULL){
			throw new RuntimeException("Failed to create the GLFW window");
		}
 
		ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		//center it
		glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);
 
		glfwMakeContextCurrent(window);

		//v-sync
		glfwSwapInterval(1);
 
		GLContext.createFromCurrent();

		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_DEPTH_TEST);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0f);

		glLoadIdentity();
	}
	
	public void setHint(int hint, boolean state){
		if(state){
			glfwWindowHint(hint, GL_TRUE);
		}
		else {
			glfwWindowHint(hint, GL_FALSE);
		}
	}

	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public void checkClose(){	
		if (glfwWindowShouldClose(window) == GL_TRUE){
			kill();
		}
	}

	public void update(){
		glfwSwapBuffers(window);
		checkClose();
	}
	
	public void hide(){
		glfwHideWindow(window);
	}
	
	public void show(){
		glfwShowWindow(window);
	}
	
	public void drawPixel(int x, int y, VectorColor c){
		glDisable(GL_TEXTURE_2D);
		
		glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		
		glBegin(GL_POINTS);
		glVertex2f(x, y);
		glEnd();
		
	}
	
	public void drawRectangle(int x1, int y1, int x2, int y2, VectorColor c){
		glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		
		drawColoredQuad(x1, y1, x1, y2, x2, y2, x2, y1, c);
	}

	public void drawColoredQuad(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, VectorColor c){
		glDisable(GL_TEXTURE_2D);
		
		glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());

		glBegin(GL_QUADS);
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		glVertex2f(x3, y3);
		glVertex2f(x4, y4);
		glEnd();
	}

	public void drawTexturedQuad(TexturedQuad quad){
		glEnable(GL_TEXTURE_2D);
		
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
	
	public void setKeyCallback(GLFWKeyCallback keyCallback){
		this.keyCallback = keyCallback;
		glfwSetKeyCallback(window, keyCallback);
	}

	public GLFWKeyCallback getKeyCallback(){
		return keyCallback;
	}

	public void kill(){
		glfwDestroyWindow(window);
		keyCallback.release();
		glfwTerminate();
		errorCallback.release();
		System.exit(0);
	}

	public long getID(){
		return window;
	}
}

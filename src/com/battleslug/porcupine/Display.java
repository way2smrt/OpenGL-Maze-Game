package com.battleslug.porcupine;

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.util.glu.GLU.*;
import org.lwjgl.BufferUtils;

import java.util.Random;

public class Display {	
	private GLFWKeyCallback keyCallback;
	private GLFWErrorCallback errorCallback;

	private long window;
	
	public static int texWidth = 32;
	
	private String title;
	private int width, height;
	
	private double xCursor, yCursor;
	private double cursorRotHoriChange, cursorRotVertChange;
	
	private boolean fullscreen;
	
	public static final int HINT_RESIZABLE = GLFW_RESIZABLE;
	public static final int HINT_VISIBLE = GLFW_VISIBLE;
	public static final int HINT_DECORATED = GLFW_DECORATED;
	public static final int HINT_FLOATING = GLFW_FLOATING;
	
	public enum RotationMode{AXIS_X, AXIS_Y, AXIS_Z, AXIS_XY, AXIS_YZ, AXIS_ZX, AXIS_XYZ};
	
	public enum DrawMode{MODE_2D, MODE_3D};
	public enum ColorMode{MODE_COLOR, MODE_TEXTURE};
	public DrawMode drawMode;
	
	private int aspectRatio;
	
	private float camX, camY, camZ;
	private float camHoriRot;
	private float camVertRot;
	
	private static final float near = 1f;
	public static final float far = 1000.0f;
	
	public final float WIDTH_TEXTURE = 1.0f;
	
	private boolean cursorLocked;
	
	public Display(String title, int width, int height){
		this(title, width, height, false);
	}
	
	public Display(String title, int width, int height, boolean fullscreen){
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
	}
	
	public void create() {
		if(fullscreen){
			window = glfwCreateWindow(width, height, title,glfwGetPrimaryMonitor(), NULL);
		}
		else {
			window = glfwCreateWindow(width, height, title, NULL, NULL);
		}
			
			
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

		glViewport(0, 0, width, height);
		glOrtho(0, width, height, 0, 1, -1);
		
		//manual resizing callback
		glfwSetFramebufferSizeCallback(window, new GLFWFramebufferSizeCallback(){
			@Override
			public void invoke(long window, int width, int height){
				glViewport(0, 0, width, height);
				glfwPollEvents();
			}
		});
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0f);
		
		glEnable(GL_CULL_FACE);
		
		glEnable(GL_DEPTH_TEST);
		glClearDepth(far);
		glDepthFunc(GL_LEQUAL);

		glLoadIdentity();
		
		aspectRatio = width/height;
		
		glFrontFace(GL11.GL_CW);
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
		
		if (cursorLocked){
			double xCursorOld = xCursor;
			double yCursorOld = yCursor;
			
			updateCursor();
			
			cursorRotHoriChange = xCursor-xCursorOld;
			cursorRotVertChange = yCursor-yCursorOld;
			
			glfwSetCursorPos(window, width/2, height/2);
			
			updateCursor();
		}
		
		checkClose();
	}
	
	public void hide(){
		glfwHideWindow(window);
	}
	
	public void show(){
		glfwShowWindow(window);
	}
	
	public void drawPixel(int x, int y, VectorColor c){
		setMode(DrawMode.MODE_2D);
		
		glDisable(GL_TEXTURE_2D);
		
		glColor4f(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
		
		glBegin(GL_POINTS);
		glVertex2f(x, y);
		glEnd();
		
	}
	
	public void drawCube(float x, float y, float z, Texture tex){
		setColorMode(ColorMode.MODE_TEXTURE);
		setMode(DrawMode.MODE_3D);
		
        drawQuadTextured3D(new QuadTextured3D(x+0.5f, y+0.5f, z-0.5f, x-0.5f, y+0.5f, z-0.5f, x-0.5f, y+0.5f, z+0.5f, x+0.5f, y+0.5f, z+0.5f, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+0.5f, y-0.5f, z+0.5f, x-0.5f, y-0.5f, z+0.5f, x-0.5f, y-0.5f, z-0.5f, x+0.5f, y-0.5f, z-0.5f, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+0.5f, y+0.5f, z+0.5f, x-0.5f, y+0.5f, z+0.5f, x-0.5f, y-0.5f, z+0.5f, x+0.5f, y-0.5f, z+0.5f, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+0.5f, y-0.5f, z-0.5f, x-0.5f, y-0.5f, z-0.5f, x-0.5f, y+0.5f, z-0.5f, x+0.5f, y+0.5f, z-0.5f, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x-0.5f, y+0.5f, z+0.5f, x-0.5f, y+0.5f, z-0.5f, x-0.5f, y-0.5f, z-0.5f, x-0.5f, y-0.5f, z+0.5f, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+0.5f, y+0.5f, z-0.5f, x+0.5f, y+0.5f, z+0.5f, x+0.5f, y-0.5f, z+0.5f, x+0.5f, y-0.5f, z-0.5f, tex, null));
	}

	public void drawQuadTextured2D(QuadTextured2D quad){
		setMode(DrawMode.MODE_2D);
		
		quad.getTexture().bind();
		
		float u = 0f;
		float v = 0f;
		float u2 = 1f;
		float v2 = 1f;

		if(quad.getColor() == null){
			setColorMode(ColorMode.MODE_TEXTURE);
			glColor4f(1f, 1f, 1f, 1f);
		}
		else {
			setColorMode(ColorMode.MODE_COLOR);
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
	
	public void drawQuadTextured3D(QuadTextured3D quad){
		setMode(DrawMode.MODE_3D);
		
		quad.getTexture().bind();
		
		float u = 0f;
		float v = 0f;
		
		float u2 = 1f;
		float v2 = 1f;
		
		if(quad.getColor() == null){
			setColorMode(ColorMode.MODE_TEXTURE);
			glColor4f(1f, 1f, 1f, 1f);
		}
		else {
			setColorMode(ColorMode.MODE_COLOR);
			glColor4f(quad.getColor().getRed(), quad.getColor().getGreen(), quad.getColor().getBlue(), quad.getColor().getAlpha());
		}
		
		glBegin(GL_QUADS);
		
		//TODO, create proper texture sizing
		glTexCoord2f(u, v);
		glVertex3f(quad.getX1(), quad.getY1(), quad.getZ1());

		glTexCoord2f(u, v2);
		glVertex3f(quad.getX2(), quad.getY2(), quad.getZ2());

		glTexCoord2f(u2, v2);
		glVertex3f(quad.getX3(), quad.getY3(), quad.getZ3());
		
		glTexCoord2f(u2, v);
		glVertex3f(quad.getX4(), quad.getY4(), quad.getZ4());
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
	
	private void randGlColor3f(){
		glColor3f(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat());
	}

	public void setMode(DrawMode mode){	
		if(mode != drawMode){
			glLoadIdentity();
			switch(mode){
				case MODE_2D:
					drawMode = mode;
					glMatrixMode(GL_MODELVIEW);
					glOrtho(0, width, height, 0, near-1, near);
					break;
				case MODE_3D:
					drawMode = mode;
					glMatrixMode(GL_PROJECTION);
					gluPerspective(45, aspectRatio, near, far);
					Circle hori = new Circle(camX, camY, far);
					gluLookAt(camX, camY, camZ, hori.getX(camHoriRot), camY, hori.getY(camHoriRot), camX, camY+far, camZ);
					break;
			}
		}
	}
	
	public void setColorMode(ColorMode mode){
		switch(mode){
			case MODE_COLOR:
				glDisable(GL_TEXTURE_2D);
			case MODE_TEXTURE:
				glEnable(GL_TEXTURE_2D);
		}
	}
	
	public int getAspectRatio(){
		return aspectRatio;
	}
	
	public long getID(){
		return window;
	}
	
	public void setCamLocation(float camX, float camY, float camZ){
		this.camX = camX;
		this.camY = camY;
		this.camZ = camZ;
	}
	
	public float getCamX(){
		return camX;
	}
	
	public float getCamY(){
		return camY;
	}
	
	public float getCamZ(){
		return camZ;
	}
	
	public void setCamHorizontalRot(float camHoriRot){
		this.camHoriRot = camHoriRot;
	}
	
	public void setCamVerticalRot(float camVertRot){
		this.camVertRot = camVertRot;
	}
	
	public float getCamHorizontalRot(){
		return camHoriRot;
	}
	
	public float getCamVerticalRot(){
		return camVertRot;
	}
	
	private void updateCursor(){
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(window, x, y);
		
		xCursor = x.get();
		yCursor = y.get();
	}
	
	public void setCursorLocked(boolean cursorLocked){
		this.cursorLocked = cursorLocked;
	
		if (cursorLocked){
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
		}
		else {
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}
	}
	
	public boolean isCursorLocked(){
		return cursorLocked;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public double getCursorRotHoriChange(){
		return cursorRotHoriChange;
	}
	
	public double getCursorRotVertChange(){
		return cursorRotVertChange;
	}
}

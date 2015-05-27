package com.battleslug.glbase;

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

import com.battleslug.glbase.geometry.Pivot;
import com.battleslug.glbase.geometry.Point;

import static java.lang.Math.*;

public class Display {	
	private GLFWKeyCallback keyCallback;
	private GLFWErrorCallback errorCallback;
	private GLFWMouseButtonCallback mouseCallback;

	private long window;
	
	public static int texWidth = 32;
	
	private String title;
	private int width, height;
	
	private double xCursor, yCursor;
	private double cursorRotXZAxisChange, cursorRotYZAxisChange;
	
	private boolean fullscreen;
	
	public static final int HINT_RESIZABLE = GLFW_RESIZABLE;
	public static final int HINT_VISIBLE = GLFW_VISIBLE;
	public static final int HINT_DECORATED = GLFW_DECORATED;
	public static final int HINT_FLOATING = GLFW_FLOATING;
	
	public enum RotationMode{AXIS_X, AXIS_Y, AXIS_Z, AXIS_XY, AXIS_YZ, AXIS_ZX, AXIS_XYZ};
	
	public enum ModeDraw{MODE_2D, MODE_3D};
	public enum ModeColor{MODE_COLOR, MODE_TEXTURE};
	private ModeDraw modeDraw;
	private ModeColor modeColor;
	
	private int aspectRatio;
	
	private float camX, camY, camZ;
	
	private static final float NEAR = 0.01f;
	public static final float FAR = 1000.0f;
	
	public static final float FOV = 45f;
	
	public final float WIDTH_TEXTURE = 1.0f;
	
	private boolean cursorLocked;
	
	private Pivot pivotCam;
	
	private double timeLast = 0;
	private double timePassed = 0;
	
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

		if(!fullscreen){
			//center window
			glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - width) / 2, (GLFWvidmode.height(vidmode) - height) / 2);
		}
		
 
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
		
		//release cursor when it exits the window, and regain upon re-entering
		glfwSetCursorEnterCallback(window, new GLFWCursorEnterCallback(){
			@Override
			public void invoke(long window, int entered){
				if(entered == GL_TRUE){
					glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
					glfwSetCursorPos(window, width/2, height/2);
				}
				else if(entered == GL_FALSE){
					setCursorLocked(false);
				}
			}
		});
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glEnable(GL_ALPHA_TEST);
		glAlphaFunc(GL_GREATER, 0.0f);
		
		glEnable(GL_CULL_FACE);
		
		glEnable(GL_DEPTH_TEST);
		glClearDepth(FAR);
		glDepthFunc(GL_LEQUAL);

		glLoadIdentity();
		
		aspectRatio = width/height;
		
		modeColor = ModeColor.MODE_COLOR;
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
			
			cursorRotXZAxisChange = xCursor-xCursorOld;
			cursorRotYZAxisChange = yCursor-yCursorOld;
			
			glfwSetCursorPos(window, width/2, height/2);
			
			updateCursor();
		}
		
		checkClose();
	}
	
	public static void updateEvents(){
		glfwPollEvents();
	}
	
	public void hide(){
		glfwHideWindow(window);
	}
	
	public void show(){
		glfwShowWindow(window);
	}
	
	public void drawLine3D(Point p1, Point p2, VectorColor c1, VectorColor c2){
		setMode(ModeDraw.MODE_3D, ModeColor.MODE_COLOR);
		
		glBegin(GL_LINES);
		glColor4f(c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
		glVertex3f(p1.getX(), p1.getY(), p1.getZ());
		glColor4f(c2.getRed(), c2.getGreen(), c2.getBlue(), c2.getAlpha());
		glVertex3f(p2.getX(), p2.getY(), p2.getZ());
		glEnd();
	}
	
	public void drawLine(float x1, float y1, float x2, float y2, VectorColor c1, VectorColor c2){
		setMode(ModeDraw.MODE_2D, ModeColor.MODE_COLOR);
		
		glBegin(GL_LINES);
		glColor4f(c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
		glVertex2f(x1, y1);
		glColor4f(c2.getRed(), c2.getGreen(), c2.getBlue(), c2.getAlpha());
		glVertex2f(x2, y2);
		glEnd();
	}
	
	/*
	public void drawLine(Point p1, Point p2, VectorColor c1, VectorColor c2){
		setMode(ModeDraw.MODE_2D, ModeColor.MODE_COLOR);
		
		glBegin(GL_LINES);
		glColor4f(c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
		glVertex2f(x1, y1);
		glColor4f(c2.getRed(), c2.getGreen(), c2.getBlue(), c2.getAlpha());
		glVertex2f(x2, y2);
		glEnd();
	}
	*/
	
	public void drawCube(float x, float y, float z, float width, Texture tex){
		setMode(ModeDraw.MODE_3D, ModeColor.MODE_TEXTURE);
		
		float widthHalf = width/2;
		
        drawQuadTextured3D(new QuadTextured3D(x+widthHalf, y+widthHalf, z-widthHalf, x-widthHalf, y+widthHalf, z-widthHalf, x-widthHalf, y+widthHalf, z+widthHalf, x+widthHalf, y+widthHalf, z+widthHalf, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+widthHalf, y-widthHalf, z+widthHalf, x-widthHalf, y-widthHalf, z+widthHalf, x-widthHalf, y-widthHalf, z-widthHalf, x+widthHalf, y-widthHalf, z-widthHalf, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+widthHalf, y+widthHalf, z+widthHalf, x-widthHalf, y+widthHalf, z+widthHalf, x-widthHalf, y-widthHalf, z+widthHalf, x+widthHalf, y-widthHalf, z+widthHalf, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+widthHalf, y-widthHalf, z-widthHalf, x-widthHalf, y-widthHalf, z-widthHalf, x-widthHalf, y+widthHalf, z-widthHalf, x+widthHalf, y+widthHalf, z-widthHalf, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x-widthHalf, y+widthHalf, z+widthHalf, x-widthHalf, y+widthHalf, z-widthHalf, x-widthHalf, y-widthHalf, z-widthHalf, x-widthHalf, y-widthHalf, z+widthHalf, tex, null));
        drawQuadTextured3D(new QuadTextured3D(x+widthHalf, y+widthHalf, z-widthHalf, x+widthHalf, y+widthHalf, z+widthHalf, x+widthHalf, y-widthHalf, z+widthHalf, x+widthHalf, y-widthHalf, z-widthHalf, tex, null));
	}

	public void drawQuadTextured2D(QuadTextured2D quad){
		quad.getTexture().bind();
		
		float u = 0f;
		float v = 0f;
		float u2 = 1f;
		float v2 = 1f;

		if(quad.getColor() == null){
			setMode(ModeDraw.MODE_2D, ModeColor.MODE_TEXTURE);
			glColor4f(1f, 1f, 1f, 1f);
		}
		else {
			setMode(ModeDraw.MODE_2D, ModeColor.MODE_COLOR);
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
		quad.getTexture().bind();
		
		float TEXTURE_SCALING = 0.12f;
		
		float u = 0f;
		float v = 0f;
		
		float u2 = TEXTURE_SCALING*Point.getDistance(new Point(quad.getX3(), quad.getY3(), quad.getZ3()), new Point(quad.getX4(), quad.getY4(), quad.getZ4()));
		float v2 = TEXTURE_SCALING*Point.getDistance(new Point(quad.getX1(), quad.getY1(), quad.getZ1()), new Point(quad.getX2(), quad.getY2(), quad.getZ2()));
		
		if(quad.getColor() == null){
			setMode(ModeDraw.MODE_3D, ModeColor.MODE_TEXTURE);
			glColor4f(1f, 1f, 1f, 1f);
		}
		else {
			setMode(ModeDraw.MODE_3D, ModeColor.MODE_COLOR);
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
	
	public void setMouseButtonCallback(GLFWMouseButtonCallback mouseCallback){
		this.mouseCallback = mouseCallback;
		glfwSetMouseButtonCallback(window, mouseCallback);
	}

	public GLFWKeyCallback getKeyCallback(){
		return keyCallback;
	}
	
	public GLFWMouseButtonCallback getMouseButtonCallback(){
		return mouseCallback;
	}

	public void kill(){
		glfwDestroyWindow(window);
		keyCallback.release();
		glfwTerminate();
		errorCallback.release();
		System.exit(0);
	}

	public void setMode(ModeDraw drawMode, ModeColor colorMode){	
		if(this.modeDraw != drawMode){
			switch(drawMode){
				case MODE_2D:
					this.modeDraw = drawMode;
					glLoadIdentity();
					glMatrixMode(GL_MODELVIEW);
					glOrtho(0, width, height, 0, NEAR-0.01, NEAR-0.001);
					break;
				case MODE_3D:
					this.modeDraw = drawMode;
					glLoadIdentity();
					glMatrixMode(GL_PROJECTION);
					gluPerspective(FOV, aspectRatio, NEAR, FAR);
					
					//we have to add a small number to the circle radius to ensure we don't get zero
					Circle cXZ = new Circle(0, 0, (float)(cos(toRadians(pivotCam.getRotYZAxis()-90)))+0.01f);
					
					//this shit is complicated - do not fiddle!
					gluLookAt(camX, camY, camZ, camX+cXZ.getX(pivotCam.getRotXZAxis()), camY+(float)(sin(toRadians(pivotCam.getRotYZAxis()-90))), camZ+cXZ.getY(pivotCam.getRotXZAxis()), 0, 1, 0);
					break;
			}
		}	
		
		if(this.modeColor != colorMode){
			switch(colorMode){
				case MODE_COLOR:
					this.modeColor = ModeColor.MODE_COLOR;
					glDisable(GL_TEXTURE_2D);
					break;
				case MODE_TEXTURE:
					this.modeColor = ModeColor.MODE_TEXTURE;
					glEnable(GL_TEXTURE_2D);
					break;
			}
		}
	}
	
	public void drawImage(Image img, float x, float y, float rot){
		img.getTexture().bind();
		
		float u = 0f;
		float v = 0f;
		
		float u2 = 1f;
		float v2 = 1f;
		
		setMode(ModeDraw.MODE_2D, ModeColor.MODE_TEXTURE);
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		glPushMatrix();
		glTranslatef(x+img.getXLocal(), y+img.getYLocal(), 0);
		glRotatef(rot, 0, 0, 1);
		glTranslatef(-(x+img.getXLocal()), -(y+img.getYLocal()), 0);
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(u, v);
		glVertex2f(x-img.getXLocal(), y-img.getYLocal());

		glTexCoord2f(u, v2);
		glVertex2f(x-img.getXLocal(), y-img.getYLocal()+img.getHeight());

		glTexCoord2f(u2, v2);
		glVertex2f(x-img.getXLocal()+img.getWidth(), y-img.getYLocal()+img.getHeight());
		
		glTexCoord2f(u2, v);
		glVertex2f(x-img.getXLocal()+img.getWidth(), y-img.getYLocal());
		
		glEnd();
		
		glPopMatrix();
	}
	
	public void setModeColor(){
		
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
			glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
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
	
	public double getCursorRotXZAxisChange(){
		return cursorRotXZAxisChange;
	}
	
	public double getCursorRotYZAxisChange(){
		return cursorRotYZAxisChange;
	}
	
	public void setPivotCam(Pivot pivot){
		pivotCam = pivot;
	}
	
	public Pivot getPivotCam(){
		return pivotCam;
	}
	
	public void updateTimer(){
		timePassed = glfwGetTime()-timeLast;
		timeLast = glfwGetTime();
	}
	
	public double getTimePassed(){
		return timePassed;
	}
	
	public double getTime(){
		return glfwGetTime();
	}
}

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
	private double xCursorLast, yCursorLast;
	
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
	
	private Point cam;
	
	private static final float NEAR = 0.01f;
	public static final float FAR = 1000.0f;
	
	public static final float FOV = 45f;
	
	public final float WIDTH_TEXTURE = 1.0f;
	
	private boolean cursorLocked;
	
	private Pivot pivotCam;
	
	private double timeLast = 0;
	private double timePassed = 0;
	
	private Texture font;
	
	public static final int DEF_CHAR_WIDTH = 16;
	public static final int DEF_CHAR_HEIGHT = 38;
	
	private Point textDrawOrigin;
	private Point textDrawLoc;
	
	public Display(String title, int width, int height){
		this(title, width, height, false);
	}
	
	public Display(String title, int width, int height, boolean fullscreen){
		this.title = title;
		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		
		cam = new Point(0, 0, 0);
		
		textDrawOrigin = new Point(0, 0);
		textDrawLoc = new Point(0, 0);
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

		font = new Texture("res/font/sans_serif2.png");
		
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
		
		xCursorLast = xCursor;
		yCursorLast = yCursor;
		
		updateCursor();
		
		checkClose();
	}

	private void updateCursor(){
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
        DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		
		glfwGetCursorPos(window, x, y);
		
		xCursor = x.get();
		yCursor = y.get();
	}
	
	public static void updateEvents(){
		glfwWaitEvents();
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
	
	public void drawLine(Point p1, Point p2, VectorColor c1, VectorColor c2){
		setMode(ModeDraw.MODE_2D, ModeColor.MODE_COLOR);
		
		glBegin(GL_LINES);
		glColor4f(c1.getRed(), c1.getGreen(), c1.getBlue(), c1.getAlpha());
		glVertex2f(p1.getX(), p1.getY());
		glColor4f(c2.getRed(), c2.getGreen(), c2.getBlue(), c2.getAlpha());
		glVertex2f(p2.getX(), p2.getY());
		glEnd();
	}
	
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
					gluLookAt(cam.getX(), cam.getY(), cam.getZ(), cam.getX()+cXZ.getX(pivotCam.getRotXZAxis()), cam.getY()+(float)(sin(toRadians(pivotCam.getRotYZAxis()-90))), cam.getZ()+cXZ.getY(pivotCam.getRotXZAxis()), 0, 1, 0);
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
	
	public void setTextDrawOrigin(Point p){
		textDrawOrigin = p;
		textDrawLoc = new Point(p.getX(), p.getY());
	}
	
	public void drawText(String text, float boundWidth, float charWidth, float charHeight, VectorColor backColor){
		font.bind();
		
		final int FONT_CHAR_COLUMNS = 16;
		final int FONT_CHAR_ROWS = 6;
		
		char curr;
		
		for(int i = 0; i != text.length(); i++){
			curr = text.charAt(i);
			
			if(textDrawLoc.getX() > textDrawOrigin.getX()+boundWidth){
				textDrawLoc.setY(textDrawLoc.getY()+charHeight);
				textDrawLoc.setX(textDrawOrigin.getX());
			}
			
			if(backColor != null){
				//draw the background color
				setMode(ModeDraw.MODE_2D, ModeColor.MODE_COLOR);
				glColor4f(backColor.getRed(), backColor.getGreen(), backColor.getBlue(), backColor.getAlpha());
				
				glBegin(GL_QUADS);
				
				glVertex2f(textDrawLoc.getX(), textDrawLoc.getY());
				glVertex2f(textDrawLoc.getX(), textDrawLoc.getY()+charHeight);
				glVertex2f(textDrawLoc.getX()+charWidth, textDrawLoc.getY()+charHeight);
				glVertex2f(textDrawLoc.getX()+charWidth, textDrawLoc.getY());

				glEnd();
			}
			
			//draw the character
			setMode(ModeDraw.MODE_2D, ModeColor.MODE_TEXTURE);
			glColor4f(1.0f, 1.0f, 1.0f, 1f);
			
			glBegin(GL_QUADS);
			
			glTexCoord2f(getTextUCoord(curr), getTextVCoord(curr));
			glVertex2f(textDrawLoc.getX(), textDrawLoc.getY());

			glTexCoord2f(getTextUCoord(curr), getTextVCoord(curr)+1f/FONT_CHAR_ROWS);
			glVertex2f(textDrawLoc.getX(), textDrawLoc.getY()+charHeight);

			glTexCoord2f(getTextUCoord(curr)+1f/FONT_CHAR_COLUMNS, getTextVCoord(curr)+1f/FONT_CHAR_ROWS);
			glVertex2f(textDrawLoc.getX()+charWidth, textDrawLoc.getY()+charHeight);
			
			glTexCoord2f(getTextUCoord(curr)+1f/FONT_CHAR_COLUMNS, getTextVCoord(curr));
			glVertex2f(textDrawLoc.getX()+charWidth, textDrawLoc.getY());

			glEnd();

			textDrawLoc.setX(textDrawLoc.getX()+charWidth);
		}
		
		textDrawLoc.setY(textDrawLoc.getY()+charHeight);
		textDrawLoc.setX(textDrawOrigin.getX());
	}
	
	public void drawText3D(String text, Point p, float boundWidth, float charWidth, float charHeight, float rot){
		font.bind();
		
		setMode(ModeDraw.MODE_3D, ModeColor.MODE_TEXTURE);
		glColor4f(1.0f, 1.0f, 1.0f, 1f);
		
		final int FONT_CHAR_COLUMNS = 16;
		final int FONT_CHAR_ROWS = 6;
		
		float drawX = p.getX();
		float drawY = p.getY();
		float drawZ = p.getZ();
		
		Circle c= new Circle(0, 0, charWidth);
		
		char curr;
		
		for(int i = 0; i != text.length(); i++){
			curr = text.charAt(i);
			
			if(drawX > p.getX()+boundWidth){
				drawY -= charHeight;
				drawX = p.getX();
			}
			
			glBegin(GL_QUADS);
			
			glTexCoord2f(getTextUCoord(curr), getTextVCoord(curr));
			glVertex3f(drawX, drawY, drawZ);

			glTexCoord2f(getTextUCoord(curr), getTextVCoord(curr)+1f/FONT_CHAR_ROWS);
			glVertex3f(drawX, drawY-charHeight, drawZ);

			glTexCoord2f(getTextUCoord(curr)+1f/FONT_CHAR_COLUMNS, getTextVCoord(curr)+1f/FONT_CHAR_ROWS);
			glVertex3f(drawX+c.getX(rot), drawY-charHeight, drawZ+c.getY(rot));
			
			glTexCoord2f(getTextUCoord(curr)+1f/FONT_CHAR_COLUMNS, getTextVCoord(curr));
			glVertex3f(drawX+c.getX(rot), drawY, drawZ);

			glEnd();
			
			drawX += charWidth;
		}
	}
	
	private float getTextUCoord(char c){
		final int FONT_CHAR_COLUMNS = 16;
		
		if(c == 'a' || c == 'q' || c == 'A' || c == 'Q' || c == '!' || c == '1' ){
			return 0f;
		}
		else if(c == 'b' || c == 'r' || c == 'B' || c == 'R' || c == '@' || c == '2'){
			return 1f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'c' ||  c == 's' || c == 'C' || c == 'S' || c == '#' || c == '3'){
			return 2f/FONT_CHAR_COLUMNS;
		}
		else if( c == 'd' || c == 't' ||  c == 'D' || c == 'T' || c == '$' || c == '4'){
			return 3f/FONT_CHAR_COLUMNS;
		}
		else if( c == 'e' ||  c == 'u' ||  c == 'E' || c == 'U' || c == '%' || c == '5'){
			return 4f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'f' || c == 'v' || c == 'F' || c == 'V' || c == '^' || c == '6'){
			return 5f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'g' || c == 'w' ||  c == 'G' ||  c == 'W' || c == '&' ||  c == '7'){
			return 6f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'h' || c == 'x' ||  c == 'H' ||  c == 'X' || c == '*' ||  c == '8' ){
			return 7f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'i' || c == 'y' || c == 'I' || c == 'Y' || c == '(' || c == '9' ){
			return 8f/FONT_CHAR_COLUMNS;
		}
		else if(  c == 'j' || c == 'z' || c == 'J' || c == 'Z' || c == ')' || c == '0' ){
			return 9f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'k' || c == '{' || c == 'K' || c == ';' || c == '-' || c == ',' ){
			return 10f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'l' || c == '}' || c == 'L' || c == ':' || c == '_' || c == '.' ){
			return 11f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'm' || c == '<' || c == 'M' || c == '\'' || c == '+' || c == '\\' ){
			return 12f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'n' || c == '>' || c == 'N' || c == '\"' || c == '=' || c == '|' ){
			return 13f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'o' || c == '[' || c == 'O' || c == '/' || c == ';' ||  c == '~' ){
			return 14f/FONT_CHAR_COLUMNS;
		}
		else if(c == 'p' || c == ']' || c == 'P' || c == '?' || c == ';' || c == ' '){
			return 15f/FONT_CHAR_COLUMNS;
		}
		else {
			return 15f/FONT_CHAR_COLUMNS;
		}
	}
	
	private float getTextVCoord(char c){
		final int FONT_CHAR_ROWS = 6;
		
		if(c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j' || c == 'k' || c == 'l' || c == 'm' || c == 'n' || c == 'o' || c == 'p'){
			return 0f;
		}
		else if(c == 'q' || c == 'r' || c == 's' || c == 't' || c == 'u' || c == 'v' || c == 'w' || c == 'x' || c == 'y' || c == 'z' || c == '{' || c == '}' || c == '<' || c == '>' || c == '[' || c == ']'){
			return 1f/FONT_CHAR_ROWS;
		}
		else if(c == 'A' || c == 'B' || c == 'C' || c == 'D' || c == 'E' || c == 'F' || c == 'G' || c == 'H' || c == 'I' || c == 'J' || c == 'K' || c == 'L' || c == 'M' || c == 'N' || c == 'O' || c == 'P'){
			return 2f/FONT_CHAR_ROWS;
		}
		else if(c == 'Q' || c == 'R' || c == 'S' || c == 'T' || c == 'U' || c == 'V' || c == 'W' || c == 'X' || c == 'Y' || c == 'Z' || c == ';' || c == ':' || c == '\'' || c == '\"' || c == '/' || c == '?'){
			return 3f/FONT_CHAR_ROWS;
		}
		else if(c == '!' || c == '@' || c == '#' || c == '$' || c == '%' || c == '^' || c == '&' || c == '*' || c == '(' || c == ')' || c == '-' || c == '_' || c == '+' || c == '=' || c == ';' || c == ';'){
			return 4f/FONT_CHAR_ROWS;
		}
		else if(c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '0' || c == ',' || c == '.' || c == '\\' || c == '|' || c == '~' || c == ' '){
			return 5f/FONT_CHAR_ROWS;
		}
		else {
			return 5f/FONT_CHAR_ROWS;
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
	
	public int getAspectRatio(){
		return aspectRatio;
	}
	
	public long getID(){
		return window;
	}
	
	public void setCamLocation(Point cam){	
		this.cam = cam;
	}
	
	public Point getCamLocation(){
		return cam;
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
	
	public void setCamPivot(Pivot pivot){
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
	
	public double getXCursor(){
		return xCursor;
	}
	
	public double getYCursor(){
		return yCursor;
	}
	
	public double getXCursorLast(){
		return xCursorLast;
	}
	
	public double getYCursorLast(){
		return yCursorLast;
	}
	
	public Texture getTexFont(){
		return font;
	}
}

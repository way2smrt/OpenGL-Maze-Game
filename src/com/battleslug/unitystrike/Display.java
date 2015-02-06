package com.battleslug.unitystrike;

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
	
	private String name;
	private int width, height;
	
	public Display(String _name, int _w, int _h){
		name = _name;
		width = _w;
		height = _h;
	}
	
    public void create() {
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        glfwDefaultWindowHints(); 
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); 
 
        window = glfwCreateWindow(width, height, name, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE);
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
        
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
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
    
    public void kill(){
    	glfwDestroyWindow(window);
        keyCallback.release();
        glfwTerminate();
        errorCallback.release();
        System.exit(0);;
    }
    
    public long getWindowID(){
    	return window;
    }
}

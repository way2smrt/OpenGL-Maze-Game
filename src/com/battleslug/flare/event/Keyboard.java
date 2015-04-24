package com.battleslug.flare.event;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.battleslug.porcupine.Display;

public class Keyboard {
	//any key higher than this value would be very odd
	public final int GLFW_KEYS = 348;
	
	public boolean[] keyDown;
	public boolean[] keyDownLast;
	
	public static final boolean KEY_DOWN = true;
	
	public Keyboard(){
		keyDown = new boolean[GLFW_KEYS+1];
		keyDownLast = new boolean[GLFW_KEYS+1];
	}
	
	public void bind(Display display){
		display.setKeyCallback(new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods){
            	if(action == GLFW_PRESS){
            		invokeState(key, true);
            	}
            	else if(action == GLFW_RELEASE){
            		invokeState(key, false);
            	}
            }
        });
	}
	
	private void invokeState(int key, boolean isPushed){
		if(isPushed == true){
			keyDown[key] = true;
		}
		else if(isPushed == false){
			keyDown[key] = false;
		}
	}
	
	public boolean isDown(int key){	
		if(keyDownLast[key] == true && keyDown[key] == true){
			return true;
		}
		return false;
	}
	
	public boolean wasPressed(int key){	
		if(keyDownLast[key] == false && keyDown[key] == true){
			return true;
		}
		return false;
	}
	
	public boolean wasReleased(int key){	
		if(keyDownLast[key] == true && keyDown[key] == false){
			return true;
		}
		return false;
	}
	
	public void update(){
		//get the key events by polling them
		glfwPollEvents();
		
		for(int i = 0; i != GLFW_KEYS+1; i++){
			keyDownLast[i] = keyDown[i];
		}
	}
}

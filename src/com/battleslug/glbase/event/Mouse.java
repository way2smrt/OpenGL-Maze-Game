package com.battleslug.glbase.event;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWMouseButtonCallback;



import com.battleslug.glbase.Display;

public class Mouse {
	private boolean leftButton, rightButton = false;
	private boolean leftButtonLast, rightButtonLast = false;
	
	public void bind(Display display){
		display.setMouseButtonCallback(new GLFWMouseButtonCallback(){
            @Override
            public void invoke(long window, int button, int action, int mods){
            	if(action == GLFW_PRESS){
            		if(button == GLFW_MOUSE_BUTTON_LEFT){
            			leftButton = true;
            		}
            		else if(button == GLFW_MOUSE_BUTTON_RIGHT){
            			rightButton = true;
            		}
            	}
            	else if(action == GLFW_RELEASE){
            		if(button == GLFW_MOUSE_BUTTON_LEFT){
            			leftButton = false;
            		}
            		else if(button == GLFW_MOUSE_BUTTON_RIGHT){
            			rightButton = false;
            		}
            	}
            }
        });
	}
	
	public boolean isDownLeftButton(){
		if(leftButtonLast && leftButton){
			return true;
		}
		return false;
	}
	
	public boolean isDownRightButton(){
		if(rightButtonLast && rightButton){
			return true;
		}
		return false;
	}
	
	public boolean wasPressedLeftButton(){
		if(!leftButtonLast && leftButton){
			return true;
		}
		return false;
	}
	
	public boolean wasPressedRightButton(){
		if(!rightButtonLast && rightButton){
			return true;
		}
		return false;
	}
	
	public boolean wasReleasedLeftButton(){
		if(leftButtonLast && !leftButton){
			return true;
		}
		return false;
	}
	
	public boolean wasReleasedRightButton(){
		if(rightButtonLast && !rightButton){
			return true;
		}
		return false;
	}
	
	public void update(){
		leftButtonLast = leftButton;
		rightButtonLast = rightButton;
	}
}

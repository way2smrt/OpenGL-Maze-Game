package com.battleslug.unitystrike;

import static org.lwjgl.glfw.GLFW.*;

public class KeyActionBinder {
	private int[] keys;
	private String[] actions;
	private long window;
	
	public KeyActionBinder(long _window){
		actions = new String[0];
		keys = new int[0];
		window = _window;
	}
	
	public void bind(String action, int key){
		//check if we have any empty keys in our array, if so add the bind
		for (int i = 0; i < actions.length; i++){
			if (actions[i] == null){
				actions[i] = action;
				keys[i] = key;
				return;
			}
		}
		
		//if not increase the size of the array and add the bind
		actions = new String[actions.length + 1];
		keys = new int[keys.length + 1];
		actions[actions.length - 1] = action;
		keys[actions.length - 1] = key;
	}
	
	public boolean getActionInput(String action){
		for (int i = 0; i < actions.length; i++){
			if (actions[i] == action){
				if (glfwGetKey(window, keys[i]) == GLFW_RELEASE){
					return true;
				}
			}
		}
		return false;
	}
	
	public void removeBind(String action){
		//remove a binded action
		for (int i = 0; i < actions.length; i++){
			if (actions[i] == action){
				actions[i] = null;
				keys[i] = 0;
			}
		}
	}
	
	public void removeBind(int key){
		//remove a binded key
		for (int i = 0; i < actions.length; i++){
			if (keys[i] == key){
				actions[i] = null;
				keys[i] = 0;
			}
		}
	}
	
	public boolean isKeyBinded(int key){
		for (int i = 0; i < actions.length; i++){
			if (keys[i] == key){
				return true;
			}
		}
		return false;
	}
}

package core;

import org.lwjgl.input.Keyboard;

public class KeyActionBinder {
	private int[] keys;
	private String[] actions;
	
	public KeyActionBinder(){
		actions = new String[0];
		keys = new int[0];
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
	
	/**
	 * Return true if the key binded to the action is currently being pressed. 
	 * @param action
	 */
	public boolean getActionInput(String action){
		for (int i = 0; i < actions.length; i++){
			if (actions[i] == action){
				return Keyboard.isKeyDown(keys[i]);
			}
		}
		return false;
	}
	
	public void removeBind(String action){
		for (int i = 0; i < actions.length; i++){
			if (actions[i] == action){
				actions[i] = null;
				keys[i] = 0;
			}
		}
	}
	
	public void removeBind(int key){
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

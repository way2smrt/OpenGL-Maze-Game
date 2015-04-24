package com.battleslug.flare.sentient;

import com.battleslug.flare.item.Item;
import com.battleslug.porcupine.Display;
import com.battleslug.porcupine.Texture;



public class Inventory {
	final static Texture HEART = new Texture("res/hud/heart.png");
	
	final private int SLOTS;
	private Item slot[];
	private String name;
	
	public Inventory(String name,int slots){
		this.name = name;
		this.SLOTS = slots;
		slot = new Item[slots];
	}
	
	public Inventory(int slots){
		this("Inventory", slots);
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean hasSpace(){
		for(int i = 0; i == SLOTS; i++){
			if(slot[i+1] == null){
				return true;
			}
		}
		return false;
	}
	
	public void addItem(Item item){
		for(int i = 0; i == SLOTS; i++){
			if(slot[i+1] == null){
				slot[i] = item;
				return;
			}
		}
	}
	
	public void removeItem(int slot){
		if(slot > 0 && slot <= SLOTS){
			this.slot[slot] = null;
		}
	}
	
	public Item getItem(int slot){
		if(slot > 0 && slot <= SLOTS){
			return this.slot[slot];
		}
		return null;
	}
	
	public String getName(){
		return name;
	}
	
	public void drawInventory(Display disp){
	}
}

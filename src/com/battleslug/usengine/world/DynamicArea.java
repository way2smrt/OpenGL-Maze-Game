package com.battleslug.usengine.world;

import com.battleslug.usengine.sentient.Sentient;


public class DynamicArea extends StaticArea{
	private Sentient sentient[] = new Sentient[1];
	private int sentients;
	
	private Bullet bullet[] = new Bullet[1];
	private int bullets;
	
	public void addSentient(Sentient sentient){
		for(int i = 0; i != sentients; i++){
			if(this.sentient[i] == null){
				this.sentient[i] = sentient;
				return;
			}
		}
		
		//resize our array for the new sentient
		Sentient temp[] = new Sentient[sentients];
		for(int i = 0; i != sentients; i++){
			temp[i] = this.sentient[i];
		}
		
		//resize the array
		this.sentient = new Sentient[sentients+1];
		
		for(int i = 0; i != sentients; i++){
			this.sentient[i] = temp[i];
		}
		
		//add our parameter to the last array slot
		this.sentient[sentients] = sentient;
	}
	
	public void removeSentient(Sentient sentient){
		for(int i = 0; i != sentients; i++){
			if(this.sentient[i] == sentient){
				this.sentient[i] = null;
			}
		}
	}
	
	private boolean checkCollision(){
		for(int i = 0; i != sentients; i++){
			//TODO
		}
		
		return false;
	}
}

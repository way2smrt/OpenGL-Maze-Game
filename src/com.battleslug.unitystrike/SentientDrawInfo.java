package core;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.SlickException;

public class SentientDrawInfo {
	private String name;
	
	private SpriteSheet spritesheet;
	
	private String[] action;
	private int[] actionFrames;
	private int[] actionCurrFrame;
	
	public SentientDrawInfo(String _name){
		name = _name;
		loadAssets();
	}
	
	private void loadAssets(){
		action[0] = "WALKING";
		actionFrames[0] = 12;
		action[1] = "RUNNING";
		actionFrames[1] = 12;
		action[2] = "CROUCHING";
		actionFrames[2] = 12;
		action[3] = "JUMPING";
		actionFrames[3] = 1; 
		action[4] = "STANDING";
		actionFrames[4] = 1;
		action[5] = "IDLE_CROUCHING";
		actionFrames[5] = 1;
		
		try{
			spritesheet = new SpriteSheet("res/sen/"+name+".png", 32, 32, 0);
		}
		catch(SlickException e){
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public Texture getTexture(String _action){
		for (int i = 0; i < action.length; i++){
			if (action[i] == _action){
				return spritesheet.getSubImage(i, actionCurrFrame[i] - 1).getTexture();
			}
		}
		return null;
	}
	
	public Texture getTexture(String _action, int frame){
		for (int i = 0; i < action.length; i++){
			if (action[i] == _action && actionFrames[i] <= frame){
				return spritesheet.getSubImage(i, actionCurrFrame[i] - 1).getTexture();
			}
		}
		return null;
	}
}

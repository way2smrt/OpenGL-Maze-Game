package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
import com.battleslug.flare.sentient.Sentient;
import com.battleslug.flare.item.*;
import com.battleslug.flare.item.Weapon.*;
import com.battleslug.flare.GUI.*;
import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;
import com.battleslug.glbase.geometry.Pivot.LimitMode;
import com.battleslug.glbase.Display;
import com.battleslug.glbase.Texture;
import com.battleslug.glbase.event.*;

import static java.lang.Math.*;

public class OPSF {	
	private final static float WORLD_FLOOR = 0.0f;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	private static final float CURSOR_SPEED = 0.4f;
	
	private Keyboard keyboard;
	private Mouse mouse;
	private World world;
	private Display display;
	
	private Sentient player;
	
	private Image imgDoge;
	
	private Texture texTest1, texGrass;
	
	private HUDBulletDisplay bulletDisplay;
	
	public OPSF(){
		init();
		play();
	}
	
	public void init(){
		if(glfwInit() != GL11.GL_TRUE){
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		keyboard = new Keyboard();
		mouse = new Mouse();
		
		display = new Display("Operation Solar Fury (Alpha 0.0.0)", 900, 720, false);
		display.create();
		
		display.setCursorLocked(true);
	}
	
	public void play(){	
		world = new World();
		world.bind(display);
		
		player = new Sentient(world, "Bob the test dummy", 125);
		player.setPivot(new Pivot(0, 90, 0));
		player.getPivot().setRotYZAxisLimits(0f, 180f);
		player.getPivot().setYZAxisLimitMode(LimitMode.STOP);
		
		//add weapons
		world.getWeaponSystem().add(new Weapon("A23", "A light smg, but with enough rounds and punch for good versatility.", Weapon.Type.SMG, Weapon.FireMode.AUTO, 0.1f, 12, Weapon.ReloadMode.CLIP, 2.5f, Weapon.AmmoType.PISTOL, 23));
		world.getWeaponSystem().add(new Weapon("UMP-48", "A powerful smg. Doensn't carry a lot of rounds though.", Weapon.Type.SMG, Weapon.FireMode.AUTO, 0.14f, 24, Weapon.ReloadMode.CLIP, 2.5f, Weapon.AmmoType.PISTOL, 16));
		world.getWeaponSystem().add(new Weapon("HPP-7", "A powerful pistol capable of shooting deadly rounds.", Weapon.Type.PISTOL, Weapon.FireMode.SEMIAUTO, 0.3f, 27, Weapon.ReloadMode.CLIP, 2f, Weapon.AmmoType.PISTOL, 8));
		world.getWeaponSystem().add(new Weapon("JAYKO", "A long range sniper rifle.", Weapon.Type.SNIPER_RIFLE, Weapon.FireMode.SEMIAUTO, 1.2f, 92, Weapon.ReloadMode.CLIP, 3f, Weapon.AmmoType.RIFLE, 6));
		world.getWeaponSystem().add(new Weapon("KT-277", "A powerful assault rifle with a decent mag.", Weapon.Type.ASSAULT_RIFLE, Weapon.FireMode.SEMIAUTO, 0.17f, 22, Weapon.ReloadMode.CLIP, 3f, Weapon.AmmoType.RIFLE, 22));
		world.getWeaponSystem().add(new Weapon("Rattlesnake", "A high-capacity pistol, with a very fast fire rate.", Weapon.Type.PISTOL, Weapon.FireMode.SEMIAUTO, 0.06f, 7, Weapon.ReloadMode.CLIP, 1f, Weapon.AmmoType.PISTOL, 20));
		world.getWeaponSystem().add(new Weapon("PNP12", "A powerful shotgun with a slow fire rate.", Weapon.Type.SHOTGUN, Weapon.FireMode.SEMIAUTO, 0.5f, 52, Weapon.ReloadMode.CHAMBER_FULL, 1.2f, Weapon.AmmoType.SHOTGUN, 6));
		world.getWeaponSystem().add(new Weapon("x11", "A reliable rifle, very versatile.", Weapon.Type.RIFLE, Weapon.FireMode.SEMIAUTO, 0.7f, 62, Weapon.ReloadMode.CLIP, 3f, Weapon.AmmoType.RIFLE, 10));
		
		player.setSpeed(10f, 1f, 3f);
		player.setWeaponInstance(new WeaponInstance(world, player.getObjectWorldData(), world.getWeaponSystem().getWeapon(0)));
		
		display.setCamPivot(player.getPivot());
		
		keyboard.bind(display);
		mouse.bind(display);
		
		texTest1 = new Texture(GAME_FOLDER+"/res/tex/brick1.png", Texture.NEAREST, Texture.REPEAT);
		texGrass = new Texture(GAME_FOLDER+"/res/tex/grass3.png", Texture.NEAREST, Texture.REPEAT);
		imgDoge = new Image(new Texture(GAME_FOLDER+"/res/img/misc/doge.png"));
		imgDoge.setDimensions(50, 50);
		
		Texture tex1 = new Texture(GAME_FOLDER+"/res/tex/sand1.png");
		Texture tex2 = new Texture(GAME_FOLDER+"/res/tex/rock1.png");
		Texture tex3 = new Texture(GAME_FOLDER+"/res/tex/grassFlowers1.png");
		Texture tex4 = new Texture(GAME_FOLDER+"/res/tex/bark1.png");
		Texture tex5 = new Texture(GAME_FOLDER+"/res/tex/stoneRoad1.png");
		Texture tex6 = new Texture(GAME_FOLDER+"/res/tex/sandRocks2.png");
		
		int weapon = 0;
		
		float crossHairDist = 5;
		final int CROSSHAIR_DIST_MAX = 25;
			
		bulletDisplay = new HUDBulletDisplay(new Point(display.getWidth()-display.getWidth()/2, display.getHeight()-display.getHeight()/4), display.getWidth()/2, display.getHeight()/4, player.getWeaponInstance(), player.getWeaponInstance().getWeapon().getAmmoTex(), new VectorColor(0.8f, 0.8f, 0.5f));
		bulletDisplay.bind(display);
		
		float cubeX = -40f;;
		
		VectorColor HUDBackColor = new VectorColor(1.0f, 0.0f, 0.0f, 0.75f);
		
		while(true){
			keyboard.update();
			mouse.update();
			
			Display.updateEvents();
			
			world.update(display.getTimePassed());
			
			bulletDisplay.draw();
			
			if(keyboard.wasPressed(GLFW_KEY_Q)){
				if(weapon > 0){
					weapon -= 1;
					changeWeapon(weapon);
				}
				else {
					weapon = 0;
				}
			}
			else if(keyboard.wasPressed(GLFW_KEY_E)){
				if(weapon < world.getWeaponSystem().getNumWeapons()-1){
					weapon += 1;
					changeWeapon(weapon);
				}
				else {
					weapon = world.getWeaponSystem().getNumWeapons()-1;
				}
			}
			
			//draw cursor and shoot gun
			if(player.getWeaponInstance().getMode() == WeaponInstance.Mode.Reload){
				player.getWeaponInstance().updateReload(display.getTime());
			}
			
			if(player.getWeaponInstance().getWeapon().getFireMode() == Weapon.FireMode.SEMIAUTO && mouse.wasPressedLeftButton() && player.getWeaponInstance().canShoot(display.getTime())){
				if(player.getWeaponInstance().hasBullets()){
					crossHairDist = CROSSHAIR_DIST_MAX;
				}
				
				player.getWeaponInstance().shoot(0, 0, 0, 0, display.getTime());
			}
			else if(player.getWeaponInstance().getWeapon().getFireMode() == Weapon.FireMode.AUTO && mouse.isDownLeftButton() && player.getWeaponInstance().canShoot(display.getTime())){
				if(player.getWeaponInstance().hasBullets()){
					crossHairDist = CROSSHAIR_DIST_MAX;
				}
				
				player.getWeaponInstance().shoot(0, 0, 0, 0, display.getTime());
			}
			else {
				if(crossHairDist > 0){
					crossHairDist -= (float)((CROSSHAIR_DIST_MAX/player.getWeaponInstance().getWeapon().getFireDelay())*display.getTimePassed());
				}
				if(crossHairDist < 0){
					crossHairDist = 0;
				}
			}
			
			Point pO = new Point(0, 0, 0);
			
			VectorColor colorBlue = new VectorColor(0f, 0f, 1f);
			VectorColor colorWhite = new VectorColor(1f, 1f, 1f);
			VectorColor colorBlack = new VectorColor(0f, 0f, 0f);
			VectorColor colorGreen = new VectorColor(0f, 1f, 0f);
			VectorColor colorRedDark = new VectorColor(1f, 0f, 0.5f);
			
			//draw the crosshair
			drawCrosshair(9, (int)(crossHairDist), new VectorColor(0f, 0f, 0f), colorRedDark);
			drawCrosshair(7, 5, colorRedDark, new VectorColor(0f, 0f, 0f));
			
			display.drawCube(3, 0.5f, 5, 1, tex1);
			display.drawCube(3, 1.5f, 5, 1, tex2);
			display.drawCube(3, 2.5f, 5, 1, tex3);
			display.drawCube(-7, 0.5f, 3, 1, tex2);
			display.drawCube(3, 0.5f, -1, 1, tex3);
			display.drawCube(-3, 0.5f, -4, 1, tex4);
			display.drawCube(0, 0.5f, 0, 1, imgDoge.getTexture());
			
			display.drawCube(cubeX, 5, 0, 5, tex5);
			cubeX += (float)(2f*display.getTimePassed());
			
			drawFloor(tex1);
			
			display.drawLine3D(pO, new Point(0, 5, 0), colorBlue, colorWhite);
			display.drawLine3D(pO, new Point(cubeX, 5f, 0), colorGreen, colorGreen);
			
			world.draw();
			
			display.setTextDrawOrigin(new Point(200, 200));
			display.drawText("Do you see this text rendering??? :D  10/10", 16*32, 16, 38, null);
			
			//draw some hud stuff
			display.setTextDrawOrigin(new Point(0, 0));
			display.drawText("Weapon: "+player.getWeaponInstance().getWeapon().getName(), display.getWidth()/3, Display.DEF_CHAR_WIDTH, Display.DEF_CHAR_HEIGHT, HUDBackColor);
			display.drawText("Weapon type: "+Weapon.getTypeString(player.getWeaponInstance().getWeapon().getType()), display.getWidth()/3, Display.DEF_CHAR_WIDTH, Display.DEF_CHAR_HEIGHT, HUDBackColor);
			display.drawText(new Integer(player.getWeaponInstance().getBullets()).toString()+"/"+new Integer(player.getWeaponInstance().getWeapon().getAmmoMax()).toString(), display.getWidth()/3, Display.DEF_CHAR_WIDTH, Display.DEF_CHAR_HEIGHT, HUDBackColor);
			
			if(keyboard.isDown(GLFW_KEY_Z)){										
				imgDoge.setLocal(imgDoge.getWidth()/2, imgDoge.getHeight()/2);
				
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, imgDoge.getTexture(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 400, display.getTexFont(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, imgDoge.getTexture(), null));
			}

			player.updateUserControlled(keyboard, mouse, display.getTimePassed(), display.getTime());
				
			display.setCamPivot(player.getPivot());
			display.setCamLocation(player.getCamLocation());
			
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			display.update();
			display.clear();
			display.updateTimer();
		}
	}
	
	private void changeWeapon(int ID){
		player.setWeaponInstance(new WeaponInstance(world, player.getObjectWorldData(), world.getWeaponSystem().getWeapon(ID)));
		
		bulletDisplay = new HUDBulletDisplay(new Point(display.getWidth()-display.getWidth()/2, display.getHeight()-display.getHeight()/4), display.getWidth()/2, display.getHeight()/4, player.getWeaponInstance(), player.getWeaponInstance().getWeapon().getAmmoTex(), new VectorColor(0.8f, 0.8f, 0.8f));
		bulletDisplay.bind(display);
	}
	
	private void drawCrosshair(int size, int spacing, VectorColor cInner, VectorColor cOuter){
		display.drawLine(new Point((display.getWidth()/2)-size-spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)-spacing, (display.getHeight()/2)), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)-size-spacing), new Point((display.getWidth()/2), (display.getHeight()/2)-spacing), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2)+spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)+size+spacing, (display.getHeight()/2)), cInner, cOuter);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)+spacing), new Point((display.getWidth()/2), (display.getHeight()/2)+size+spacing), cInner, cOuter);
	}
	
	private void drawFloor(Texture texFloor){
		display.drawQuadTextured3D(new QuadTextured3D(100, WORLD_FLOOR, 100, 100, WORLD_FLOOR, -100, -100, WORLD_FLOOR, -100, -100, WORLD_FLOOR, 100, texFloor, null));
	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

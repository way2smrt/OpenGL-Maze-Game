package com.battleslug.opsf;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.GL11;

import com.battleslug.flare.*;
import com.battleslug.flare.world.*;
import com.battleslug.flare.sentient.Sentient;
import com.battleslug.flare.item.*;
import com.battleslug.flare.item.Weapon.AmmoType;
import com.battleslug.flare.item.Weapon.FireMode;
import com.battleslug.flare.item.Weapon.ReloadMode;
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
	private final static float WORLD_GRAVITY = 9.81f;
	
	private static final String GAME_FOLDER = "game/OPSF";
	
	private static final float CURSOR_SPEED = 0.4f;
	
	private Keyboard keyboard;
	private Mouse mouse;
	private World world;
	private Display display;
	
	private Sentient player;
	
	private Image imgDoge;
	
	private Texture texTest1, texGrass;
	
	private Texture hud_bullet_normal, hud_bullet_rifle, hud_bullet_shotgun;
	
	public OPSF(){
		init();
		play();
	}
	
	public void init(){
		if (glfwInit() != GL11.GL_TRUE){
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		keyboard = new Keyboard();
		mouse = new Mouse();
		
		display = new Display("Operation Solar Fury (Alpha 0.0.0)", 1280, 1024, false);
		display.create();
		
		display.setCursorLocked(true);
	}
	
	public void play(){
		hud_bullet_normal = new Texture("res/img/hud/bullet_normal.png");
		hud_bullet_rifle = new Texture("res/img/hud/bullet_rifle.png");
		hud_bullet_shotgun = new Texture("res/img/hud/bullet_shotgun.png");
		
		player = new Sentient(world, "Bob the test dummy", 125);
		player.setPivot(new Pivot(0, 90, 0));
		player.getPivot().setRotYZAxisLimits(0f, 180f);
		player.getPivot().setYZAxisLimitMode(LimitMode.STOP);
		
		player.setWeaponInstance(new WeaponInstance(world, player.getObjectWorldData(), new Weapon("pew pew gun", "shoot bullets", Weapon.FireMode.Automatic, 0.05f, 8, Weapon.ReloadMode.Clip, 2f, Weapon.AmmoType.SMG, 64)));
		
		display.setPivotCam(player.getPivot());
		
		world = new World();
		world.bind(display);
		
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
		
		float crossHairDist = 5;
		final int CROSSHAIR_DIST_MAX = 30;
		
		HUDBulletDisplay bulletDisplay = new HUDBulletDisplay(display.getWidth()-display.getWidth()/2, display.getHeight()-display.getHeight()/4, display.getWidth()/2, display.getHeight()/4, player.getWeaponInstance(), hud_bullet_rifle);
		bulletDisplay.bind(display);
		
		float cubeX = -40f;;
		
		while(true){
			keyboard.update();
			mouse.update();
			
			Display.updateEvents();
			
			world.update(display.getTimePassed());
			
			bulletDisplay.draw();
			
			//draw cursor and shoot gun
			if(player.getWeaponInstance().getMode() == WeaponInstance.Mode.Reload){
				player.getWeaponInstance().updateReload(display.getTime());
			}
			
			if(player.getWeaponInstance().getWeapon().getFireMode() == Weapon.FireMode.Semiautomatic && mouse.wasPressedLeftButton() && player.getWeaponInstance().canShoot(display.getTime())){
				crossHairDist = 0;
				
				player.getWeaponInstance().shoot(0, 0, 0, 0, display.getTime());
			}
			else if(player.getWeaponInstance().getWeapon().getFireMode() == Weapon.FireMode.Automatic && mouse.isDownLeftButton() && player.getWeaponInstance().canShoot(display.getTime())){
				crossHairDist = 0;
				
				player.getWeaponInstance().shoot(0, 0, 0, 0, display.getTime());
			}
			else {
				if(crossHairDist < CROSSHAIR_DIST_MAX){
					crossHairDist += (float)(75*display.getTimePassed());
				}
				if(crossHairDist > CROSSHAIR_DIST_MAX){
					crossHairDist = CROSSHAIR_DIST_MAX;
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
			
			if(keyboard.isDown(GLFW_KEY_Z)){										
				imgDoge.setLocal(imgDoge.getWidth()/2, imgDoge.getHeight()/2);
				display.drawQuadTextured2D(new QuadTextured2D(0, 0, 200, 200, imgDoge.getTexture(), null));
				display.drawQuadTextured2D(new QuadTextured2D(200, 200, 400, 0, imgDoge.getTexture(), null));
			}

			player.updateUserControlled(keyboard, mouse, display.getTimePassed());
			
			if(keyboard.wasPressed(GLFW_KEY_SPACE) && player.getObjectWorldData().getPoint().getY() == 0f){
				player.getObjectWorldData().getSpeed().setYSpeed(0.5f);
			}
			if(keyboard.wasPressed(GLFW_KEY_R)){
				if(player.getWeaponInstance().getMode() == WeaponInstance.Mode.Ready){
					player.getWeaponInstance().initReload(display.getTime());
				}
			}
				
			display.setPivotCam(player.getPivot());
			display.setCamLocation(player.getCamLocation());
			
			//invoke gravity on player
			player.getObjectWorldData().getPoint().setY(player.getObjectWorldData().getSpeed().getYSpeed()-(float)(WORLD_GRAVITY*pow(display.getTimePassed(), 2)));
			
			//update player location
			player.getObjectWorldData().getPoint().setY(player.getObjectWorldData().getPoint().getY()+player.getObjectWorldData().getSpeed().getYSpeed());			
			if(player.getObjectWorldData().getPoint().getY() < WORLD_FLOOR){
				player.getObjectWorldData().getPoint().setY(WORLD_FLOOR);
				player.getObjectWorldData().getSpeed().setYSpeed(0f);
			}
			
			if(keyboard.isDown(GLFW_KEY_ESCAPE)){
				display.kill();
			}
			
			display.update();
			display.clear();
			display.updateTimer();			
		}
	}
	
	private void drawCrosshair(int size, int spacing, VectorColor cInner, VectorColor cOuter){
		display.drawLine(new Point((display.getWidth()/2)-size-spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)-spacing, (display.getHeight()/2)), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)-size-spacing), new Point((display.getWidth()/2), (display.getHeight()/2)-spacing), cOuter, cInner);
		display.drawLine(new Point((display.getWidth()/2)+spacing, (display.getHeight()/2)), new Point((display.getWidth()/2)+size+spacing, (display.getHeight()/2)), cInner, cOuter);
		display.drawLine(new Point((display.getWidth()/2), (display.getHeight()/2)+spacing), new Point((display.getWidth()/2), (display.getHeight()/2)+size+spacing), cInner, cOuter);
	}
	
	private void drawFloor(Texture texFloor){
		display.drawQuadTextured3D(new QuadTextured3D(display.FAR, WORLD_FLOOR, display.FAR, display.FAR, WORLD_FLOOR, -display.FAR, -display.FAR, WORLD_FLOOR, -display.FAR, -display.FAR, WORLD_FLOOR, display.FAR, texFloor, null));
	}
	
	public static void main(String[] args) {
        new OPSF();
    }
}

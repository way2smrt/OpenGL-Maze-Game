package com.battleslug.flare.sentient;

import com.battleslug.flare.world.*;
import com.battleslug.flare.item.*;

public class Player extends Sentient {	
	public Player(World world, String name, int maxHealth){
		super(world, name, maxHealth);
		
		Weapon gunPewPew = new Weapon("ayy lmao gun", "shoots stuff", Weapon.FireMode.Automatic, 0.12f, 20, Weapon.ReloadMode.Clip, 0.5f, Weapon.AmmoType.Rifle, 35);
		
		weaponInstance = new WeaponInstance(world, this.getObjectWorldData(), gunPewPew);
	}
}

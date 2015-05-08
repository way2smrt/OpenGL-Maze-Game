package com.battleslug.flare;

import com.battleslug.flare.sentient.*;
import com.battleslug.flare.item.*;

public class Player extends Sentient {	
	public Player(String name, int maxHealth){
		super(name, maxHealth);
		
		weapon = new Weapon("ayy lmao gun", "shoots stuff", Weapon.FireMode.Automatic, 20, 35);
	}
}

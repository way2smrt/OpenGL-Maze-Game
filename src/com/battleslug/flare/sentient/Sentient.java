package com.battleslug.flare.sentient;

import com.battleslug.flare.world.*;

public interface Sentient {
	void think(double timePassed);
	
	ObjectWorldData getCamera();
}

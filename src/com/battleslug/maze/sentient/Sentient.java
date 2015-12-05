package com.battleslug.maze.sentient;

import com.battleslug.maze.world.*;

public interface Sentient {
	void think(double timePassed);
	
	ObjectWorldData getCamera();
}

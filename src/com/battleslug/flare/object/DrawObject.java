package com.battleslug.flare.object;

import com.battleslug.glbase.*;

public interface DrawObject {
	void draw(Display display);
	
	boolean shouldBeDrawn();
}

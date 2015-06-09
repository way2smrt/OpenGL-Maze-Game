package com.battleslug.flare.object;

import com.battleslug.glbase.*;
import com.battleslug.glbase.geometry.*;

public interface DrawObject {
	void draw(Display display);
	
	boolean shouldBeDrawn();
}

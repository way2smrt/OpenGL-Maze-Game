package com.battleslug.usengine.event;

public class Timer {
	public long lastUpdate;
	
	public static final int NANOSECOND = 1000000000;
	
	public Timer(){
		lastUpdate = System.nanoTime();
	}

	public void waitMillis(long waitMillis){
		while(System.nanoTime() <= lastUpdate+waitMillis){
		}
	}
	
	/**
	 * Updates the timer, so getTimePassedNanos will return time since you called this.
	 * Should be called every frame/main update.
	 */
	public void update(){
		lastUpdate = System.nanoTime();
	}
	
	/**
	 * Returns the time passed since wait or update was called.
	 * Caution: Can return 0 at times.
	 * @return
	 */
	public long getTimePassedNanos(){
		return System.nanoTime() - lastUpdate;
	}
	
	public long getTimeNanos(){
		return System.nanoTime();
	}
	
	public long getFPS(){
		long timePassed = getTimePassedNanos();
		
		//need to be careful not to divide by zero, HIGHLY unlikely but just in case of chronological error.
		if(timePassed != 0){
			return (NANOSECOND/timePassed);
		}
		return 1337;
	}
}

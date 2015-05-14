package com.battleslug.flare.item;

public class WeaponInstance {
	private Weapon weapon;

	private int ammoCurr;

	private double fireTimeLast;
	
	private double reloadStartTime;
	
	public enum Mode{Ready, Reload};
	private Mode mode;
	
	public WeaponInstance(Weapon weapon){
		this.weapon = weapon;
		ammoCurr = weapon.getAmmoMax();
		
		mode = Mode.Ready;
	}
	
	public boolean hasBullets(){
		if(ammoCurr != 0){
			return true;
		}
		return false;
	}
	
	public boolean canShoot(double currTime){
		if(fireTimeLast+weapon.getFireDelay() <= currTime && mode == Mode.Ready){
			return true;
		}
		return false;
	}
	
	public boolean isClipFull(){
		if(ammoCurr == weapon.getAmmoMax()){
			return true;
		}
		return false;
	}
	
	public void shoot(int x, int y, int xSpeed, int ySpeed, double currTime){
		fireTimeLast = currTime;
		
		if(ammoCurr != 0){
			ammoCurr -= 1;	
		}
	}
	
	public int getBullets(){
		return ammoCurr;
	}
	
	public void initReload(double currTime){
		mode = Mode.Reload;
		
		reloadStartTime = currTime;
	}
	
	public void updateReload(double currTime){
		if(weapon.getReloadMode() == Weapon.ReloadMode.ChamberSingle){
			if(reloadStartTime+weapon.getReloadDelay() <= currTime){
				if(ammoCurr < weapon.getAmmoMax()){
					ammoCurr += 1;
				}
				mode = Mode.Ready;
			}
		}
		else if(weapon.getReloadMode() == Weapon.ReloadMode.ChamberFull){			
			int ammoReload = (int)((currTime-reloadStartTime)/weapon.getReloadDelay());
			
			if(ammoCurr+ammoReload < weapon.getAmmoMax()){
				ammoCurr += ammoReload;
			}
			else {
				ammoCurr = weapon.getAmmoMax();
				mode = Mode.Ready;
			}
		}
		else if(weapon.getReloadMode() == Weapon.ReloadMode.Clip){
			if(reloadStartTime+weapon.getReloadDelay() <= currTime){
				ammoCurr = weapon.getAmmoMax();
				mode = Mode.Ready;
			}
		}
	}
	
	public Weapon getWeapon(){
		return weapon;
	}
	
	public Mode getMode(){
		return mode;
	}
}
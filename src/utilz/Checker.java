package utilz;

import logic.base.ID;
import logic.base.Keys;

public class Checker {
	
	public static String keyDirection(Keys key) {
		if(key.LEFT) return "LEFT";
		if(key.RIGHT) return "RIGHT";
		if(key.UP) return "UP";
		if(key.DOWN) return "DOWN";
		if(key.A) return "L"; 
		if(key.D) return "R"; 
		if(key.W) return "U"; 
		if(key.S) return "D";
		return "Z";
	}
	public static String keyWalkDirection(Keys key) {
		if(key.A) return "L"; 
		if(key.D) return "R"; 
		if(key.W) return "U"; 
		if(key.S) return "D";
		return "Z";
	}
	
	public static String keyHitDirection(Keys key) {
		if(key.LEFT) return "LEFT";
		if(key.RIGHT) return "RIGHT";
		if(key.UP) return "UP";
		if(key.DOWN) return "DOWN";
		return "Z";
	}
	
	public static boolean unpressedWalkDirection(Keys key) {
		return !(key.W || key.A || key.S || key.D);
	}
	
	public static boolean unpressedHitDirection(Keys key) {
		return !(key.LEFT || key.RIGHT || key.UP || key.DOWN);
	}
	
	public static String getDirectionByVelo(double xVelo, double yVelo) {
		if(xVelo < 0) return "L";
		if(xVelo > 0) return "R";
		if(yVelo < 0) return "U";
		if(yVelo > 0) return "D";
		return "Z";
	}
	
	public static boolean isStableObject(ID id) {
		switch(id) {
			case Door1 : return true; 
			case Door2 : return true;
			case Key1 : return true;
			case Key2 : return true;
			default : return false;
		}
	}
	
	public static boolean isDoor(ID id) {
		switch(id) {
			case Door1 : return true; 
			case Door2 : return true;
			default : return false;
		}
	}
	
	public static boolean isKey(ID id) {
		switch(id) {
			case Key1 : return true; 
			case Key2 : return true;
			default : return false;
		}
	}
	
	public static boolean isKnife(ID id) {
		switch(id) {
			case Knife : return true; 
			default : return false;
		}
	}
	
	public static boolean isGun(ID id) {
		switch(id) {
			case PistolGun : return true; 
			default : return false;
		}
	}
	
	public static boolean isMagazine(ID id) {
		switch(id) {
			case PistolMagazine : return true; 
			default : return false;
		}
	}
	
	public static boolean isHpBottle(ID id) {
		switch(id) {
			case HpBottle : return true; 
			default : return false;
		}
	}
	
	public static boolean isComputer(ID id) {
		switch(id) {
			case Computer : return true; 
			default : return false;
		}
	}
	
	public static boolean inRange(double low, double high,double val) {
		return val >= low && val <= high;
	}
	
	public static int rand(int Min, int Max) {
		return (int)((Math.random()*(Max-Min+1)+Min));
	}
}

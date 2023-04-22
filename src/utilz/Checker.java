package utilz;

import logic.base.ID;
import logic.base.Keys;

public class Checker {
	
	public static String KeyDirection(Keys key) {
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
	public static String KeyWalkDirection(Keys key) {
		if(key.A) return "L"; 
		if(key.D) return "R"; 
		if(key.W) return "U"; 
		if(key.S) return "D";
		return "Z";
	}
	
	public static String KeyHitDirection(Keys key) {
		if(key.LEFT) return "LEFT";
		if(key.RIGHT) return "RIGHT";
		if(key.UP) return "UP";
		if(key.DOWN) return "DOWN";
		return "Z";
	}
	
	public static boolean UnpressedWalkDirection(Keys key) {
		return !(key.W || key.A || key.S || key.D);
	}
	
	public static boolean UnpressedHitDirection(Keys key) {
		return !(key.LEFT || key.RIGHT || key.UP || key.DOWN);
	}
	
	public static String GetDirectionByVelo(double xVelo, double yVelo) {
		if(xVelo < 0) return "L";
		if(xVelo > 0) return "R";
		if(yVelo < 0) return "U";
		if(yVelo > 0) return "D";
		return "Z";
	}
	
	public static boolean IsStableObject(ID id) {
		switch(id) {
			case Door1 : return true; 
			case Door2 : return true;
			case Door3 : return true;
			case Key1 : return true;
			case Key2 : return true;
			case Key3 : return true;
			default : return false;
		}
	}
	
	public static boolean IsDoor(ID id) {
		switch(id) {
			case Door1 : return true; 
			case Door2 : return true;
			case Door3 : return true;
			default : return false;
		}
	}
	
	public static boolean IsKey(ID id) {
		switch(id) {
			case Key1 : return true; 
			case Key2 : return true;
			case Key3 : return true;
			default : return false;
		}
	}
	
	public static boolean IsKnife(ID id) {
		switch(id) {
			case Knife : return true; 
			default : return false;
		}
	}
	
	public static boolean IsGun(ID id) {
		switch(id) {
			case Gun : return true; 
			default : return false;
		}
	}
	
	public static boolean IsMagazine(ID id) {
		switch(id) {
			case Magazine : return true; 
			default : return false;
		}
	}
	
	public static boolean IsHpBottle(ID id) {
		switch(id) {
			case HpBottle : return true; 
			default : return false;
		}
	}
	
	public static boolean IsComputer(ID id) {
		switch(id) {
			case Computer : return true; 
			default : return false;
		}
	}
	
	public static boolean InRange(double low, double high,double val) {
		return val >= low && val <= high;
	}
	
	public static int Rand(int Min, int Max) {
		return (int)((Math.random()*(Max-Min+1)+Min));
	}
}

package utilz;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.Map;
import logic.person.Criminal;
import logic.person.Player;
import logic.container.Bullet;
import logic.container.Knife;
import logic.container.Gun;
import logic.container.Magazine;
import logic.container.KeyLocker;
import logic.container.HpBottle;
import object.Computer;
import object.Label;
import object.Door;
import object.Lazer;
import object.Sculpture;

public class Obj {
	
	public static boolean pressed = false;
	public static double temp1, temp2;
	public static double time = 0; 
	public static StackPane stackPane = new StackPane();
	public static GridPane gridPane = new GridPane();
	
	public static boolean collisionZero(GameObject A) {
		if(Map.mapTileNum[(int)A.getyPos()/48][(int)A.getxPos()/48] == 0) return true;
		return false;
	}
	
	public static void collision(GameObject A) {
		Handler handler = Handler.getInstance();
		
		for(int i = 0; i < handler.allObjects.size(); i++) {
			if(handler.allObjects.get(i).getCode() == A.getCode()) continue;
			if(A.getSolidArea().intersects(handler.allObjects.get(i).getSolidArea().getBoundsInLocal())) {
				action(A, handler.allObjects.get(i));
			}
		}
	}

	public static void action(GameObject A, GameObject B) {
		Handler handler = Handler.getInstance();
		time += 1;

		switch(A.getId()) {
			case Player : {
				switch(B.getId()) {
					case Bullet : {
						((Player)A).setHp(((Player)A).getHp() - ((Bullet)B).damage());
						handler.removeObject(B);
						break;
					}
					case Computer : {
						if(((Player)A).getKey().E && time > 15) ((Computer)B).interact(((Player)A));
						break;
					}
					case Label1 : {
						if(((Player)A).getKey().E && time > 15) ((Label)B).interact(((Player)A));
						break;
					}
					case Label2 : {
						if(((Player)A).getKey().E && time > 15) ((Label)B).interact(((Player)A));
						break;
					}
					case Label3 : {
						if(((Player)A).getKey().E && time > 15) ((Label)B).interact(((Player)A));
						break;
					}
					case Label4 : {
						if(((Player)A).getKey().E && time > 15) ((Label)B).interact(((Player)A));
						break;
					}
					case Lazer1 : {
						if(((Lazer)B).isShow()) ((Player)A).setHp(((Player)A).getHp() - ((Lazer)B).dps_damage());
						break;
					}
					case Lazer2 : {
						if(((Lazer)B).isShow()) ((Player)A).setHp(((Player)A).getHp() - ((Lazer)B).dps_damage());
						break;
					}
					case Knife : {
						((Knife)B).interact(((Player)A));
						break;
					}
					case Gun : {
						((Gun)B).interact(((Player)A));
						break;
					}
					case Magazine : {
						((Magazine)B).interact(((Player)A));
						break;
					}
					case Door1 : {
						((Door)B).interact(((Player)A));
						break;
					}
					case Door2 : {
						((Door)B).interact(((Player)A));
						break;
					}
					case Key1 : {
						((KeyLocker)B).interact(((Player)A));
						break;
					}
					case Key2 : {
						((KeyLocker)B).interact(((Player)A));
						break;
					}
					case HpBottle : {
						((HpBottle)B).interact(((Player)A));
						break;
					}
					case Sculpture : {
						((Sculpture)B).interact(((Player)A));
						break;
					}
					default : break;
				}
	
				break;
			}
			case Criminal : {
				switch(B.getId()) {
					case Bullet : {
						((Criminal)A).setHp(((Criminal)A).getHp() - ((Bullet)B).damage());
						handler.removeObject(B);
						
						break;
					}
					case Knife : {
						((Criminal)A).setHp(((Criminal)A).getHp() - ((Knife)B).damage());
						
						break;
					}
					default : break;
				}
				break;
			}
			
			default : break;
		}
		return ;
	}
	
	public static void pushOffFrom(Player A, GameObject B) {
		Rectangle RA = A.getSolidArea();
		Rectangle RB = B.getSolidArea();

		switch(A.getDirect()) {
			case "L" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setxPos(A.getxPos() + A.getxVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			case "R" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setxPos(A.getxPos() - A.getxVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			case "U" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setyPos(A.getyPos() + A.getyVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			case "D" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setyPos(A.getyPos() - A.getyVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			default : break;
		}
	}
	
	public static void pushOffFrom(Player A, GameObject B) {
		Rectangle RA = A.getSolidArea();
		Rectangle RB = B.getSolidArea();
		
		switch(A.getDirect()) {
			case "L" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setxPos(A.getxPos() + A.getxVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			case "R" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setxPos(A.getxPos() - A.getxVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			case "U" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setyPos(A.getyPos() + A.getyVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			case "D" : {
				while(RA.intersects(RB.getBoundsInLocal())) {
					A.setyPos(A.getyPos() - A.getyVelo());
					RA = A.getSolidArea();
				}
				break;
			}
			default : break;
		}
	}
	
	public static void getClose(GameObject A, GameObject B) {
		boolean setX = false;
		boolean setY = false;
		
		double _Vx = A.getxVelo();
		double _Vy = A.getyVelo();
		
		if(Math.abs(A.getxPos() - B.getxPos()) < A.getxVelo()) {
			_Vx = 0;
			setX = true;
		}
		if(Math.abs(A.getyPos() - B.getyPos()) < A.getyVelo()) {
			_Vy = 0;
			setY = true;
		}
		
		if(!setX)
			if(A.getxPos() < B.getxPos()) _Vx = Math.abs(A.getxVelo());
			else if(A.getxPos() > B.getxPos()) _Vx = -Math.abs(A.getxVelo());
		if(!setY)
			if(A.getyPos() < B.getyPos()) _Vy = Math.abs(A.getyVelo());
			else if(A.getyPos() > B.getyPos()) _Vy = -Math.abs(A.getyVelo());
		
		int newXPos = 0 ,newYPos = 0;
		if(_Vx >= 0) newXPos = ((int)((A.getxPos() + _Vx - 10)/48));			
		else newXPos = ((int)((A.getxPos() + _Vx - 20)/48));	
		
		if(_Vy >= 0) newYPos = ((int)((A.getyPos() + _Vy - 4)/48)) + 2;		 	
		else newYPos = ((int)((A.getyPos() + _Vy - 20)/48)) + 2;
		
		if(Map.mapTileNum[newYPos][newXPos] != 0) {
			A.setxPos(setX ? B.getxPos() : A.getxPos() + _Vx);
			A.setyPos(setY ? B.getyPos() : A.getyPos() + _Vy);
		}
		else if(Map.mapTileNum[newYPos][newXPos] == 0 && Map.mapTileNum[newYPos][(int)(A.getxPos()-10)/48] != 0 && _Vx >= 0) {
			A.setyPos(setY ? B.getyPos() : A.getyPos() + _Vy);
		}
		else if(Map.mapTileNum[newYPos][newXPos] == 0 && Map.mapTileNum[newYPos][(int)(A.getxPos()-20)/48] != 0 && _Vx < 0) {
			A.setyPos(setY ? B.getyPos() : A.getyPos() + _Vy);
		}
		else if(Map.mapTileNum[newYPos][newXPos] == 0 && Map.mapTileNum[((int)(A.getyPos()-4)/48) + 2][newXPos] != 0 && _Vy >= 0) {
			A.setxPos(setX ? B.getxPos() : A.getxPos() + _Vx);
		}
		else if(Map.mapTileNum[newYPos][newXPos] == 0 && Map.mapTileNum[((int)(A.getyPos()-20)/48) + 2][newXPos] != 0 && _Vy < 0) {
			A.setxPos(setX ? B.getxPos() : A.getxPos() + _Vx);
		}
		
		return ;
	}
	
	public static void follow(GameObject A, GameObject B) {
		// More Function
		getClose(A, B);
	}
	
	public static double distance(GameObject A, GameObject B) {
		double disX = disX(A, B);
		double disY = disY(A, B);
		return Math.sqrt(disX * disX + disY * disY);
	}
	
	public static double disX(GameObject A, GameObject B) {
		return Math.abs(A.getxPos() - B.getxPos());
	}
	
	public static double disY(GameObject A, GameObject B) {
		return Math.abs(A.getyPos() - B.getyPos());
	}
	
	public static String getDirection(GameObject A, GameObject B) {
		if(A instanceof Player) {
			if(A.getxPos() - B.getxPos() > 0) {
				if(disX(A, B) >= disY(A, B)) return "L";
				if(A.getyPos() - B.getyPos() > 0) return "U";
				return "D";
			}
			if(disX(A, B) >= disY(A, B)) return "R";
			if(A.getyPos() - B.getyPos() > 0) return "U";
			return "D";
		}
		else {
			if(A.getxPos() - B.getxPos() > 0) {
				if(disX(A, B) >= disY(A, B)) return "LEFT";
				if(A.getyPos() - B.getyPos() > 0) return "UP";
				return "DOWN";
			}
			if(disX(A, B) >= disY(A, B)) return "RIGHT";
			if(A.getyPos() - B.getyPos() > 0) return "UP";
			return "DOWN";
		}
	}
	
}

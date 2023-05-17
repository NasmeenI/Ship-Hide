package utilz;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.Handler;
import logic.base.Map;
import logic.person.Captive;
import logic.person.Commander;
import logic.person.Criminal;
import logic.person.Person;
import logic.person.Player;
import logic.container.Bullet;
import logic.container.Knife;
import logic.container.Gun;
import logic.container.Magazine;
import logic.container.KeyLocker;
import logic.container.HpBottle;
import object.Computer;
import object.Label;
import object.Helicopter;
import object.Door;
import object.Lazer;
import object.Sculpture;
import static utilz.Constants.GameProcess.*;

import java.util.ArrayList;

import application.GameProcess;

public class Obj {
	
	public static boolean pressed = false;
	public static double temp1, temp2;
	public static double time = 0; 
	public static StackPane stackPane = new StackPane();
	public static GridPane gridPane = new GridPane();
	public static int dx[] = {-1, 0, 1, 0};
	public static int dy[] = {0, -1, 0, 1};
	private static boolean vit[][] = new boolean[240][240];
	private static ArrayList<Rectangle> queue = new ArrayList<Rectangle>();
	
	public static boolean collisionZero(GameObject A) {
		for(int i=(int)A.getSolidArea().getY();i<=(int)A.getSolidArea().getY()+(int)A.getSolidArea().getHeight();i++) {
			for(int j=(int)A.getSolidArea().getX();j<=(int)A.getSolidArea().getX()+(int)A.getSolidArea().getWidth();j++) {
				if(Map.getInstance().mapTileNum[(int)i/TILE_SIZE][(int)j/TILE_SIZE-1] == 0) return true;
			}
		}
		return false;
	}
	
	public static boolean collisionTwo(GameObject A) {
		boolean result = false;
		for(int i=(int)((Person)A).getRenderArea().getY();i<=(int)((Person)A).getRenderArea().getY()+(int)((Person)A).getRenderArea().getHeight() && (int)i/TILE_SIZE<=MAX_SCREEN_ROW ;i++) {
			for(int j=(int)((Person)A).getRenderArea().getX();j<=(int)((Person)A).getRenderArea().getX()+(int)((Person)A).getRenderArea().getWidth() && j/TILE_SIZE<=MAX_SCREEN_COL ;j++) {
				if(Map.getInstance().mapTileNum[(int)i/TILE_SIZE][(int)j/TILE_SIZE-1] == 2) {
					result = true;
					int j1 = (int)j/TILE_SIZE;
					while(j1 <= MAX_SCREEN_COL && Map.getInstance().mapTileNum[(int)i/TILE_SIZE][j1-1] == 2) {
						GameProcess.renderState[(int)i/TILE_SIZE-1][j1] = true;
						GameProcess.renderState[(int)i/TILE_SIZE][j1] = true;
						j1++;
					}
						
					j1 = (int)j/TILE_SIZE-1;
					while(j1 > 0 && Map.getInstance().mapTileNum[(int)i/TILE_SIZE][j1-1] == 2) {
						GameProcess.renderState[(int)i/TILE_SIZE][j1] = true;
						GameProcess.renderState[(int)i/TILE_SIZE-1][j1] = true;
						j1--;
					}
				}
			}
		}
		return result;
	}
	
	public static boolean collisionTwo_sculpture(Sculpture A) {
		boolean result = false;
		for(int i=(int)((Sculpture)A).getSolidArea().getY();i<=(int)((Sculpture)A).getSolidArea().getY()+(int)((Sculpture)A).getSolidArea().getHeight() && i/TILE_SIZE<=MAX_SCREEN_ROW ;i++) {
			for(int j=(int)((Sculpture)A).getSolidArea().getX();j<=(int)((Sculpture)A).getSolidArea().getX()+(int)((Sculpture)A).getSolidArea().getWidth() && j/TILE_SIZE<=MAX_SCREEN_COL ;j++) {
				if(Map.getInstance().mapTileNum[(int)i/TILE_SIZE][(int)j/TILE_SIZE-1] == 2) {
					result = true;
					int j1 = (int)j/TILE_SIZE;
					while(Map.getInstance().mapTileNum[(int)i/TILE_SIZE][j1-1] == 2) {
						GameProcess.renderState[(int)i/TILE_SIZE-1][j1] = true;
						GameProcess.renderState[(int)i/TILE_SIZE][j1] = true;
						j1++;
					}
						
					j1 = (int)j/TILE_SIZE-1;
					while(Map.getInstance().mapTileNum[(int)i/TILE_SIZE][j1-1] == 2) {
						GameProcess.renderState[(int)i/TILE_SIZE][j1] = true;
						GameProcess.renderState[(int)i/TILE_SIZE-1][j1] = true;
						j1--;
					}
				}
			}
		}
		return result;
	}
	
	public static boolean collisionZeroRect(Rectangle A) {
		for(int i=(int)A.getY();i<=(int)A.getY()+(int)A.getHeight();i++) {
			for(int j=(int)A.getX();j<=(int)A.getX()+(int)A.getWidth();j++) {
				if(Map.getInstance().mapTileNum[(int)i/TILE_SIZE][(int)j/TILE_SIZE-1] == 0) return true;
			}
		}
		return false;
	}
	
	public static void collision(GameObject A) {
		for(int i = 0; i < Handler.getInstance().allObjects.size(); i++) {
			if(Handler.getInstance().allObjects.get(i).getCode() == A.getCode() || (Handler.getInstance().allObjects.get(i) instanceof Sculpture)) continue;
			if(A.getSolidArea().intersects(Handler.getInstance().allObjects.get(i).getSolidArea().getBoundsInLocal())) {
				action(A, Handler.getInstance().allObjects.get(i));
			}
		}
	}

	public static void action(GameObject A, GameObject B) {
		time += 1;

		switch(A.getId()) {
			case Player : {
				switch(B.getId()) {
					case Bullet : {
						((Player)A).setHp(((Player)A).getHp() - ((Bullet)B).damage());
						Handler.getInstance().removeObject(B);
						break;
					}
					case Computer : {
						if(((Player)A).getKey().E && time > 30) ((Computer)B).interact(((Player)A));
						break;
					}
					case Helicopter : {
						if(((Player)A).getKey().E && time > 30) ((Helicopter)B).interact(((Player)A));
						break;
					}
					case Label1 : {
						if(((Player)A).getKey().E && time > 30) ((Label)B).interact(((Player)A));
						break;
					}
					case Label2 : {
						if(((Player)A).getKey().E && time > 30) ((Label)B).interact(((Player)A));
						break;
					}
					case Label3 : {
						if(((Player)A).getKey().E && time > 30) ((Label)B).interact(((Player)A));
						break;
					}
					case Label4 : {
						if(((Player)A).getKey().E && time > 30) ((Label)B).interact(((Player)A));
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
						Handler.getInstance().removeObject(B);
						
						break;
					}
					case Knife : {
						((Criminal)A).setHp(((Criminal)A).getHp() - ((Knife)B).damage());
						
						break;
					}
					case Sculpture : {
						((Sculpture)B).interact(((Criminal)A));
						break;
					}
					default : break;
				}
				break;
			}
			case Commander : {
				switch(B.getId()) {
					case Bullet : {
						((Commander)A).setHp(((Commander)A).getHp() - ((Bullet)B).damage());
						Handler.getInstance().removeObject(B);
						
						break;
					}
					case Knife : {
						((Commander)A).setHp(((Commander)A).getHp() - ((Knife)B).damage());
						
						break;
					}
					case Sculpture : {
						((Sculpture)B).interact(((Commander)A));
						break;
					}
					default : break;
				}
				break;
			}
			case Captive : {
				switch(B.getId()) {
					case Bullet : {
						((Captive)A).setHp(((Captive)A).getHp() - ((Bullet)B).damage());
						Handler.getInstance().removeObject(B);
						
						break;
					}
					case Sculpture : {
						((Sculpture)B).interact(((Captive)A));
						break;
					}
					default : break;
				}
				break;
			}
			case Sculpture : {
				switch(B.getId()) {
					case Sculpture : {
						((Sculpture)B).interact(((Sculpture)A));
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
	
	public static void pushOffWall(GameObject A) {
		Rectangle rStart;
		if(A instanceof Person) rStart = ((Person) A).getFootArea();
		else rStart = A.getSolidArea();
		
		if(!Obj.collisionZeroRect(rStart)) return ;
		
		for(int i = 0; i < 240; i++) {
			for(int j = 0; j < 240; j++) {
				vit[i][j] = false;
			}
		}
		
		Rectangle RA = new Rectangle(rStart.getX(), rStart.getY(), rStart.getWidth(), rStart.getHeight());
		int Tx = (int) (RA.getX() - 120);
		int Ty = (int) (RA.getY() - 120);
		queue.add(RA);
		
		while(!queue.isEmpty()) {
			Rectangle RT = queue.get(0);
			queue.remove(0);
			
			if(vit[(int) RT.getX() - Tx][(int) RT.getY() - Ty]) continue;
			vit[(int) RT.getX() - Tx][(int) RT.getY() - Ty] = true;
			
			for(int i = 0; i < 4; i++) {
				int newX = (int) (RT.getX() + dx[i]);
				int newY = (int) (RT.getY() + dy[i]);
				if(newX < 0 || (newX + RT.getWidth()) / 48 > 109 || newY < 0 || (newY + RT.getHeight()) / 48 > 59) continue;
				if(newX - Tx < 0 || newX - Tx >= 240 || newY - Ty < 0 || newY - Ty >= 240 || vit[newX - Tx][newY - Ty]) continue;
				Rectangle RX = new Rectangle(newX, newY, RT.getWidth(), RT.getHeight());
				if(!Obj.collisionZeroRect(RX)) {
					A.setxPos(RX.getX() - rStart.getX() + A.getxPos());
					A.setyPos(RX.getY() - rStart.getY() + A.getyPos());
					if(A instanceof Person) ((Person) A).setFootArea(RX);
					else A.setSolidArea(RX);
					queue.clear();
					return ;
				}
				queue.add(RX);
			}
		}
	}
	
	public static void pushOffFrom(GameObject A, GameObject B) {
		Rectangle rStart;
		if(A instanceof Person) rStart = ((Person) A).getFootArea();
		else rStart = A.getSolidArea();
		
		if(!rStart.intersects(B.getSolidArea().getBoundsInLocal())) return ;
		
		for(int i = 0; i < 240; i++) {
			for(int j = 0; j < 240; j++) {
				vit[i][j] = false;
			}
		}
		
		Rectangle RA = new Rectangle(rStart.getX(), rStart.getY(), rStart.getWidth(), rStart.getHeight());
		int Tx = (int) (RA.getX() - 120);
		int Ty = (int) (RA.getY() - 120);
		queue.add(RA);
		
		while(!queue.isEmpty()) {
			Rectangle RT = queue.get(0);
			queue.remove(0);
			
			if(vit[(int) RT.getX() - Tx][(int) RT.getY() - Ty]) continue;
			vit[(int) RT.getX() - Tx][(int) RT.getY() - Ty] = true;
			
			for(int i = 0; i < 4; i++) {
				int newX = (int) (RT.getX() + dx[i]);
				int newY = (int) (RT.getY() + dy[i]);
				if(newX < 0 || (newX + RT.getWidth()) / 48 > 109 || newY < 0 || (newY + RT.getHeight()) / 48 > 59) continue;
				if(newX - Tx < 0 || newX - Tx >= 240 || newY - Ty < 0 || newY - Ty >= 240 || vit[newX - Tx][newY - Ty]) continue;
				Rectangle RX = new Rectangle(newX, newY, RT.getWidth(), RT.getHeight());
				if(!RX.intersects(B.getSolidArea().getBoundsInLocal())) {
					A.setxPos(RX.getX() - rStart.getX() + A.getxPos());
					A.setyPos(RX.getY() - rStart.getY() + A.getyPos());
					if(A instanceof Person) ((Person) A).setFootArea(RX);
					else A.setSolidArea(RX);
					queue.clear();
					return ;
				}
				queue.add(RX);
			}
		}
	}
	
	public static void getClosePoint(GameObject A, int mPx, int mPy, int xPos, int yPos) {
		boolean setX = false;
		boolean setY = false;
		
		if(Math.abs(mPx - xPos) < A.getxVelo()) {
			A.setxPos(A.getxPos() + Math.abs(mPx - xPos));
			setX = true;
		}
		if(Math.abs(mPy - yPos) < A.getyVelo()) {
			A.setyPos(A.getyPos() + Math.abs(mPy - yPos));
			setY = true;
		}
		
		if(!setX)
			if(mPx < xPos) A.setxPos(A.getxPos() + Math.abs(A.getxVelo()));
			else if(mPx > xPos) A.setxPos(A.getxPos() - Math.abs(A.getxVelo()));
		if(!setY)
			if(mPy < yPos) A.setyPos(A.getyPos() + Math.abs(A.getyVelo()));
			else if(mPy > yPos) A.setyPos(A.getyPos() - Math.abs(A.getyVelo()));
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
		
		if(Map.getInstance().mapTileNum[newYPos][newXPos] != 0) {
			A.setxPos(setX ? B.getxPos() : A.getxPos() + _Vx);
			A.setyPos(setY ? B.getyPos() : A.getyPos() + _Vy);
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[newYPos][(int)(A.getxPos()-10)/48] != 0 && _Vx >= 0) {
			A.setyPos(setY ? B.getyPos() : A.getyPos() + _Vy);
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[newYPos][(int)(A.getxPos()-20)/48] != 0 && _Vx < 0) {
			A.setyPos(setY ? B.getyPos() : A.getyPos() + _Vy);
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[((int)(A.getyPos()-4)/48) + 2][newXPos] != 0 && _Vy >= 0) {
			A.setxPos(setX ? B.getxPos() : A.getxPos() + _Vx);
		}
		else if(Map.getInstance().mapTileNum[newYPos][newXPos] == 0 && Map.getInstance().mapTileNum[((int)(A.getyPos()-20)/48) + 2][newXPos] != 0 && _Vy < 0) {
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

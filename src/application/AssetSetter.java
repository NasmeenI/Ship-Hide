package application;

import logic.base.Handler;
import logic.base.ID;
import logic.container.Gun;
import logic.container.HpBottle;
import logic.container.KeyLocker;
import logic.container.Knife;
import logic.container.Magazine;
import object.Computer;
import object.Door;
import object.DoorJail;
import object.Label;
import object.Lazer;
import object.MusiumDoor;
import object.Sculpture;
import utilz.Checker;
import static utilz.Constants.GameProcess.*;

public class AssetSetter {
	Handler handler;
	
	public AssetSetter() {
		this.handler = Handler.getInstance();
	}
	
	public void setObject() {
		
		// Sculpture
		Sculpture[] sculpture = new Sculpture[4];
		sculpture[0] = new Sculpture(73 * TILE_SIZE ,47 * TILE_SIZE, ID.Sculpture1);
		sculpture[1] = new Sculpture(53 * TILE_SIZE ,47 * TILE_SIZE, ID.Sculpture2);
		sculpture[2] = new Sculpture(73 * TILE_SIZE ,40 * TILE_SIZE, ID.Sculpture3);
		sculpture[3] = new Sculpture(56 * TILE_SIZE ,38 * TILE_SIZE, ID.Sculpture3);
		for(int i=0;i<4;i++) handler.allObjects.add(sculpture[i]);	
		
		// Computer
		handler.allObjects.add(new Computer(68 * TILE_SIZE ,12 * TILE_SIZE, ID.Computer));	
		
		// Label
		handler.allObjects.add(new Label(58 * TILE_SIZE ,46 * TILE_SIZE, ID.Label1));	
		handler.allObjects.add(new Label(64 * TILE_SIZE ,46 * TILE_SIZE, ID.Label2));
		handler.allObjects.add(new Label(72 * TILE_SIZE ,46 * TILE_SIZE, ID.Label3));
		handler.allObjects.add(new Label(65 * TILE_SIZE ,36 * TILE_SIZE, ID.Label4));
		
		// Weapons
		handler.allObjects.add(new Knife(27 * TILE_SIZE ,12 * TILE_SIZE, ID.Knife ,true));
		handler.allObjects.add(new Gun(27 * TILE_SIZE ,15 * TILE_SIZE, ID.Gun ,true));
		handler.allObjects.add(new Magazine(25 * TILE_SIZE ,14 * TILE_SIZE, ID.Magazine ,6));
		handler.allObjects.add(new Magazine(22 * TILE_SIZE ,14 * TILE_SIZE, ID.Magazine ,6));
		
		// HP Bottle
		handler.allObjects.add(new HpBottle(22 * TILE_SIZE ,16 * TILE_SIZE, ID.HpBottle));
		
		// Keys
		handler.allObjects.add(new KeyLocker(37 * TILE_SIZE ,33 * TILE_SIZE, ID.Key1));
		handler.allObjects.add(new KeyLocker(37 * TILE_SIZE ,15 * TILE_SIZE, ID.Key2));
		
		// Doors
		handler.allObjects.add(new Door(31 * TILE_SIZE ,31 * TILE_SIZE, ID.Door1, ID.Key1));
		handler.allObjects.add(new Door(46 * TILE_SIZE ,45 * TILE_SIZE, ID.Door2, ID.Key2));
		handler.allObjects.add(new DoorJail(71 * TILE_SIZE,15 * TILE_SIZE, ID.DoorJail));
		handler.allObjects.add(new MusiumDoor(53 * TILE_SIZE ,31 * TILE_SIZE, ID.MusiumDoor ,sculpture));
		
		// Lazer_1
		for(int i=48;i<=54;i+=3) {
			int interval = Checker.Rand(1, 8) * 30; // Random between 1.0 to 4.0 seconds (+-0.5) and multiply with FPS Constant
			for(int j=19;j<=27;j++) {
				handler.allObjects.add(new Lazer(i * TILE_SIZE ,j * TILE_SIZE, ID.Lazer1, 5, interval));	
			}
		}
				
		// Lazer_2
		for(int i=20;i<=26;i+=2) {
			int interval = Checker.Rand(1, 8) * 30;
			for(int j=47;j<=55;j++) {
				handler.allObjects.add(new Lazer(j * TILE_SIZE ,i * TILE_SIZE, ID.Lazer2, 5, interval));
			}
		}
	}
}
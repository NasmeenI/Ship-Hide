package application;

import logic.base.Handler;
import logic.base.ID;
import logic.container.Gun;
import logic.container.HpBottle;
import logic.container.KeyLocker;
import logic.container.Knife;
import logic.container.Magazine;
import logic.person.Captive;
import logic.person.Commander;
import logic.person.Criminal;
import object.Computer;
import object.Door;
import object.DoorJail;
import object.Helicopter;
import object.Label;
import object.Lazer;
import object.MusiumDoor;
import object.Sculpture;
import utilz.Checker;
import static utilz.Constants.GameProcess.*;

public class AssetSetter {
	
	public AssetSetter() {
	}
	
	public void setObject() {
		
		// Captive
		Handler.getInstance().addObject(new Captive(3000, 600, ID.Captive, 2, 2));
		
		// Criminal
		Handler.getInstance().addObject(new Criminal(600, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(600, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(500, 700, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(600, 800, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(600, 900, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(600, 1000, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(800, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(900, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1200, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1300, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1400, 600, ID.Criminal, 2, 2));
		
		// Commander
		Handler.getInstance().addObject(new Commander(2600, 600, ID.Commander, 2, 2));
		
		
		// Sculptures
		Sculpture[] sculpture = new Sculpture[4];
		sculpture[0] = new Sculpture(65 * TILE_SIZE ,42 * TILE_SIZE, ID.Sculpture);
		sculpture[1] = new Sculpture(52 * TILE_SIZE ,37 * TILE_SIZE, ID.Sculpture); 
		sculpture[2] = new Sculpture(73 * TILE_SIZE ,37 * TILE_SIZE, ID.Sculpture); 
		sculpture[3] = new Sculpture(52 * TILE_SIZE ,46 * TILE_SIZE, ID.Sculpture); 
		
		// Sculptures are correct location. 
//		sculpture[0] = new Sculpture(68 * TILE_SIZE ,46 * TILE_SIZE, ID.Sculpture);
//		sculpture[1] = new Sculpture(52 * TILE_SIZE ,40 * TILE_SIZE, ID.Sculpture); 
//		sculpture[2] = new Sculpture(72 * TILE_SIZE ,41 * TILE_SIZE, ID.Sculpture); 
//		sculpture[3] = new Sculpture(58 * TILE_SIZE ,42 * TILE_SIZE, ID.Sculpture); 
		for(int i=0;i<4;i++) Handler.getInstance().allObjects.add(sculpture[i]);	
		
		// Computer
		Handler.getInstance().allObjects.add(new Computer(68 * TILE_SIZE ,12 * TILE_SIZE, ID.Computer));	
		
		// Helicopter
		Handler.getInstance().allObjects.add(new Helicopter(86 * TILE_SIZE ,30 * TILE_SIZE, ID.Helicopter));	
		
		// Labels
		Handler.getInstance().allObjects.add(new Label(58 * TILE_SIZE ,46 * TILE_SIZE, ID.Label1));	
		Handler.getInstance().allObjects.add(new Label(64 * TILE_SIZE ,46 * TILE_SIZE, ID.Label2));
		Handler.getInstance().allObjects.add(new Label(72 * TILE_SIZE ,46 * TILE_SIZE, ID.Label3));
		Handler.getInstance().allObjects.add(new Label(65 * TILE_SIZE ,36 * TILE_SIZE, ID.Label4));
		
		// Weapons
		Handler.getInstance().allObjects.add(new Gun(26 * TILE_SIZE ,13 * TILE_SIZE, ID.Gun ,true));
		Handler.getInstance().allObjects.add(new Knife(16 * TILE_SIZE ,38 * TILE_SIZE, ID.Knife ,true));
		Handler.getInstance().allObjects.add(new Magazine(16 * TILE_SIZE ,20 * TILE_SIZE, ID.Magazine));
		Handler.getInstance().allObjects.add(new Magazine(60 * TILE_SIZE ,38 * TILE_SIZE, ID.Magazine));
		Handler.getInstance().allObjects.add(new Magazine(59 * TILE_SIZE ,13 * TILE_SIZE, ID.Magazine));
		Handler.getInstance().allObjects.add(new Magazine(59 * TILE_SIZE ,33 * TILE_SIZE, ID.Magazine));
		Handler.getInstance().allObjects.add(new Magazine(61 * TILE_SIZE ,33 * TILE_SIZE, ID.Magazine));
		Handler.getInstance().allObjects.add(new Magazine(63 * TILE_SIZE ,33 * TILE_SIZE, ID.Magazine));
		Handler.getInstance().allObjects.add(new Magazine(65 * TILE_SIZE ,33 * TILE_SIZE, ID.Magazine));
		
		// HP Bottles
		Handler.getInstance().allObjects.add(new HpBottle(32 * TILE_SIZE ,47 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(71 * TILE_SIZE ,37 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(49 * TILE_SIZE ,23 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(48 * TILE_SIZE ,13 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(66 * TILE_SIZE ,13 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(74 * TILE_SIZE ,28 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(74 * TILE_SIZE ,33 * TILE_SIZE, ID.HpBottle));
		
		// Keys
		Handler.getInstance().allObjects.add(new KeyLocker(37 * TILE_SIZE ,33 * TILE_SIZE, ID.Key1));
		Handler.getInstance().allObjects.add(new KeyLocker(37 * TILE_SIZE ,15 * TILE_SIZE, ID.Key2));
		
		// Doors
		Handler.getInstance().allObjects.add(new Door(31 * TILE_SIZE ,31 * TILE_SIZE, ID.Door1, ID.Key1));
		Handler.getInstance().allObjects.add(new Door(46 * TILE_SIZE ,45 * TILE_SIZE, ID.Door2, ID.Key2));
		Handler.getInstance().allObjects.add(new DoorJail(71 * TILE_SIZE,15 * TILE_SIZE, ID.DoorJail));
		Handler.getInstance().allObjects.add(new MusiumDoor(53 * TILE_SIZE ,31 * TILE_SIZE, ID.MusiumDoor ,sculpture));
		
		// Lazer_1
		for(int i=48;i<=54;i+=3) {
			int interval = Checker.Rand(1, 8) * 30; // Random between 1.0 to 4.0 seconds (+-0.5) and multiply with FPS Constant
			for(int j=19;j<=27;j++) {
				Handler.getInstance().allObjects.add(new Lazer(i * TILE_SIZE ,j * TILE_SIZE, ID.Lazer1, 5, interval));	
			}
		}
				
		// Lazer_2
		for(int i=20;i<=26;i+=2) {
			int interval = Checker.Rand(1, 8) * 30;
			for(int j=47;j<=55;j++) {
				Handler.getInstance().allObjects.add(new Lazer(j * TILE_SIZE ,i * TILE_SIZE, ID.Lazer2, 5, interval));
			}
		}
	}
}
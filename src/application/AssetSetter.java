package application;

import logic.base.Handler;
import logic.base.ID;
import logic.container.HpBottle;
import logic.container.KeyLocker;
import logic.container.Knife;
import logic.container.pistol.PistolMagazine;
import logic.container.pistol.PistolGun;
import logic.person.Captive;
import logic.person.Commander;
import logic.person.Criminal;
import logic.person.Player;
import object.Computer;
import object.Door;
import object.DoorJail;
import object.Helicopter;
import object.Label;
import object.Lazer;
import object.MuseumDoor;
import object.Sculpture;
import object.Trader;
import utilz.Checker;
import static utilz.Constants.GameProcess.*;

public class AssetSetter {
	
	public void setObject() {
		
		// Captive
		Handler.getInstance().addObject(new Captive(3450, 620, ID.Captive, 2, 2));
		
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
		
		// Trader
		Handler.getInstance().allObjects.add(new Trader(34 * TILE_SIZE ,46 * TILE_SIZE, ID.Trader));	
		
		// Labels
		Handler.getInstance().allObjects.add(new Label(58 * TILE_SIZE ,46 * TILE_SIZE, ID.Label1));	
		Handler.getInstance().allObjects.add(new Label(64 * TILE_SIZE ,46 * TILE_SIZE, ID.Label2));
		Handler.getInstance().allObjects.add(new Label(72 * TILE_SIZE ,46 * TILE_SIZE, ID.Label3));
		Handler.getInstance().allObjects.add(new Label(65 * TILE_SIZE ,36 * TILE_SIZE, ID.Label4));
		
		// Keys
		Handler.getInstance().allObjects.add(new KeyLocker(37 * TILE_SIZE ,33 * TILE_SIZE, ID.Key1));
			Handler.getInstance().allObjects.add(new KeyLocker(37 * TILE_SIZE ,15 * TILE_SIZE, ID.Key2));
			
		// Doors
		Handler.getInstance().allObjects.add(new Door(31 * TILE_SIZE ,31 * TILE_SIZE, ID.Door1, ID.Key1));
		Handler.getInstance().allObjects.add(new Door(46 * TILE_SIZE ,45 * TILE_SIZE, ID.Door2, ID.Key2));
		Handler.getInstance().allObjects.add(new DoorJail(71 * TILE_SIZE,15 * TILE_SIZE, ID.DoorJail));
		Handler.getInstance().allObjects.add(new MuseumDoor(53 * TILE_SIZE ,31 * TILE_SIZE, ID.MusiumDoor ,sculpture));
		
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
	
	public void setForDetective() {
		
		// Criminal Room 1&2
		Handler.getInstance().addObject(new Criminal(820, 1200, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(900, 700, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(1000, 2200, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(1250, 600, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(1450, 650, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(1750, 1550, ID.Criminal, .5f, .5f));
				
		// Criminal Room 3&4
		Handler.getInstance().addObject(new Criminal(2600, 2000, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(3300, 1150, ID.Criminal, .5f, .5f));
		Handler.getInstance().addObject(new Criminal(3300, 1200, ID.Criminal, .5f, .5f));
				
		// Commander
		Handler.getInstance().addObject(new Commander(2600, 600, ID.Commander, .5f, .5f));
		Handler.getInstance().addObject(new Commander(1200, 1200, ID.Commander, .5f, .5f));
		Handler.getInstance().addObject(new Commander(3700, 1500, ID.Commander, .5f, .5f));
		
		// Weapons
		Handler.getInstance().allObjects.add(new PistolGun(26 * TILE_SIZE ,13 * TILE_SIZE, ID.PistolGun ,true));
		Handler.getInstance().allObjects.add(new Knife(16 * TILE_SIZE ,38 * TILE_SIZE, ID.Knife ,true));
		Handler.getInstance().allObjects.add(new PistolMagazine(16 * TILE_SIZE ,20 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(60 * TILE_SIZE ,38 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(59 * TILE_SIZE ,13 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(59 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(61 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(63 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(65 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
				
		// HP Bottles
		Handler.getInstance().allObjects.add(new HpBottle(32 * TILE_SIZE ,47 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(71 * TILE_SIZE ,37 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(49 * TILE_SIZE ,23 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(48 * TILE_SIZE ,13 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(66 * TILE_SIZE ,13 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(74 * TILE_SIZE ,28 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(74 * TILE_SIZE ,33 * TILE_SIZE, ID.HpBottle));
	}
	
	public void setForDesperate(Player player) {
		
		// Criminal Room 1&2
		Handler.getInstance().addObject(new Criminal(820, 1200, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(850, 1040, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(900, 700, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(904, 1970, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1000, 2200, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1166, 1378, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1200, 1500, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1250, 600, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1349, 2303, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1450, 650, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1707, 586, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1750, 1550, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1750, 1550, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1763, 1149, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1763, 1398, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1807, 2239, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1853, 1907, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(1948, 785, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2043, 1185, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2069, 2257, ID.Criminal, 2, 2));
						
		// Criminal Room 3&4
		Handler.getInstance().addObject(new Criminal(2256, 823, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2281, 2276, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2301, 2025, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2416, 1635, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2600, 2000, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2668, 1976, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2750, 2287, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2765, 1870, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2781, 964, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(2801, 1160, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3118, 2281, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3129, 1908, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3230, 736, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3275, 1607, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3300, 1150, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3300, 1200, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3493, 2009, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3505, 889, ID.Criminal, 2, 2));
		
		// Criminal Room 5
		Handler.getInstance().addObject(new Criminal(3695, 906, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3702, 675, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3702, 1468, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3702, 1905, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3765, 1155, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(3840, 2014, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(4010, 869, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(4441, 1395, ID.Criminal, 2, 2));
		Handler.getInstance().addObject(new Criminal(4200, 1675, ID.Criminal, 2, 2));
					
		// Commander
		Handler.getInstance().addObject(new Commander(2600, 600, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(1200, 1200, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(2833, 663, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(2833, 1643, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(3493, 2289, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(3381, 1863, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(3700, 1500, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(3870, 1249, ID.Commander, 2, 2));
		Handler.getInstance().addObject(new Commander(4211, 1143, ID.Commander, 2, 2));
		
		// Weapons
		Handler.getInstance().allObjects.add(new PistolGun(26 * TILE_SIZE ,13 * TILE_SIZE, ID.PistolGun ,true));
		Handler.getInstance().allObjects.add(new Knife(16 * TILE_SIZE ,38 * TILE_SIZE, ID.Knife ,true));
		Handler.getInstance().allObjects.add(new PistolMagazine(16 * TILE_SIZE ,20 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(60 * TILE_SIZE ,38 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(59 * TILE_SIZE ,13 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(59 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(61 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(63 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
		Handler.getInstance().allObjects.add(new PistolMagazine(65 * TILE_SIZE ,33 * TILE_SIZE, ID.PistolMagazine));
				
		// HP Bottles
		Handler.getInstance().allObjects.add(new HpBottle(32 * TILE_SIZE ,47 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(71 * TILE_SIZE ,37 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(49 * TILE_SIZE ,23 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(48 * TILE_SIZE ,13 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(66 * TILE_SIZE ,13 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(74 * TILE_SIZE ,28 * TILE_SIZE, ID.HpBottle));
		Handler.getInstance().allObjects.add(new HpBottle(74 * TILE_SIZE ,33 * TILE_SIZE, ID.HpBottle));
	}
}
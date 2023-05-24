package logic.container.pistol;

import logic.base.ID;
import logic.container.Magazine;

public class PistolMagazine extends Magazine {
	
	private static final long serialVersionUID = 1L;
	
	public PistolMagazine(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 6);
		setVisible(true);
	}
	
	public PistolMagazine(double xPos, double yPos, ID id, int numBullet) {
		super(xPos, yPos, id, numBullet);
		setVisible(true);
	}
}
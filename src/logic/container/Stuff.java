package logic.container;

import logic.base.GameObject;
import logic.base.ID;

public abstract class Stuff extends GameObject {

	private static final long serialVersionUID = 1L;

	public Stuff(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
	}
}

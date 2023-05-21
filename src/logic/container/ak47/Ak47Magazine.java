package logic.container.ak47;

import logic.base.ID;
import logic.base.StableObject;
import logic.container.Magazine;

public class Ak47Magazine extends Magazine implements StableObject {
	
	private static final long serialVersionUID = 1L;
	private int price;
	private String name;
	
	public Ak47Magazine(double xPos, double yPos, ID id) {
		super(xPos, yPos, id, 30);
		setName("Ak-47 Magazine");
		setPrice(5);
	}

	public Ak47Magazine(double xPos, double yPos, ID id, int numBullet) {
		super(xPos, yPos, id, numBullet);
		setName("Ak-47 Magazine");
		setPrice(5);
	}

	// Getter & Setter
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
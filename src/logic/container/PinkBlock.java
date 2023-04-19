package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.base.GameObject;
import logic.base.ID;
import static utilz.Constants.Debug.*;

public class PinkBlock extends GameObject {
	
	private boolean visible = true;

	public PinkBlock(double xPos, double yPos, ID id) {
		super(xPos, yPos, id);
		setSolidArea(new Rectangle(getxPos(), getyPos(), 48, 48));
		this.visible = true;
	}
	
	public PinkBlock(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id);
		setSolidArea(new Rectangle(getxPos() + 10, getyPos() + 10, 48, 48));
		this.visible = visible;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
		if(!isVisible()) return ;
		if(SOLID_SHOW) ShowSolidArea(gc, 10, 10);
		
		gc.setFill(Color.PINK);
		gc.fillRect((int)xPos, (int)yPos, 48, 48);
		return ;
	}
	
	// Getters & Setters
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		return ;
	}
	
}

package logic.container;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import logic.base.GameObject;
import logic.base.ID;
import static utilz.Constants.Debug.*;

public class PinkBlock extends GameObject {
	
	private static final long serialVersionUID = 1L;
	private boolean visible = true;

	public PinkBlock(double xPos, double yPos, ID id) {
		super(xPos, yPos, id ,0, 0, 48, 48);
		this.visible = true;
	}
	
	public PinkBlock(double xPos, double yPos, ID id, boolean visible) {
		super(xPos, yPos, id ,0, 0, 48, 48);
		this.visible = visible;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GraphicsContext gc) {
		if(!isVisible()) return ;
		if(SOLID_SHOW) ShowSolidArea(gc);
		
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

	@Override
	public void initImg() {
		// TODO Auto-generated method stub
		
	}
	
}

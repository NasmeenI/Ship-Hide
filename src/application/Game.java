package application;

import Scenes.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;
import static utilz.Constants.Screen.*;

public class Game extends Application {

	public final int originalTileSize = 16;
    public final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 110;
    public final int maxScreenRow = 60;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    
    public static final int WIDTH = S_WIDTH_DEFAULT;
	public static final int HEIGHT = S_HEIGHT_DEFAULT;
	public static final int FPS = 600;

	public void start(Stage stage) throws Exception {
		
		MenuScene menuScene = new MenuScene(stage);
		
		stage.setScene(menuScene.getScene());
		stage.setTitle("Ship Hide");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
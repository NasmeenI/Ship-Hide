package application;

import Scenes.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
	MenuScene menuScene;
	
	public void start(Stage stage) throws Exception {
		menuScene = new MenuScene(stage);
		
		stage.setScene(MenuScene.startScene);
		stage.setTitle("Ship Hide");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
package application;

import Scenes.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Game extends Application {
	
	public void start(Stage stage) throws Exception {  
		new MenuScene(stage);
		
		stage.setScene(MenuScene.startScene);
		stage.setResizable(false);
		stage.setTitle("Ship Hide");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
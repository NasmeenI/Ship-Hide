package application;

import Scenes.MenuScene;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
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
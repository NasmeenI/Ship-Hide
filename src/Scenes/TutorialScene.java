package Scenes;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TutorialScene {
	public static Scene scene;
	public TutorialScene(Stage stage ,MenuScene menuScene) {
		StackPane root = new StackPane();
		scene = new Scene(root);
		
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		String image_path = "file:res/background.jpg";
		gc.drawImage(new Image(image_path) ,0 ,0 ,960 ,640);

		Button backButton = new Button("Back");
		backButton.setTranslateX(-370);
		backButton.setTranslateY(-250);
        backButton.setOnAction(event -> {
            // Set the scene to the main scene
        	stage.setScene(menuScene.getScene());
        });
		
		root.getChildren().addAll(backButton);	
	}
	
	public Scene getScene() {
		return scene;
	}
}

package Scenes;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoadingScene {
	
	public static Stage stage;
	public static Scene scene;
	public static ProgressBar progressBar;
	
	public LoadingScene(Stage stage) {
		LoadingScene.stage = stage;
		initScene();
	}

	public void initScene() {
		Canvas canvas = new Canvas(960 ,640);
		
		progressBar = new ProgressBar();
        progressBar.setPrefWidth(200);

        // Create a label to display loading text
        Label loadingLabel = new Label("Loading...");

        // Create a layout pane to center the progress bar and label
        StackPane layout = new StackPane();
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(canvas, progressBar, loadingLabel);
        layout.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        // Create a scene with the layout
        scene = new Scene(layout);
    }
	
	public static void loading() {
		// Set the scene on the primary stage
		LoadingScene.stage.setScene(scene);
		LoadingScene.stage.setTitle("Loading Screen Example");
		LoadingScene.stage.show();
        
		// Simulate a time-consuming task
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (int i = 0; i <= 10; i++) {
                    updateProgress(i, 10);
                    Thread.sleep(500); // Simulate some work
                }
                return null;
            }
        };

        // Bind the progress property of the progress bar to the task's progress
        progressBar.progressProperty().bind(task.progressProperty());

        // When the task is complete, close the loading screen and do further processing
        task.setOnSucceeded(event -> {
 //        	stage.close();
            // Perform additional operations after the loading screen
            // For example, you can open a new window or start your main application logic
        });

        // Start the task in a background thread
        Thread thread = new Thread(task);
        thread.start();
	}
}
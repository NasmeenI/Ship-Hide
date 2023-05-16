package Scenes;

import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilz.LoadSave;
import application.GameProcess;
import static utilz.Constants.GameState.*;

public class MenuScene {
	private static Stage stage;
	public static Scene startScene;
	public static Scene continueScene;
	public static boolean start;
	
	public MenuScene(Stage stage) {
		MenuScene.stage = stage;
		MenuScene.start = false;
		initStartScene();
		
		new CustomScene(stage);
		new TutorialScene(stage);
		new SettingScene(stage);
		new LoadingScene(stage);
	}

	public void initStartScene() {
		StackPane root = new StackPane();
		MenuScene.startScene = new Scene(root);
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.MENU_SCENE) ,0 ,0 ,960 ,640);
		
		VBox box = new VBox(
			10,
			new MenuItem("New Game" ,() -> {
				LoadingScene.loading();
				MenuScene.start = true;
				Thread loadGame = new Thread(() -> {
					new GameProcess(MenuScene.stage);
					GameProcess.save();
				});
				loadGame.start();
				
				new Thread(() -> {
					try {
						loadGame.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					Platform.runLater(() -> MenuScene.stage.setScene(GameProcess.scene));
				}).start();
			}),
			new MenuItem("Load Save" ,() -> {
				LoadingScene.loading();
				
				Thread loadSave = new Thread(() -> {
					new GameProcess(stage);
					GameProcess.loadSave();	
				});
				loadSave.start();
				
				new Thread(() ->{
					try {
						loadSave.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Platform.runLater(() -> MenuScene.stage.setScene(GameProcess.scene));
				}).start();
			}),
			new MenuItem("Tutorial" ,() -> {
				MenuScene.stage.setScene(TutorialScene.scene);
			}),
			new MenuItem("Setting" ,() -> {
				MenuScene.stage.setScene(SettingScene.scene);
			}),
			new MenuItem("Exit" ,() -> Platform.exit())
		);
		box.setTranslateX(100);
		box.setTranslateY(200);
			
		root.getChildren().addAll(box);
	}
	
	public static void initContinueScene(GameProcess gameProcess) {
		StackPane root = new StackPane();
		MenuScene.continueScene = new Scene(root);
		Canvas canvas = new Canvas(960 ,640);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		gc.drawImage(LoadSave.GetSpriteAtlas(LoadSave.MENU_SCENE) ,0 ,0 ,960 ,640);
		
		VBox box = new VBox(
			10,
			new MenuItem("Continue" ,() -> {
				try {
					GameProcess.setGameState(PLAY_STATE); 
					gameProcess.run(gameProcess.getGc());
					gameProcess.setESCState(false);
					gameProcess.setFalseKeyESC();
					GameProcess.input.setFalse();
					GameProcess.stage.setScene(GameProcess.scene);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}),
			new MenuItem("Load Save" ,() -> {
				LoadingScene.loading();
				
				Thread loadSave = new Thread(() -> {
					GameProcess.loadSave();	
				});
				loadSave.start();
				
				new Thread(() ->{
					try {
						loadSave.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Platform.runLater(() -> {
						try {
							GameProcess.setGameState(PLAY_STATE); 
							gameProcess.run(gameProcess.getGc());
							gameProcess.setESCState(false);
							gameProcess.setFalseKeyESC();
							GameProcess.stage.setScene(GameProcess.scene);
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
				}).start();
			}),
			new MenuItem("Tutorial" ,() -> {
				GameProcess.stage.setScene(TutorialScene.scene);
			}),
			new MenuItem("Setting" ,() -> {
				GameProcess.stage.setScene(SettingScene.scene);
			}),
			new MenuItem("Exit" ,() -> Platform.exit())
		);
		box.setTranslateX(100);
		box.setTranslateY(200);
			
		root.getChildren().addAll(box);
	}

	public static class MenuItem extends StackPane {
		MenuItem(String name ,Runnable action){
			LinearGradient gradient = new LinearGradient(
					0 ,0.5 ,1 ,0.5 ,true ,CycleMethod.NO_CYCLE,
					new Stop(0.1 ,Color.web("black" ,0.75)),
					new Stop(1.0 ,Color.web("black" ,0.15))
			);
			Rectangle bg0 = new Rectangle(250 ,40 ,gradient);
			Rectangle bg1 = new Rectangle(250 ,40 ,Color.web("black" ,0.6));
			
			FillTransition ft = new FillTransition(Duration.seconds(1), bg1 ,Color.web("black" ,0.2) ,Color.web("white" ,0.3));
			
			ft.setAutoReverse(true);
			ft.setCycleCount(Integer.MAX_VALUE);
			
			hoverProperty().addListener((o ,oldValue ,isHovering) -> {
				if(isHovering) {
					ft.playFromStart();
				}
				else {
					ft.stop();
					bg1.setFill(Color.web("black",0.2));
				}
			});
			
			Rectangle line = new Rectangle(5 ,40);
			line.widthProperty().bind(
					Bindings.when(hoverProperty())
					.then(8)
					.otherwise(5)
			);
			line.fillProperty().bind(
					Bindings.when(hoverProperty())
					.then(Color.RED)
					.otherwise(Color.GRAY)
			);
			
			Text text = new Text(name);
			text.setFont(Font.font(22.0));
			text.fillProperty().bind(
					Bindings.when(hoverProperty())
					.then(Color.WHITE)
					.otherwise(Color.GRAY)
			);
			
			setOnMouseClicked(e -> action.run());
			setOnMousePressed(e -> bg0.setFill(Color.WHITE));
			setOnMouseReleased(e -> bg0.setFill(gradient));
			
			setAlignment(Pos.CENTER_LEFT);
			
			HBox box = new HBox(15 ,line ,text);
			box.setAlignment(Pos.CENTER_LEFT);
			
			getChildren().addAll(bg0 ,bg1 ,box);
		}
	}
	
	public static Scene getScene() {
		if(!MenuScene.start) return MenuScene.startScene;
    	return MenuScene.continueScene;
	}
}
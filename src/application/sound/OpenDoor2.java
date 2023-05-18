package application.sound;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class OpenDoor2 {
	public static MediaPlayer sound;
	
	public OpenDoor2() {	
		Media media = new Media(new File("res/Sound/openDoor2.mp3").toURI().toString());
		sound = new MediaPlayer(media);
		sound.play();
		Thread playSound = new Thread(() -> {
			Platform.runLater(() -> sound.play());
		});
		playSound.start();
		
		new Thread(() -> {
			try {
				playSound.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sound.stop();
		}).start();
	}
}
package application.sound;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Error {
	public static MediaPlayer sound;
	
	public Error() {	
		Media media = new Media(new File("res/Sound/getCoin.mp3").toURI().toString());
		sound = new MediaPlayer(media);
		sound.setVolume(1);
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
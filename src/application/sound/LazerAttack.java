package application.sound;

import java.io.File;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LazerAttack {
	public static MediaPlayer sound;
	
	public LazerAttack() {	
		Media media = new Media(new File("res/Sound/lazer.mp3").toURI().toString());
		sound = new MediaPlayer(media);
		sound.setVolume(0.3);
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
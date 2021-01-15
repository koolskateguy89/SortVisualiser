package dara.utils;

import java.net.URL;

import javafx.scene.media.AudioClip;

public class SoundFactory {

private SoundFactory() { }
	
	private static final AudioClip PING;
	
	private static final AudioClip OOOO;
	
	static {
		URL pingURL = SoundFactory.class.getClassLoader().getResource("sounds/ping.mp3");
		PING = new AudioClip(pingURL.toString());
		PING.setVolume(.5);
		
		// TODO: find a sound
		OOOO = null;
	}
	
	public static void ping() {
		PING.play();
	}
	
	public static void oooo(double pitch) {
		//OOOO.setRate(pitch);
		OOOO.play();
	}
}

package sv3.utils;

import java.net.URL;

import javafx.scene.media.AudioClip;

//TODO: once this is ready, make methods private
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

	private static void ping() {
		PING.play();
	}

	// TODO: implement setting the pitch of a sound
	private static void oooo(double pitch) {
		//OOOO.setRate(pitch);
		OOOO.play();
	}
}

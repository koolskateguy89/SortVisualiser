package koolskateguy89.utils;

import static koolskateguy89.utils.Utils.sleep;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.shape.Rectangle;

class Deselector {
	
	private Deselector() { }
	
	private static Queue<Rectangle> q = new LinkedBlockingQueue<>();
	
	static {
		Thread thread = new Thread(() -> {
			while (true) {
				if (Utils.deselect != Utils.DEFAULT_COLOUR) {
					q.clear();
				}
				if (!q.isEmpty()) {
					q.poll().setFill(Utils.deselect);
				}
				sleep(20);
			}
		});
		thread.setName("Deselector thread");
		thread.setDaemon(true);
		thread.start();
	}
	
	public static void queue(Rectangle r) {
		q.offer(r);
	}
	
	public static void queueAll(Rectangle... rectangles) {
		for (Rectangle rect : rectangles) {
			q.offer(rect);
		}
	}
	
}
